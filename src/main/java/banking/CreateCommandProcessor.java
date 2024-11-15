package banking;

public class CreateCommandProcessor {
	private final Bank bank;

	public CreateCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandSeparated) {

		String accountType = commandSeparated[1];
		String accountId = commandSeparated[2];
		double apr = Double.parseDouble(commandSeparated[3]);

		switch (accountType) {
		case "checking":
			bank.addAccount(accountId, new Checking(accountId, apr));
			break;
		case "savings":
			bank.addAccount(accountId, new Savings(accountId, apr));
			break;
		case "cd":
			double balance = Double.parseDouble(commandSeparated[4]);
			bank.addAccount(accountId, new CD(accountId, apr, balance));
			break;
		}

	}
}
