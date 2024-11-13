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
		if (account == null) {
			return false;
		}
		String accountType = account.getAccountType();

		if (!commandValidator.isValidAccountId(accountId) || !commandValidator.isValidBalance(amount)) {
			return false;
		}

		return switch (accountType) {
		case "Certificate of Deposit" -> validateCD();
		case "Savings" -> validateSavings(amount);
		case "Checking" -> validateChecking(amount);
		default -> false;
		};
	}

	private boolean validateCD() {
		return false;
	}

	private boolean validateSavings(String amount) {
		return Double.parseDouble(amount) <= 2500;
	}

	private boolean validateChecking(String amount) {
		return Double.parseDouble(amount) <= 1000;
	}
}
