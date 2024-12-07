package banking;

public class TransferCommandProcessor {
	private final Bank bank;

	public TransferCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandSeparated) {
		String senderAccountId = commandSeparated[1];
		String receiverAccountId = commandSeparated[2];
		double transferAmount = Double.parseDouble(commandSeparated[3]);

		bank.retrieveAccount(senderAccountId).withdraw(transferAmount);
		bank.retrieveAccount(receiverAccountId).deposit(transferAmount);

	}
}
