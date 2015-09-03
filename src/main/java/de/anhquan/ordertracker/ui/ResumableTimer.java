package de.anhquan.ordertracker.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class ResumableTimer extends Timer{

	private boolean isPaused;
	
	public void toggle(){
		if (isPaused())
			start();
		else
			stop();
	}
	
	public void resume() {
		if (isPaused())
			start();
	}

	public void pause() {
		if (isPaused())
			return;
		
		stop(); 
	}
	
	@Override
	public void start() {
		setPaused(false);
		super.start();
	}
	
	@Override
	public void stop() {
		setPaused(true);
		super.stop();
	}

	public ResumableTimer(int delay, final ActionListener listener) {
		super(delay, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				listener.actionPerformed(e);
			}
		});
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	private static final long serialVersionUID = -729528300135136180L;

}
