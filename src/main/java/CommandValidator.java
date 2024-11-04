public class CommandValidator {
	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		this.createValidator = CreateValidator(bank);
		this.depositValidator = DepositValidator(bank);
	}

	public boolean validate(String command) {
		String[] commandSeparated = command.split(" ");
		if (commandSeparated.length < 3) {
			return false;
		}

		String functionPurpose = commandSeparated[0];

		return switch (functionPurpose) {
		case "create" -> validateCreate(commandSeparated);
		case "deposit" -> validateDeposit(commandSeparated);
		default -> false;
		};

	}
}
