package nameservice_access;

import mware_lib.ObjectBroker;

public class Main {

	public static void main(String[] args) {
		Nameservice nameservice = new Nameservice();
		ObjectBroker objectBroker = ObjectBroker.init(nameservice.getHost(), nameservice.getPort(), nameservice.getPort());
		objectBroker.getNameService().rebind(nameservice, nameservice.getName());	
	}

}
