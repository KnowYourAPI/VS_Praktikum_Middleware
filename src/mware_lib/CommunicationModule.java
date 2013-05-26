package mware_lib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommunicationModule {
	
	public void send(String message, String host, int port) {
		try {
			Socket socket = new Socket(host, port);
			writeToServer(socket, message);
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String sendAndReceive(String message, String host, int port) {
		String answer = null;
		
		try {
			Socket socket = new Socket(host, port);
			writeToServer(socket, message);
			answer = readFromServer(socket);
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return answer;
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
