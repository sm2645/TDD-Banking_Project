package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Accounts {
	private final double APR;
	private final String ID;
	private final List<String> transactionHistory;
	private double balance;
	private int accountAge;

	protected Accounts(String ID, double APR, double balance) {
		this.balance = balance;
		this.APR = APR;
		this.ID = ID;
		this.accountAge = 0;
		this.transactionHistory = new ArrayList<>();

	}

	public double getBalance() {

		return balance;
	}

	public double getAPR() {
		return APR;
	}

	public String getID() {
		return ID;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (balance >= amount) {
			balance -= amount;

		} else {
			balance = 0;
		}

	}

	public int getAccountAge() {
		return accountAge;
	}

	public void incrementAccountAge(int months) {
		accountAge += months;
	}

	public abstract String getAccountType();

	public void accrueApr() {
		double monthlyRate = (APR / 100) / 12;
		balance += balance * monthlyRate;
		balance = Double.parseDouble(new DecimalFormat("0.00").format(balance));

	}

	void logTransaction(String transaction) {
		transactionHistory.add(transaction);
	}

	public List<String> getTransactionHistory() {
		return transactionHistory;
	}

}
