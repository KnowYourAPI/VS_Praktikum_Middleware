package nameservice_access;

import java.util.HashMap;
import java.util.Map;

public class Nameservice {
	
	private final String NAMESERVICE_NAME = "nameservice";
	
	private final String HOST = "localhost";
	
	private final int PORT = 10000;
	
	private Map<String, String> nameToReference;
	
	public Nameservice() {
		this.nameToReference = new HashMap<String, String>();
	}

	public synchronized void rebind(String name, String reference) {
		nameToReference.put(name, reference);
	}
	
	public synchronized String resolve(String name) {
		return nameToReference.get(name);
	}
		
	public String getHost() {
		return HOST;
	}

	public int getPort() {
		return PORT;
	}

	public String getName() {
		return NAMESERVICE_NAME;
	}
}
