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

		if (bank.ifAccountExists(accountId)) {
			return false;
		}

		if (Objects.equals(accountType, "cd")) {
			return validateCdAccount(commandSeparated, accountId, apr);
		} else {
			return validateNonCdAccount(commandSeparated, accountType, accountId, apr);
		}
	}

	private boolean validateCdAccount(String[] commandSeparated, String accountId, String apr) {
		if (commandSeparated.length != 5) {
			return false;
		}

		String balance = commandSeparated[4];
		return bank.isValidAccountId(accountId) && bank.isValidApr(apr) && bank.isValidAmount(balance)
				&& (Double.parseDouble(balance) >= 1000 && Double.parseDouble(balance) <= 10000);
	}

	private boolean validateNonCdAccount(String[] commandSeparated, String accountType, String accountId, String apr) {
		if (commandSeparated.length != 4) {
			return false;
		}

		return isValidAccountType(accountType) && bank.isValidAccountId(accountId) && bank.isValidApr(apr);
	}

	public boolean isValidAccountType(String type) {
		return Objects.equals(type, "savings") || Objects.equals(type, "checking") || Objects.equals(type, "cd");
	}
}
