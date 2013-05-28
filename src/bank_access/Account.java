package bank_access;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mware_lib.ObjectBroker;
import mware_lib.communicationModule.CommunicationModule;

public class Account extends AccountImplBase{

	String host;
	int port; 
	String name;
	
	public Account(String host, int port, String name) {
		this.host = host;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		ObjectBroker objectBroker = ObjectBroker.getInstance();
		CommunicationModule communicationModule = objectBroker.getCommunicationModule();
		String message = "INVOKE%" + this.name + "%transfer%double%" + amount;
		
		String returnString = communicationModule.sendAndReceive(message, this.host, this.port);
		String[] returnAry = returnString.split("%");
		if (returnAry[0].equalsIgnoreCase("ERROR")) {
			try {
				Class<?> classToThrow = Class.forName(returnAry[1]);
				Constructor<?> konstruktor = classToThrow.getConstructor(new Class[] {"".getClass()});
								
				Object exception = konstruktor.newInstance(new Object[]{returnAry[2]});
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
		String message = "INVOKE%" + this.name + "%getBalance";
		
		String returnString = communicationModule.sendAndReceive(message, this.host, this.port);
		String[] returnAry = returnString.split("%");
		String returnDifferentiation = returnAry[0];
		String returnValue = returnAry[2];

		if (returnDifferentiation.equalsIgnoreCase("ERROR")) {
			try {
				Class<?> classToThrow = Class.forName(returnAry[1]);
				Constructor<?> konstruktor = classToThrow.getConstructor(new Class[] {"".getClass()});
								
				Object exception = konstruktor.newInstance(new Object[]{returnAry[2]});

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

		return Double.valueOf(returnValue);
	}

}
