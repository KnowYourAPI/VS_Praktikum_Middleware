package bank_access;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mware_lib.ObjectBroker;
import mware_lib.communicationModule.CommunicationModule;

public class Manager extends ManagerImplBase {

	String host;
	int port; 
	String name;
	
	public Manager(String host, int port, String name) {
		this.host = host;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		ObjectBroker objectBroker = ObjectBroker.getInstance();
		CommunicationModule communicationModule = objectBroker.getCommunicationModule();
		String message = "INVOKE%" + this.name + "%createAccount%String%" + owner + "%String%" + branch;
		
		String returnString = communicationModule.sendAndReceive(message, this.host, this.port);
		String[] returnAry = returnString.split("%");
		String returnDifferentiation = returnAry[0];
		String returnValue = returnAry[2];
		
		if (returnDifferentiation.equalsIgnoreCase("ERROR")) {
			try {
				String errorType = returnAry[1];
				Class<?> classToThrow = Class.forName(errorType);
				Constructor<?> konstruktor = classToThrow.getConstructor(new Class[] {"".getClass()});
								
				String errorMessage = returnAry[2];			
				Object exception = konstruktor.newInstance(new Object[]{errorMessage});

				throw (RuntimeException) exception;
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return returnValue;
	}

}
