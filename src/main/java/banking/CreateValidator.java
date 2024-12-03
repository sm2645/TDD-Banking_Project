package banking;

import java.util.Objects;

public class CreateValidator {
	private final Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;

	}

	public boolean validate(String[] commandSeparated) {
		if (commandSeparated.length < 4 || commandSeparated.length > 5) {
			return false;
		}

		String accountType = commandSeparated[1];
		String accountId = commandSeparated[2];
		String apr = commandSeparated[3];
		String balance = null;

		if (bank.ifAccountExists(accountId)) {
			return false;
		}

		if (Objects.equals(accountType, "cd")) {
			if (commandSeparated.length != 5) {
				return false;
			}
			balance = commandSeparated[4];
		}
		if (Objects.equals(accountType, "cd")) {
			return isValidAccountId(accountId) && isValidApr(apr) && isValidBalance(balance)
					&& (Double.parseDouble(balance) >= 1000 && Double.parseDouble(balance) <= 10000);
		} else {
			if (commandSeparated.length != 4) {
				return false;
			}
			return isValidAccountType(accountType) && isValidAccountId(accountId) && isValidApr(apr);
		}

	}

	public boolean isValidAccountType(String type) {
		return Objects.equals(type, "savings") || Objects.equals(type, "checking") || Objects.equals(type, "cd");
	}

	public boolean isValidApr(String apr) {
		if (apr == null || apr.isEmpty()) {
			return false;
		}

		try {
			double aprValue = Double.parseDouble(apr);
			return aprValue >= 0 && aprValue <= 10;
		} catch (NumberFormatException e) {
			return false;
		}

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
