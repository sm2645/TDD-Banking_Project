package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	List<String> input;
	private Bank bank;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		bank = new Bank();

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

	@Test
	void multiple_commands_test() {
		// Adding input commands to the list
		input.add("create savings 12345678 0.6"); // create a new savings account
		input.add("deposit 12345678 700"); // deposit $700 into savings
		input.add("deposit 12345678 5000"); // invalid, amount too high
		input.add("creAte cHecKing 98765432 0.01"); // case insensitive command
		input.add("deposit 98765432 300"); // deposit $300 into checking
		input.add("transfer 98765432 12345678 300"); // transfer $300 from checking to savings
		input.add("pass 1"); // Pass 1 month of time
		input.add("create cd 23456789 1.2 2000"); // create a new CD with 1.2% APR $2000

		// Execute commands through MasterControl
		List<String> actual = masterControl.start(input);

		// Expected output after processing the commands
		List<String> expected = new ArrayList<>();
		expected.add("Savings 12345678 1000.50 0.60"); // current state of savings account
		expected.add("deposit 12345678 700"); // transaction history for savings account
		expected.add("transfer 98765432 12345678 300"); // transaction history for checking account
		expected.add("Cd 23456789 2000.00 1.20"); // current state of CD account
		expected.add("deposit 12345678 5000"); // invalid command due to amount too high

		// Assert that the output matches the expected result
		assertEquals(expected, actual);
	}
}
