package bank_access;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mware_lib.ObjectBroker;
import mware_lib.communicationModule.CommunicationModule;

public class Account extends AccountImplBase{

	String host;
	int port; 
	String objectReference;
	
	public Account(String host, int port, String objectReference) {
		this.host = host;
		this.port = port;
		this.objectReference = objectReference;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		ObjectBroker objectBroker = ObjectBroker.getInstance();
		CommunicationModule communicationModule = objectBroker.getCommunicationModule();
		String message = "INVOKE%" + this.objectReference + "%transfer%double%" + amount;
		
		String returnString = communicationModule.sendAndReceive(message, this.host, this.port);
		String[] returnAry = returnString.split("%");
		if (returnAry[0].equalsIgnoreCase("ERROR")) {
			try {
				String errorType = returnAry[1];
				Class<?> classToThrow = Class.forName(errorType);
				Constructor<?> konstruktor = classToThrow.getConstructor(new Class[] {"".getClass()});
				String errorMessage = returnAry[2];			
				Object exception = konstruktor.newInstance(new Object[]{errorMessage});
				if (exception instanceof OverdraftException) {
					throw (OverdraftException) exception;				
				} else {
					throw (RuntimeException) exception;
				}
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
			
	}
	

	@Override
	public double getBalance() {
		
		ObjectBroker objectBroker = ObjectBroker.getInstance();
		CommunicationModule communicationModule = objectBroker.getCommunicationModule();
		String message = "INVOKE%" + this.objectReference + "%getBalance";
		
		String returnString = communicationModule.sendAndReceive(message, this.host, this.port);
		String[] returnAry = returnString.split("%");
		String returnDifferentiation = returnAry[0];

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

		String returnValue = returnAry[2];
		return Double.valueOf(returnValue);
	}

}
