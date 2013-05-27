package mware_lib;

import mware_lib.communicationModule.CommunicationModule;

public class NameserviceStub extends NameService {

	private final String NAMESVERVICE_NAME = "nameservice";
	private String nameserviceHost;
	private int nameservicePort;
	
	public NameserviceStub(String nameserviceHost, int nameservicePort) {
		this.nameserviceHost = nameserviceHost;
		this.nameservicePort = nameservicePort;
	}
	
	@Override
	public void rebind(Object servant, String name) {
		String nameserviceReference = nameserviceHost + ":" + nameservicePort + ":" + NAMESVERVICE_NAME;
		RemoteReferenceModule remoteReferenceModule = ObjectBroker.getInstance().getRemoteReferenceModule();
		String servantReference = remoteReferenceModule.save(name, servant);
		//Ruft auf dem entfernten Nameservice auf: rebind(String name, String remoteReference);
		String rebindMessage = "INVOKE%rebind%" + nameserviceReference + "%" + "String%" + name + "%String%" + servantReference;
		
		CommunicationModule communicationModule = ObjectBroker.getInstance().getCommunicationModule();
		communicationModule.send(rebindMessage, nameserviceHost, nameservicePort);
	}

	@Override
	public Object resolve(String name) {
		return null;
	}

}
