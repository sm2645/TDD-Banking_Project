package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	List<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();

		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertSingleCommand("depositt 12345678 100", actual);
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
	}

	@Test
	void invalid_to_create_accounts_with_same_ID() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create checking 12345678 1.0", actual);
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

// withdraw
	@Test
	void typo_in_withdraw_command_is_invalid() {
		input.add("withdaw 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertSingleCommand("withdaw 12345678 100", actual);
	}

	@Test
	void withdraw_over_checking_limit_is_invalid() {
		input.add("withdraw 12345678 401");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertSingleCommand("withdraw 12345678 401", actual);
	}
}
