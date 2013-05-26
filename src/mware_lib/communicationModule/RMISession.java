package mware_lib.communicationModule;

import java.net.Socket;

class RMISession extends Thread {
	
	private Socket connectionSocket;
	
	public RMISession(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}
	
	// TODO
	// Hier werden eingehende RMI-Anfragen behandelt
	// und an die entsprechenden Skeletons weitergeleitet
	@Override
	public void run() {
		
	}

}
