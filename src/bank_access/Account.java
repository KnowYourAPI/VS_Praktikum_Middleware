package bank_access;

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
		// TODO 
	}

	@Override
	public double getBalance() {
		// TODO 
	}

}
