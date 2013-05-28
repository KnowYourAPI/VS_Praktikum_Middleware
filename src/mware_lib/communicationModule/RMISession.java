package mware_lib.communicationModule;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import mware_lib.ObjectBroker;
import mware_lib.RemoteReferenceModule;
import mware_lib.Skeleton;

class RMISession extends Thread {
	
	private Socket connectionSocket;
	
	public RMISession(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}
	
	// Hier werden eingehende RMI-Anfragen behandelt
	// und an die entsprechenden Skeletons weitergeleitet
	// und dann die entsprechende Antwort zurueckgesendet
	@Override
	public void run() {
		
		try {
			
			String rmiMessage = readFromServer(connectionSocket);
			RemoteReferenceModule remoteReferenceModule = ObjectBroker.getInstance().getRemoteReferenceModule();
			Skeleton skeleton = remoteReferenceModule.getResponsibleSkeleton(rmiMessage);
			String rmiResponse = skeleton.invokeMethod(rmiMessage);
			writeToServer(connectionSocket, rmiResponse);
			connectionSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Sende die uebergebene Nachricht an den uebergebenen Socket
	// Jede Nachricht wird durch ein newline getrennt.
	// Nachrichten duerfen daher keine newlines enthalten
	private void writeToServer(Socket socket, String message) throws IOException {
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes(message + '\n' );
	}
	
	// Lese die naechste Zeile aus dem uebergebenen Socket
	// Blockiert, bis eine Nachricht angekommen ist, die mit einem newline endet
	private String readFromServer(Socket socket) throws IOException {
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return inFromServer.readLine();
	}

}
