package mware_lib;

import mware_lib.communicationModule.CommunicationModule;

public class ObjectBroker {
	
	private static ObjectBroker instance;

	public static ObjectBroker init(String nameserviceHost, int nameservicePort) {
		if(instance == null) {
			CommunicationModule communicationModule = new CommunicationModule();
			communicationModule.start();
			RemoteReferenceModule remoteReferenceModule = new RemoteReferenceModule();
			NameService nameService = new NameserviceStub(nameserviceHost, nameservicePort);
			instance = new ObjectBroker(communicationModule, remoteReferenceModule, nameService);
		}
		return instance;
	}
	
	public static ObjectBroker getInstance() {
		return instance;
	}
	
	private CommunicationModule communicationModule;
	private RemoteReferenceModule remoteReferenceModule;
	private NameService nameService;
	
	private ObjectBroker(CommunicationModule communicationModule,
			RemoteReferenceModule remoteReferenceModule, NameService nameService) {
		this.communicationModule = communicationModule;
		this.remoteReferenceModule = remoteReferenceModule;
		this.nameService = nameService;
	}
	
	public void shutDown() {
		//TODO: Gibt es noch weiteres zu tun als das ComModule zu beenden?
		communicationModule.shutdown();
	}
	
	//------ weitere benoetigte Methoden ------
	
	public NameService getNameService() {
		return nameService;
	}
	
	public CommunicationModule getCommunicationModule() {
		return communicationModule;
	}
	
	public RemoteReferenceModule getRemoteReferenceModule() {
		return remoteReferenceModule;
	}
}
