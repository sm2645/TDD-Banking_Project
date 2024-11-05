import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateValidatorTest {

	private CreateValidator createValidator;

	@BeforeEach
	void setUp() {
		Bank bank = new Bank();
		CommandValidator commandValidator = new CommandValidator(bank, null, null);
		createValidator = new CreateValidator(bank, commandValidator);
	}

	@Test
	void invalidate_create_commands_with_too_many_parameters() {
		String[] commandSeparated = { "create", "savings", "12345678", "5.0", "5000" };
		assertFalse(createValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_create_commands_with_too_few_parameters() {
		String[] commandSeparated = { "create", "savings", "12345678" };
		assertFalse(createValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_cd_account_invalid_balance_below_minimum() {
		String[] commandSeparated = { "create", "cd", "12345678", "5.0", "500" };
		assertFalse(createValidator.validate(commandSeparated));
	}

	@Test
	void validate_cd_account_invalid_balance_above_maximum() {
		String[] commandSeparated = { "create", "cd", "12345678", "5.0", "15000" };
		assertFalse(createValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_account_with_invalid_account_id() {
		String[] commandSeparated = { "create", "qbcbduc", "1234", "2.5" };
		assertFalse(createValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_account_with_negative_apr() {
		String[] commandSeparated = { "create", "savings", "12345678", "-1.0" };
		assertFalse(createValidator.validate(commandSeparated));
	}

	@Test
	void invalidate_account_with_too_high_apr() {
		String[] commandSeparated = { "create", "savings", "12345678", "18" };
		assertFalse(createValidator.validate(commandSeparated));
	}

	@Test
	void validate_cd_account_with_valid_balance_and_apr() {
		String[] commandSeparated = { "create", "cd", "12345678", "5.0", "5000" };
		assertTrue(createValidator.validate(commandSeparated));
	}

	@Test
	void validate_savings_account_with_valid_apr_and_id() {
		String[] commandSeparated = { "create", "savings", "12345678", "3.0" };
		assertTrue(createValidator.validate(commandSeparated));
	}

	@Test
	void isValidAccountType_valid_types() {
		assertTrue(createValidator.isValidAccountType("savings"));
		assertTrue(createValidator.isValidAccountType("checking"));
		assertTrue(createValidator.isValidAccountType("cd"));
	}

	@Test
	void isValidAccountType_invalid_type() {
		assertFalse(createValidator.isValidAccountType("crypto"));
	}

	@Test
	void isValidApr_valid_min_value() {
		assertTrue(createValidator.isValidApr("0"));
	}

	@Test
	void isValidApr_valid_normal_value() {
		assertTrue(createValidator.isValidApr("5.0"));
	}

	@Test
	void isValidApr_valid_max_value() {
		assertTrue(createValidator.isValidApr("10.0"));
	}

	@Test
	void isValidApr_invalid_negative_value() {
		assertFalse(createValidator.isValidApr("-1"));
	}

	@Test
	void isValidApr_invalid_too_high_value() {
		assertFalse(createValidator.isValidApr("15.0"));
	}

	@Test
	void isValidApr_invalid_apr_as_text() {
		assertFalse(createValidator.isValidApr("abc"));
	}

}
