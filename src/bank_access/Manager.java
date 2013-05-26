package bank_access;

import mware_lib.CommunicationModule;
import mware_lib.ObjectBroker;

public class Manager extends ManagerImplBase {

	String host;
	int port; 
	String name;
	
	public Manager(String host, int port, String name) {
		this.host = host;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		ObjectBroker objectBroker = ObjectBroker.getInstance();
		CommunicationModule communicationModule = objectBroker.getCommunicationModule();
		String message = "INVOKE%" + this.name + "%createAccount%String%" + owner + "%String%" + branch;
		communicationModule.send(message, this.host, this.port);

		// und wie komme ich jetzt an den Returnwert?
		// ? return communicationModule.recieve();
		// TODO
	}

}
