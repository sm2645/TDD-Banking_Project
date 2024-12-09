package banking;

public class DepositCommandProcessor {
	private final Bank bank;

	public DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandSeparated) {
		String accountId = commandSeparated[1];
		double depositAmount = Double.parseDouble(commandSeparated[2]);

		bank.retrieveAccount(accountId).deposit(depositAmount);
		bank.retrieveAccount(accountId).logTransaction(String.join(" ", commandSeparated));
	}
}
