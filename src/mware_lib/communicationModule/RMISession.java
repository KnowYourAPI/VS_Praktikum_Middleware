package mware_lib.communicationModule;

import java.net.Socket;

public class RMISession extends Thread {
	
	private Socket connectionSocket;
	
	public RMISession(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}
	
	@Override
	public void run() {
		
	}

}
