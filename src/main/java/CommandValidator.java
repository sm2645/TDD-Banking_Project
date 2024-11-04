public class CommandValidator {
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] commandSeparated = command.split(" ");
		if (commandSeparated.length < 3) {
			return false;
		}

		String function = commandSeparated[0];

		return switch (function) {
		case "create" -> validateCreate(commandSeparated);
		case "deposit" -> validateDeposit(commandSeparated);
		default -> false;
		};

	}
}
