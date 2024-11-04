import java.util.Objects;

public class CommandValidator {
	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private Bank bank;

	public CommandValidator(Bank bank, CreateValidator createValidator, DepositValidator depositValidator) {
		this.bank = bank;
		this.createValidator = createValidator;
		this.depositValidator = depositValidator;
	}

	public boolean validate(String command) {
		String[] commandSeparated = command.split(" ");
		if (commandSeparated.length < 3) {
			return false;
		}

		String functionPurpose = commandSeparated[0];

		return switch (functionPurpose) {
		case "create" -> createValidator.validate(commandSeparated);
		case "deposit" -> depositValidator.validate(commandSeparated);
		default -> false;
		};
	}

	public boolean isValidAccountType(String type) {
		return Objects.equals(type, "savings") || Objects.equals(type, "checking") || Objects.equals(type, "cd");
	}

}
