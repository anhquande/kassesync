package de.anhquan.ordertracker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import de.anhquan.ordertracker.datasource.EmailSource;
import de.anhquan.ordertracker.ui.ResumableTimer;
import de.anhquan.ordertracker.ui.Utils;
import de.anhquan.ordertracker.ui.model.OrderTable;
import de.anhquan.ordertracker.ui.model.OrderTableModel;

public class MainWindow extends JFrame implements Runnable{

	private static final String APP_TITLE = "Dai Duong Lieferstatus";
	private static final long serialVersionUID = 1L;
	private static final int REFRESH_INTERVAL = 45000; // Reload datasource
														// every 10 seconds
	OrderTableModel tableModel;
	JTable table;
	ResumableTimer displayTimer;
	JRadioButton modNormal;
	JRadioButton modBusy;
	JRadioButton modClosed;
	JRadioButton modProblem;
	
	volatile boolean firstInit;

	SimpleDateFormat df = new SimpleDateFormat("HH:mm - dd/MM/yyyy");

	static Logger log = Logger.getLogger(MainWindow.class);

	JButton btPause;
	
	class PopUpDemo extends JPopupMenu {
		private static final long serialVersionUID = 1L;

		public PopUpDemo() {
			final JMenuItem itemRefresh = new JMenuItem("Refresh");
			final JMenuItem itemQuit = new JMenuItem("Quit");
			final JMenuItem itemPause = new JMenuItem(displayTimer.isPaused() ? "Resume" : "Pause");

			itemQuit.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					log.debug("Close the program...");
					MainWindow.this.mailSource.saveToFile();
					MainWindow.this.mailSource.interrupt();
					displayTimer.stop();
					tableModel.saveToFile();
					MainWindow.this.dispose();
					log.debug("Quit!");
				}
			});

			itemPause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					log.debug("Pause button click");
					MainWindow.this.mailSource.toggle();
				}
			});

			itemRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					log.debug("Refresh button clicked. ");
					MainWindow.this.mailSource.fetch();
				}
			});
			
			this.add(itemRefresh);
			this.add(itemPause);
			this.add(itemQuit);
		}
	}

	class PopClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger())
				doPop(e);
		}

		private void doPop(MouseEvent e) {
			PopUpDemo menu = new PopUpDemo();
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	JDialog helpDlg = null;
	public void showHelpDialog(){
		if (helpDlg==null){
		     String message = "F11: Suchen den route auf Google Maps\n" +
		     		"Ctrl + O: Zeigen den Lieferstatus auf dem Browser\n" +
		     		"Ctrl + Recht: zum nächste Lieferstatus";
		     JOptionPane pane = new JOptionPane(message);
		     helpDlg = pane.createDialog(new JFrame(), "Hilfe - Dai Duong Lieferstatus");
		}
		helpDlg.show();
	}
	EmailSource mailSource;

	public MainWindow(final EmailSource mailSource){
		tableModel = new OrderTableModel();
		
		this.mailSource = mailSource;
		mailSource.addListener(tableModel);
	}
	
	public OrderTableModel getTableModel(){
		return this.tableModel;
	}

	protected void updateWindowTitle() {
		setTitle(APP_TITLE + " - " + df.format(new Date()));
	}

	protected void loadRemoteSetting(){
		log.info("Read remote settings to update to value of modNormal/modBusy Radio buttons");
		JSONObject settingStoreModeBusy = Utils.getRemoteSetting("store.mode.busy");
		if (settingStoreModeBusy==null){
			log.error("Cannot get a setting from remote server. Remote server may be down.");
		}
		else{
			String errno = (String)settingStoreModeBusy.get("errno");
			if ("0".compareTo(errno)==0){
				String storeMode = (String)settingStoreModeBusy.get("value");
				if ("1".compareTo(storeMode)==0){
					modBusy.setSelected(true);
				}
				else{
					modNormal.setSelected(true);
				}
			}
			else{
				log.info("Error when reading remote setting:"+settingStoreModeBusy.get("errmsg"));
			}
		}
	}

	@Override
	public void run() {
		System.out.println("Init UI " + Thread.currentThread());
        java.awt.Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		table = new OrderTable(this, tableModel);
		JScrollPane pane = new JScrollPane(table);
		add(pane, BorderLayout.NORTH);
		pane.setPreferredSize(new Dimension(620, 420));
		pane.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		setVisible(true);
		setLayout(new FlowLayout());
		this.setSize(new Dimension(630, 490));
		setTitle(APP_TITLE);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);

		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon("icons/logo_20x20.png").getImage());
		icons.add(new ImageIcon("icons/logo_40x40.png").getImage());
		setIconImages(icons);

		//Statusbar
		log.debug("Init Statusbar ...");
		JPanel statusBar = new JPanel();
		add(statusBar, BorderLayout.SOUTH);
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar.setPreferredSize(new Dimension(this.getWidth(), 32));
		statusBar.setLayout(new BorderLayout());

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new FlowLayout());
		JLabel statusLabel = new JLabel("Betriebsmodus:");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		radioPanel.add(statusLabel, BorderLayout.WEST);

		modNormal = new JRadioButton("Normal");
		modBusy = new JRadioButton("Busy");
		modClosed = new JRadioButton("Geschlossen");
		modProblem = new JRadioButton("Ausserbetrieb");
		ButtonGroup bG = new ButtonGroup();
		bG.add(modNormal);
		bG.add(modBusy);
		bG.add(modClosed);
		bG.add(modProblem);
		radioPanel.add(modNormal,BorderLayout.WEST);
		radioPanel.add(modBusy, BorderLayout.WEST);
		radioPanel.add(modClosed, BorderLayout.WEST);
		radioPanel.add(modProblem, BorderLayout.WEST);

		modNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.debug("Mode Normal");
				Utils.doPostRequest(Constants.host+"/bimat?key=store.mode.busy&value=0");
			}
		});

		modBusy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.debug("Mode Busy");
				Utils.doPostRequest(Constants.host+"/bimat?key=store.mode.busy&value=1");
			}
		});
		modClosed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.debug("Mode Closed");
				Utils.doPostRequest(Constants.host+"/bimat?key=store.mode.busy&value=2");
			}
		});
		modProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.debug("Mode Technical Problem");
				Utils.doPostRequest(Constants.host+"/bimat?key=store.mode.busy&value=3");
			}
		});
		
		
		modNormal.setToolTipText("Normal Betrieb, z.B. am Montag");
		modBusy.setToolTipText("Wenn wir habe viel zu tun, z.B. am Sonntag");
		modClosed.setToolTipText("Nur wenn wir sind geschlossen");
		modProblem.setToolTipText("Es scheint 'Ausserbetrieb' auf der Website");

		JButton btPause = new JButton("Pause");
		btPause.setPreferredSize(new Dimension(110, 10));
		
		statusBar.add(radioPanel, BorderLayout.WEST);
		statusBar.add(btPause, BorderLayout.EAST);
		
		btPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainWindow.this.mailSource.toggle();
				if (MainWindow.this.mailSource.isPaused())
					MainWindow.this.btPause.setText("Resume");
				else
					MainWindow.this.btPause.setText("Pause");
			}
		});
		log.debug("Statusbar added.");
		
		//!!!Statusbar
		// Add Listeners
		this.addMouseListener(new PopClickListener());

		// Add Timer to refresh table
		log.debug("Set up Timer ...");
		displayTimer = new ResumableTimer(REFRESH_INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//tableModel.saveToFile();
				updateWindowTitle();
			}
		});

		displayTimer.setInitialDelay(3000);
		displayTimer.start();
	}
}
