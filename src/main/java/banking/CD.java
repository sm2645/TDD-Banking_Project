package banking;

public class CD extends Accounts {
	public CD(String ID, double APR, double balance) {
		super(ID, APR, balance);
	}

	@Override
	public String getAccountType() {
		return "CD";
	}

}