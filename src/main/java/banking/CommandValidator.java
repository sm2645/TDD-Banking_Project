package banking;

public class CommandValidator {
	// add withdraw TO DO
	private final CreateValidator createValidator;
	private final DepositValidator depositValidator;
	private final WithdrawValidator withdrawValidator;
	private final TransferValidator transferValidator;
	private final PassTimeValidator passTimeValidator;

	public CommandValidator(Bank bank) {
		this.createValidator = new CreateValidator(bank);
		this.depositValidator = new DepositValidator(bank);
		this.withdrawValidator = new WithdrawValidator(bank);
		this.transferValidator = new TransferValidator(bank);
		this.passTimeValidator = new PassTimeValidator();
	}

	public boolean validate(String command) {
		command = command.toLowerCase();
		String[] commandSeparated = command.split(" ");
		if (commandSeparated.length < 2) {
			return false;
		}

		String functionPurpose = commandSeparated[0];

		switch (functionPurpose) {
		case "create":
			return createValidator.validate(commandSeparated);
		case "deposit":
			return depositValidator.validate(commandSeparated);
		case "withdraw":
			return withdrawValidator.validate(commandSeparated);
		case "transfer":
			return transferValidator.validate(commandSeparated);
		case "pass":
			return passTimeValidator.validate(commandSeparated);
		default:
			return false;
		}
	}

}
