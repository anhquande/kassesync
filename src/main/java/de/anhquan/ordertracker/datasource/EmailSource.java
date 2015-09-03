package de.anhquan.ordertracker.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Flags;

import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ImapSslServer;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.anhquan.ordertracker.parser.OrderBuildHelper;
import de.anhquan.ordertracker.parser.OrderParser;
import de.anhquan.ordertracker.parser.OrderParsingException;
import de.anhquan.ordertracker.parser.model.Address;
import de.anhquan.ordertracker.parser.model.Order;
import de.anhquan.ordertracker.ui.OrderListener;
import de.anhquan.ordertracker.ui.model.OrderNumber;
import de.anhquan.ordertracker.ui.model.OrderTrackingEntity;
import de.anhquan.ordertracker.ui.model.OrderTrackingStatus;

/**
 * 
 * @author anhquan
 *
 */
public class EmailSource extends Thread implements IDataSource {
	ReceiveMailSession session;
	ImapSslServer imapServer;
	String emailServer;
	String emailUsername;
	String emailPassword;
	
	static Logger log = Logger.getLogger(EmailSource.class);
	
	Properties prop;
	String outputDir = "";
	private String filterSender;
	private static int orderName = 8101;
	volatile boolean isChecking = false;
	volatile boolean paused = false;
	
	public EmailSource(Properties prop) {
		this.prop = prop;
		outputDir = prop.getProperty("output-dir");
		emailServer = prop.getProperty("email-server");
		emailUsername = prop.getProperty("email-username");
		emailPassword = prop.getProperty("email-password");
		filterSender= prop.getProperty("filter-sender");
		Integer i = loadFromFile();
		if (i==null)
			orderName= Integer.parseInt(prop.getProperty("last-order"));
		else
			orderName = i;
		orderName++;
	}
	
	public void toggle(){
		paused = !paused;
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	@Override
	public void interrupt() {
		try {
			closeSession();
        } catch (Exception ignored) {
        } finally {
            super.interrupt();
        }
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			if (!paused)
				checkMail();
			
			try {
				sleep(60000);
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}

	public void openSession() {
		log.info("Create IMAP Session ...");
		session = imapServer.createSession();
		session.open();
		session.useFolder("INBOX");
		log.info("IMAP Session opened");
	}

	public void closeSession() {
		log.info("going to close session ...");
		if (session!=null)
			session.close();
		session = null;
		log.info("IMAP Session closed. Good bye!");
	}

	public void checkMail() {
		isChecking = true;
		openSession();
		
		newOrders.clear();
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy hh:mm:ss");
		log.info("[" + df.format(new Date()) + "] Inbox ("
				+ session.getMessageCount() + ")");
		Flags flags = new Flags("processed");

		Flags flagsToSet = new Flags("processed");
		ReceivedEmail[] emails = session.receive(
				EmailFilter.filter().from(filterSender).subject("#new_order  #"+orderName)
						, flagsToSet);
//		ReceivedEmail[] emails = session.receive(
//				EmailFilter.filter().from(filterSender)
//						.flags(flags, false), flagsToSet);
		
		//TEST//TEST
//		Flags flagsToSet = new Flags("checked");
//				ReceivedEmail[] emails = session.receive(
//				EmailFilter.filter().or(
//						EmailFilter.filter().subject("#new_order  #"+emailName)).
//				flags(flags, false), flagsToSet);
				
				
		if (emails != null) {
			log.info("New Message ("+emails.length + ")");
			for (ReceivedEmail email : emails) {
				orderName++;
				// process messages
				List<EmailMessage> messages = email.getAllMessages();
				for (EmailMessage msg : messages) {
					if (msg.getMimeType().contains("TEXT/PLAIN")) {
						try {
							String emailContent = msg.getContent();
							System.out.println(emailContent);
							Order order = OrderParser.parse(emailContent);
							OrderBuildHelper.toXML(order, outputDir);
							
							// Tracking Entity
							OrderTrackingEntity trackingEntity = new OrderTrackingEntity();
							int orderNumber = Integer.parseInt(order.getShopifyId());
							trackingEntity.setOrderNumber(orderNumber );
							
							trackingEntity.setTrackingId(order.getTrackingId());

							Address address = order.getCustomer().getDeliveryAddress();
							trackingEntity.setCustomer(address.getLastName());
							trackingEntity.setAddress(address.getStreet()+" "+address.getHouseNo() );
							
							try {
								String strDate = order.getParent().getCreateDateTime();
								trackingEntity.setOrderDate(df.parse(strDate));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							
							trackingEntity.setStatus(OrderTrackingStatus.READY);
							
							OrderTrackingEntity found = findOrderByNumber(orderNumber);
							if (found==null){
								newOrders.add(trackingEntity);
								orders.add(trackingEntity);
							}
							order = null; //clean up
						} catch (OrderParsingException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			log.info("New Message (0)");
		}
		if (!newOrders.isEmpty())
			fireEvent();
		
		closeSession();
		isChecking = false;
	}
	
	List<OrderListener> listeners = new ArrayList<OrderListener>();
	List<OrderTrackingEntity> orders = new ArrayList<OrderTrackingEntity>();
	List<OrderTrackingEntity> newOrders = new ArrayList<OrderTrackingEntity>();

	private OrderTrackingEntity findOrderByNumber(int index){
		for (OrderTrackingEntity order : orders) {
			OrderNumber orderNumber = order.getOrderNumber();
			if (orderNumber!=null)
				if (orderNumber.value() == index)
					return order;
			
			
			
			
		}
		return null;
	}
	
	private void fireEvent(){
		for (OrderListener listener : listeners) {
			listener.onNewOrdersReceived(this, newOrders);
		}
	}
	
	public void addListener(OrderListener listener) {
		listeners.add(listener);
	}

	public void fetch() {
		checkMail();
	}

	public boolean connect() {
		imapServer = new ImapSslServer(emailServer,
				emailUsername, emailPassword);

		return true;
	}

	public void disconnect() {
		
	}

	public void saveToFile(){	
		FileOutputStream out;
		try {
			out = new FileOutputStream("control.conf");
			Properties props = new Properties();
			int lastOrder = orderName-1;
			props.setProperty("last-order", ""+lastOrder);
			props.store(out, null);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

//        File file = new File("ordername.tmp");
//        FileOutputStream f;
//		try {
//			f = new FileOutputStream(file);
//	        ObjectOutputStream s = new ObjectOutputStream(f);
//	        s.writeObject(orderName);
//	        s.close();
//	        
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}
	
	public Integer loadFromFile(){
		System.out.println("Load from file ...");
		final Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("control.conf"));
		} catch (IOException e) {
			log.error("Cannot load the control.conf");
		}
		String lastOrder = properties.getProperty("last-order");
		if ((lastOrder!=null) && (!lastOrder.isEmpty())){
			return Integer.parseInt(lastOrder);
		}
		
		return 0;

//		Integer i = null;
//		try {
//			FileInputStream f = new FileInputStream("ordername.tmp");
//		    ObjectInputStream s = new ObjectInputStream(f);
//		    i = (Integer) s.readObject();
//		    s.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		return i;
	}

}
