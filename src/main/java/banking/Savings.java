package banking;

public class Savings extends Accounts {

	public Savings(String ID, double APR) {
		super(ID, APR, 0);
	}

	@Override
	public String getAccountType() {
		return "banking.Savings";
	}

}
