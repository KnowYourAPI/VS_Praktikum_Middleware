package nameservice;

import java.util.HashMap;
import java.util.Map;

public class Nameservice {

	private Map<String, String> nameToReference;

	public Nameservice() {
		this.nameToReference = new HashMap<String, String>();
	}

	public synchronized void rebind(String name, String reference) {
		System.out.println("Rebind: " + name + " -> " + reference);
		nameToReference.put(name, reference);
	}

	public synchronized String resolve(String name) {
		return nameToReference.get(name);
	}

}
