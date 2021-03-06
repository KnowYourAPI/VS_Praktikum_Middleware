package cash_access;

public abstract class TransactionImplBase {
	
	public abstract void deposit(String accountID, double amount);
	
	public abstract void withdraw(String accountID, double amount) throws OverdraftException;
	
	public abstract double getBalance(String accountID);
	
	public static TransactionImplBase narrowCast(Object rawObjectRef) {
		String[] objectRefAry = ((String)rawObjectRef).split(":");
		String host = objectRefAry[0];
		int port = Integer.valueOf(objectRefAry[1]);
		return new Transaction(host, port, (String)rawObjectRef);
	}
}
