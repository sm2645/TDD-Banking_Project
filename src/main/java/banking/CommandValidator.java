package banking;

public class CommandValidator {
	// add withdraw TO DO
	private final CreateValidator createValidator;
	private final DepositValidator depositValidator;
//	private final WithdrawValidator withdrawValidator;

	public CommandValidator(Bank bank, CreateValidator createValidator, DepositValidator depositValidator) {
		this.createValidator = createValidator;
		this.depositValidator = depositValidator;
	}

	public boolean validate(String command) {
		command = command.toLowerCase();
		String[] commandSeparated = command.split(" ");
		if (commandSeparated.length < 3) {
			return false;
		}

		String functionPurpose = commandSeparated[0];

		switch (functionPurpose) {
		case "create":
			return createValidator.validate(commandSeparated);
		case "deposit":
			return depositValidator.validate(commandSeparated);
//		case "withdraw":
//			return withdrawValidator.validate(commandSeparated);
		default:
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
