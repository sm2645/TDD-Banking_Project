import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	String INVALID_COMMAND = "undeposit abcdedfs -500";
	private CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	void invalid_tests_can_be_added_to_command_storage() {

		commandStorage.addInvalidCommand(INVALID_COMMAND);
		assertTrue(commandStorage.getInvalidCommands().contains(INVALID_COMMAND));
	}

	@Test
	void multiple_invalid_tests_can_be_added_to_command_storage() {
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		assertEquals(3, commandStorage.getInvalidCommands().size());
	}

	@Test
	void getInvalidCommands_returns_empty_list_when_no_commands_added() {
		assertTrue(commandStorage.getInvalidCommands().isEmpty());
	}
}
