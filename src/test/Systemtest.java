package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import bank_access.AccountImplBase;

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
		AccountImplBase remoteKonto = AccountImplBase.narrowCast(objectReference);
		remoteKonto.getBalance();
		

	}

}
