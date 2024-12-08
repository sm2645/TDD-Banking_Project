package banking;

public class CommandProcessor {
	private final CreateCommandProcessor createProcessor;
	private final DepositCommandProcessor depositProcessor;
	private final WithdrawCommandProcessor withdrawProcessor;
	private final TransferComandProcessor transferProcessor;
	private final PassTimeCommandProcessor passTimeProcessor;

	public CommandProcessor(Bank bank) {
		this.createProcessor = new CreateCommandProcessor(bank);
		this.depositProcessor = new DepositCommandProcessor(bank);
		this.withdrawProcessor = new WithdrawCommandProcessor(bank);
		this.transferProcessor = new TransferComandProcessor(bank);
		this.passTimeProcessor = new PassTimeCommandProcessor(bank);
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
		case "withdraw":
			withdrawProcessor.process(commandSeparated);
			break;
		case "transfer":
			transferProcessor.process(commandSeparated);
			break;
		case "pass":
			passTimeProcessor.process(commandSeparated);
		default:
			break;
		}
	}
}
