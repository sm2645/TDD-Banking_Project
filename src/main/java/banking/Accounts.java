package banking;

public abstract class Accounts {
	private final double APR;
	private final String ID;
	private double balance;
	private int accountAge;

	public Accounts(String ID, double APR, double balance) {
		this.balance = balance;
		this.APR = APR;
		this.ID = ID;
		this.accountAge = 0;

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

}
