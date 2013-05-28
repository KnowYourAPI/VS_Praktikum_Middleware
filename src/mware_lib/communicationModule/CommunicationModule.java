package mware_lib.communicationModule;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommunicationModule extends Thread {
	
	private ServerSocket serverSocket;
	private boolean communicationServerRunning;
	
	// Das CommunicationModule bindet sich an einen beliebigen Port.
	// Dieser kann ueber getLocalPort() abgefragt werden.
	public CommunicationModule() {
		try {
			this.serverSocket = new ServerSocket(0);
			this.communicationServerRunning = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CommunicationModule(int middlewarePort) {
		try {
			this.serverSocket = new ServerSocket(middlewarePort);
			this.communicationServerRunning = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Nimmt eingehende Verbindungsanfragen an und startet eine RMISession
	// Die RMISession behandelt dann die eingehende RMI-Anfrage
	@Override
	public void run() {
		while(communicationServerRunning) {
			try {
				Socket connectionSocket = serverSocket.accept();
				(new RMISession(connectionSocket)).start();
			} catch (IOException e) {
				System.out.println("Server shutting down.");
			}
		}
	}
	
	public void shutdown() {
		communicationServerRunning = false;
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Sende eine Nachricht ohne eine Antwort zu erwarten
	// (z.B. bei einer Funktion ohne Rueckgabewert)
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
	
	
	// Sende eine Nachricht und erwarte eine Antwort
	// Die Methode blockiert, bis eine Antwort erhalten wurde
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
	
	// Gibt den Port zurueck an den sich das CommunicationModule gebunden hat
	public int getLocalPort() {
		return serverSocket.getLocalPort();
	}
}
