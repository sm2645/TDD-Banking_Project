package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	private final List<String> invalidCommands;

	public CommandStorage() {
		this.invalidCommands = new ArrayList<>();
	}

	public void addInvalidCommand(String invalidCommand) {
		invalidCommands.add(invalidCommand);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}
}
