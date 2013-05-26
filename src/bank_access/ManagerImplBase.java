package bank_access;

public abstract class ManagerImplBase {
	
	public abstract String createAccount(String owner, String branch);
	
	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		String[] objectRefAry = ((String)rawObjectRef).split(":");
		String host = objectRefAry[0];
		int port = Integer.valueOf(objectRefAry[1]);
		String name = objectRefAry[2];
		return new Manager(host, port, name);
	}

}
