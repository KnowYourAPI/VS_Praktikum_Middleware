package nameservice_access;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.sun.org.apache.xml.internal.security.Init;

import mware_lib.ObjectBroker;

public class Main {

	public static void main(String[] args) throws UnknownHostException {
		String host = InetAddress.getLocalHost().getHostName();
		String nameserviceName = "nameservice";
		int port = 9000;
		
		Nameservice nameservice = new Nameservice();
		ObjectBroker objectBroker = ObjectBroker.init(host, port, port);
		objectBroker.getNameService().rebind(nameservice, nameserviceName);	
	}

}
