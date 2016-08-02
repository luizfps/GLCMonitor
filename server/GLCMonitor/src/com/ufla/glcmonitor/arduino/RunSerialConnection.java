package com.ufla.glcmonitor.arduino;

public class RunSerialConnection {

	public void run() throws Exception {
		SerialConnection main = new SerialConnection();
		main.initialize();
		
		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for 1000 seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				try {
					Thread.sleep(1);
				} catch (InterruptedException ie) {
				}
			}
		};
		
		t.start();
	}
	
}
