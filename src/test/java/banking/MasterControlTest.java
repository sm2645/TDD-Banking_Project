package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	List<String> input, expected;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		expected = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage(),
				bank);
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

		expected.add("Checking 12345678 0.00 1.00");
		expected.add("create checking 12345678 1.0");
		assertEquals(expected, actual);
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

	@Test
	void multiple_commands_with_invalid_and_valid_tests() {
		input.add("create savings 12345678 0.6");
		input.add("deposit 12345678 700");
		input.add("deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("deposit 98765432 300");
		input.add("transfer 98765432 12345678 300");
		input.add("pass 1");
		input.add("doesnt work");
		input.add("create cd 23456789 1.2 2000");

		List<String> actual = masterControl.start(input);

		expected.add("Savings 12345678 1000.50 0.60");
		expected.add("deposit 12345678 700");
		expected.add("transfer 98765432 12345678 300");
		expected.add("Cd 23456789 2000.00 1.20");
		expected.add("deposit 12345678 5000");
		expected.add("doesnt work");

		assertEquals(expected, actual);
	}

	@Test
	void transfer_from_checking_to_savings() {
		input.add("create checking 12345678 1.0");
		input.add("create savings 98765432 1.5");
		input.add("deposit 12345678 1000");
		input.add("transfer 12345678 98765432 200");

		List<String> actual = masterControl.start(input);
		expected.add("Checking 12345678 800.00 1.00");
		expected.add("deposit 12345678 1000");
		expected.add("transfer 12345678 98765432 200");
		expected.add("Savings 98765432 200.00 1.50");
		expected.add("transfer 12345678 98765432 200");

		assertEquals(expected, actual);
	}

	@Test
	void transfer_from_checking_to_cd_is_invalid() {
		input.add("create checking 12345678 1.0");
		input.add("create cd 98765432 2.0 5000");
		input.add("deposit 12345678 1000");
		input.add("transfer 12345678 98765432 500");

		List<String> actual = masterControl.start(input);
		expected.add("Checking 12345678 1000.00 1.00");
		expected.add("deposit 12345678 1000");
		expected.add("Cd 98765432 5000.00 2.00");
		expected.add("transfer 12345678 98765432 500");

		assertEquals(expected, actual);
	}

}
