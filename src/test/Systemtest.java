package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cash_access.OverdraftException;
import cash_access.TransactionImplBase;

import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class Systemtest {

	public static void main(String[] args) throws UnknownHostException {
		String host = InetAddress.getLocalHost().getHostName();
		ObjectBroker objectBroker = ObjectBroker.init(host, 9000);
		NameService nameService = objectBroker.getNameService();
		Konto konto = new Konto();
		nameService.rebind(konto, "MeinKonto");
		Object objectReference = nameService.resolve("MeinKonto");
		TransactionImplBase remoteKonto = TransactionImplBase.narrowCast(objectReference);
		try {
			remoteKonto.withdraw("Klaus", 720);
		} catch (OverdraftException e) {
			System.out.println("OverdraftException gefangen :)");
			System.out.println("Message: " + e.getMessage());
		}
		
		objectBroker.shutDown();
	}

}
