package banking;

public class Checking extends Accounts {

	public Checking(String ID, double APR) {
		super(ID, APR, 0);
	}

	@Override
	public String getAccountType() {
		return "Checking";
	}

}