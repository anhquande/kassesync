package de.anhquan.ordertracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.anhquan.ordertracker.parser.CustomerMapper;


public class MainGui {
	
	static Logger log = Logger.getLogger(MainGui.class);
	
	public static void main(String[] args) throws Exception {
		
		CustomerMapper.loadFromFile();
		Thread.currentThread().setName("guiThread");
		
		PropertyConfigurator.configure("log4j.properties");

		final Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("app.conf"));
		} catch (IOException e) {
			log.error("Cannot load the configuration file app.conf");
		}
		final de.anhquan.ordertracker.datasource.EmailSource mailSource = new de.anhquan.ordertracker.datasource.EmailSource(properties);
		mailSource.setName("coreThread");
		final MainWindow mainWin = new MainWindow(mailSource);
		
		Thread appThread = new Thread() {
            public void run() {
                try {
                    SwingUtilities.invokeAndWait(mainWin);
                               		
					log.debug("loading content to UI...");
					mainWin.loadRemoteSetting();
					
					mailSource.connect();
					mailSource.start();					
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Finished on " + Thread.currentThread());
            }
        };
        
        appThread.start();
	}
}
