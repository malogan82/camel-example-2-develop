package it.marco.camel.thread;

import org.apache.camel.main.Main;

public class TestRunnable implements Runnable{
	
	private Main main;
	
	public TestRunnable(Main main) {
		this.main = main;
	}

	public void run() {
		try {
			main.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
