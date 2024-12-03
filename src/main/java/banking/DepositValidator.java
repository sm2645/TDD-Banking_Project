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

		if (!isValidAccountId(accountId) || !isValidBalance(amount)) {
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

	public boolean isValidAccountId(String id) {
		if (id.length() != 8) {
			return false;
		}

		for (int i = 0; i < id.length(); i++) {
			char num = id.charAt(i);
			if (num < '0' || num > '9') {
				return false;
			}
		}
		return true;
	}

	public boolean isValidBalance(String balance) {
		try {
			double balanceInteger = Double.parseDouble(balance);
			return balanceInteger >= 0;
		} catch (NumberFormatException e) {
			return false;
		}

	}
}
