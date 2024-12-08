package banking;

public class Savings extends Accounts {
	private boolean alreadyWithdrawalsMonth;

	public Savings(String ID, double APR) {
		super(ID, APR, 0);
		this.alreadyWithdrawalsMonth = false;
	}

	public boolean hasWithdrawnThisMonth() {
		return alreadyWithdrawalsMonth;
	}

	void changeWithdrawalStatus() {
		alreadyWithdrawalsMonth = !alreadyWithdrawalsMonth;
	}

	void resetWithdrawals() {
		alreadyWithdrawalsMonth = false;
	}

	@Override
	public String getAccountType() {
		return "banking.Savings";
	}

}
