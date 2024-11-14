import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

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
		assertEquals(INVALID_COMMAND, commandStorage.getInvalidCommands().get(0));
	}

	@Test
	void multiple_invalid_tests_can_be_added_to_command_storage() {
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		assertEquals(3, commandStorage.getInvalidCommands().size());
	}

	@Test
	void multiple_diffrent_invalid_tests_are_all_stored_in_command_storage() {
		commandStorage.addInvalidCommand("undeposit abcdedfs -500");
		commandStorage.addInvalidCommand("undeposits acsbcdedfs -500");

		String expected = "undeposit abcdedfs -500 undeposits acsbcdedfs -500";
		String actual = String.join(" ", commandStorage.getInvalidCommands());

		assertEquals(expected, actual);
	}

	@Test
	void getInvalidCommands_returns_empty_list_when_no_commands_added() {
		assertTrue(commandStorage.getInvalidCommands().isEmpty());
	}

	@Test
	void getInvalidCommands_returns_list_with_every_invalid_command_added() {
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		commandStorage.addInvalidCommand(INVALID_COMMAND);
		commandStorage.addInvalidCommand("INVALID_COMMAND");
		commandStorage.addInvalidCommand("INVALIDCOMMAND");
		ArrayList<String> invalidCommandsList = new ArrayList<>(
				Arrays.asList(INVALID_COMMAND, INVALID_COMMAND, "INVALID_COMMAND", "INVALIDCOMMAND"));

		assertEquals(invalidCommandsList, commandStorage.getInvalidCommands());
	}
}
