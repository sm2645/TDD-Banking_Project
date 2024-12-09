package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private final CommandValidator commandValidator;
	private final CommandProcessor commandProcessor;
	private final CommandStorage commandStorage;
	private final Bank bank;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage, Bank bank) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
		this.bank = bank;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.process(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}
		List<String> output = new ArrayList<>();
		output.addAll(bank.formattedAccDetails()); // This will now return accounts in the creation order
		output.addAll(commandStorage.getInvalidCommands());
		return output;
	}
}
