package banking;

public class DepositValidator {

	private final Bank bank;

	public DepositValidator(Bank bank) {
		this.bank = bank;
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

		switch (accountType) {
		case "Certificate of Deposit":
			return validateCD();
		case "banking.Savings":
			return validateSavings(amount);
		case "banking.Checking":
			return validateChecking(amount);
		default:
			return false;
		}
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
