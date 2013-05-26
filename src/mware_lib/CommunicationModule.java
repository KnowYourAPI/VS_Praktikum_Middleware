package mware_lib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommunicationModule {
	
	public void send(String message, String host, int port) {
		//TODO
	}
	
	public String sendAndReceive(String message, String host, int port) {
		String answer = null;
		
		try {
			Socket socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return answer;
	}
	
	private void writeToServer(Socket socket, String message) throws IOException {
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		outToServer.writeBytes(message + '\n' );
	}
	
	private String readFromServer(Socket socket) throws IOException {
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return inFromServer.readLine();
	}
}
