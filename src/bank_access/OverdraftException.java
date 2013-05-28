package bank_access;

public class OverdraftException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public OverdraftException() {
		super();
	}
	
	public OverdraftException(String message) {
		super(message);
	}

}
