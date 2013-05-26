package bank_access;

import mware_lib.CommunicationModule;
import mware_lib.ObjectBroker;

public class Account extends AccountImplBase{

	String host;
	int port; 
	String name;
	
	public Account(String host, int port, String name) {
		this.host = host;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		ObjectBroker objectBroker = ObjectBroker.getInstance();
		CommunicationModule communicationModule = objectBroker.getCommunicationModule();
		String message = "INVOKE%" + this.name + "%transfer%double%" + amount;
		communicationModule.send(message, this.host, this.port);
		// TODO 
	}

	@Override
	public double getBalance() {
		ObjectBroker objectBroker = ObjectBroker.getInstance();
		CommunicationModule communicationModule = objectBroker.getCommunicationModule();
		String message = "INVOKE%" + this.name + "%getBalance";
		communicationModule.send(message, this.host, this.port);
		
		// und wie komme ich jetzt an den Returnwert?
		// ? return communicationModule.recieve();
		
		// TODO 
	}

}
