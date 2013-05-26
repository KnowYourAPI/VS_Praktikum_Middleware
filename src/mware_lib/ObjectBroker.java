package mware_lib;

import mware_lib.communicationModule.CommunicationModule;

public class ObjectBroker {
	
	private static ObjectBroker instance;

	public static ObjectBroker init(String serviceHost, int listenPort) {
		if(instance == null) {
			CommunicationModule communicationModule = new CommunicationModule();
			RemoteReferenceModule remoteReferenceModule = new RemoteReferenceModule();
			//TODO: NameService-Implementierung
			NameService nameService = new NameService();
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
	
	public NameService getNameService() {
		//TODO
	}
	
	public void shutDown() {
		//TODO
	}
	
	//------ weitere benoetigte Methoden ------
	
	public CommunicationModule getCommunicationModule() {
		//TODO
	}
}
