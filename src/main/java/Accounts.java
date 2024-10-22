public abstract class Accounts {
	private final double APR;
	private double balance;

	public Accounts(double APR, double balance) {
		this.balance = balance;
		this.APR = APR;
	}

	public double getBalance() {

		return balance;
	}

	public double getAPR() {
		return APR;
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
}
