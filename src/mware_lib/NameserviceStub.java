package mware_lib;

import mware_lib.communicationModule.CommunicationModule;

public class NameserviceStub extends NameService {

	private final String NAMESVERVICE_NAME = "nameservice";
	private final String STRING_CLASS_NAME = String.class.getName();
	private String nameserviceHost;
	private int nameservicePort;
	private String nameserviceReference;
	
	public NameserviceStub(String nameserviceHost, int nameservicePort) {
		this.nameserviceHost = nameserviceHost;
		this.nameservicePort = nameservicePort;
		this.nameserviceReference = nameserviceHost
			 					  + ":" + nameservicePort
								  + ":" + NAMESVERVICE_NAME;
	}
	
	@Override
	public void rebind(Object servant, String name) {
		RemoteReferenceModule remoteReferenceModule = ObjectBroker.getInstance().getRemoteReferenceModule();
		String servantReference = remoteReferenceModule.save(name, servant);
		//Ruft auf dem entfernten Nameservice auf: rebind(String name, String remoteReference);
		String rebindMessage = "INVOKE%"
							 + nameserviceReference
						 	 + "%rebind%" + STRING_CLASS_NAME
						 	 + "%" + name + "%" +
						 	 STRING_CLASS_NAME + "%" + servantReference;
		
		CommunicationModule communicationModule = ObjectBroker.getInstance().getCommunicationModule();
		communicationModule.sendAndReceive(rebindMessage, nameserviceHost, nameservicePort);
	}

	@Override
	public Object resolve(String name) {
		String resolveMessage = "INVOKE%" 
							  + nameserviceReference
							  + "%resolve%" + STRING_CLASS_NAME + "%" + name;
		CommunicationModule communicationModule = ObjectBroker.getInstance().getCommunicationModule();
		String resolveResponse = communicationModule.sendAndReceive(resolveMessage, nameserviceHost, nameservicePort);
		//resolveResponse = "RETURN%<Type>%<Value>"
		String[] splittedResponse = resolveResponse.split("%");
		String returnType = splittedResponse[1];
		String returnValue = splittedResponse[2];
		
		//DEBUG-Abfrage:
		if(!returnType.equals(String.class.getName())) {
			System.err.println("Got an illegal responsemessage, expected Type: "
								+ String.class.getName()
								+ " but received Type: " + returnType);
		}
		
		return returnValue;
	}

}
