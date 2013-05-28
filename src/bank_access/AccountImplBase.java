package bank_access;

public abstract class AccountImplBase {
	
	public abstract void transfer(double amount) throws OverdraftException;
	
	public abstract double getBalance();
	
	public static AccountImplBase narrowCast (Object rawObjectRef) {
		String[] objectRefAry = ((String)rawObjectRef).split(":");
		String host = objectRefAry[0];
		int port = Integer.valueOf(objectRefAry[1]);
		return new Account(host, port, (String)rawObjectRef);
	}
}
