package test;

import cash_access.OverdraftException;
import cash_access.TransactionImplBase;

public class Konto extends TransactionImplBase {

	@Override
	public void deposit(String accountID, double amount) {
		System.out.println("Deposit called with: " + accountID + ", " + amount);
	}

	@Override
	public void withdraw(String accountID, double amount)
			throws OverdraftException {
		System.out.println("Withdraw called with: " + accountID + ", " + amount);
		throw new OverdraftException("Meine tolle Errormessage :)");
	}

	@Override
	public double getBalance(String accountID) {
		System.out.println("GetBalance called with: " + accountID);
		return 42;
	}



}
