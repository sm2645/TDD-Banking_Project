package banking;

public class WithdrawCommandProcessor {
	private final Bank bank;

	public WithdrawCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandSeparated) {
		String accountId = commandSeparated[1];
		double withdrawAmount = Double.parseDouble(commandSeparated[2]);

		bank.retrieveAccount(accountId).withdraw(withdrawAmount);
		bank.retrieveAccount(accountId).logTransaction(String.join(" ", commandSeparated));
	}
}
