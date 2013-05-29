package mware_lib;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mware_lib.communicationModule.CommunicationModule;

public class RemoteReferenceModule {
	
	private Map<String, Skeleton> localSkeletonDirectory;
	
	public RemoteReferenceModule() {
		this.localSkeletonDirectory = new ConcurrentHashMap<String, Skeleton>();
	}
	
	// Erstellt ein Skeleton fuer das uebergebene Objekt,
	// erzeugt dessen entfernte Referenz,
	// speichert abschliessend das Skeleton unter der Referenz lokal ab
	// und gibt die Referenz zurueck
	public String save(String objectName, Object object) {
		Skeleton skeleton = new Skeleton(object);
		CommunicationModule communicationModule = ObjectBroker.getInstance().getCommunicationModule();
		int localMiddlewarePort = communicationModule.getLocalPort();
		String localHostName = null;
		
		try {
			localHostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) { e.printStackTrace(); }
		
		String remoteReference = localHostName
								+ ":" + localMiddlewarePort
								+ ":" + objectName;
		localSkeletonDirectory.put(remoteReference, skeleton);

		return remoteReference;		
	}
	
	public Skeleton getResponsibleSkeleton(String message) {
		String[] splitMessage = message.split("%");
		String objectReference = splitMessage[1];
		return localSkeletonDirectory.get(objectReference);
	}

}
