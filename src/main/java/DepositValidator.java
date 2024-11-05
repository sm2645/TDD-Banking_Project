public class DepositValidator {

	private final Bank bank;
	private final CommandValidator commandValidator;

	public DepositValidator(Bank bank, CommandValidator commandValidator) {
		this.bank = bank;
		this.commandValidator = commandValidator;
	}

	public boolean validate(String[] commandSeparated) {
		if (commandSeparated.length != 3) {
			return false;
		}

		String accountId = commandSeparated[1];
		String amount = commandSeparated[2];
		Accounts account = bank.retrieveAccount(accountId);

		if (account instanceof CD) {
			return false;
		}
		if (!commandValidator.isValidAccountId(accountId) || !commandValidator.isValidBalance(amount)) {
			return false;
		}
		if (account instanceof Savings && Double.parseDouble(amount) > 2500) {
			return false;
		}

		if (account instanceof Checking && Double.parseDouble(amount) > 1000) {
			return false;
		}

		return true;
	}
}
