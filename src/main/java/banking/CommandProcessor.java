package banking;

public class CommandProcessor {
	private final CreateCommandProcessor createProcessor;
	private final DepositCommandProcessor depositProcessor;

	public CommandProcessor(Bank bank) {
		this.createProcessor = new CreateCommandProcessor(bank);
		this.depositProcessor = new DepositCommandProcessor(bank);
	}

	public void process(String command) {
		command = command.toLowerCase();
		String[] commandSeparated = command.split(" ");
		String functionPurpose = commandSeparated[0];

		switch (functionPurpose) {
		case "create":
			createProcessor.process(commandSeparated);
			break;
		case "deposit":
			depositProcessor.process(commandSeparated);
			break;
		default:
			break;
		}
	}
}
