import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepositValidatorTest {

	private DepositValidator depositValidator;

	@BeforeEach
	void setUp() {
		Bank bank = new Bank();
		CommandValidator commandValidator = new CommandValidator(bank, null, null);
		depositValidator = new DepositValidator(bank, commandValidator);

		Checking checkingAccount = new Checking("87654321", 1.0);
		Savings savingsAccount = new Savings("12345678", 1.5);
		CD cdAccount = new CD("12345679", 5.0, 5000);
		bank.addAccount("87654321", checkingAccount);
		bank.addAccount("12345678", savingsAccount);
		bank.addAccount("12345679", cdAccount);
	}

	@Test
	void invalidate_incorrect_command_length_missing_parameter() {
		String[] commandSeparated = { "deposit", "12345678" };
		assertFalse(depositValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_incorrect_command_length_too_many_parameters() {
		String[] commandSeparated = { "deposit", "12345678", "3.5", "100" };
		assertFalse(depositValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_deposit_to_cd_account() {
		String[] commandSeparated = { "deposit", "12345679", "500" };
		assertFalse(depositValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_deposits_exceeding_deposit_limit_for_savings() {
		String[] commandSeparated = { "deposit", "12345678", "3000" };
		assertFalse(depositValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_deposit_exceeding_limit_for_checking() {
		String[] commandSeparated = { "deposit", "87654321", "1550" };
		assertFalse(depositValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_invalid_account_id() {
		String[] commandSeparated = { "deposit", "invalidId", "500" };
		assertFalse(depositValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_invalid_balance_format() {
		String[] commandSeparated = { "deposit", "87654321", "abc" };
		assertFalse(depositValidator.validate(commandSeparated));
	}

	@Test
	void validate_correct_deposit_to_checking() {
		String[] commandSeparated = { "deposit", "87654321", "500" };
		assertTrue(depositValidator.validate(commandSeparated));
	}

	@Test
	void validate_proper_deposit_to_savings() {
		String[] commandSeparated = { "deposit", "12345678", "1000" };
		assertTrue(depositValidator.validate(commandSeparated));
	}

}
