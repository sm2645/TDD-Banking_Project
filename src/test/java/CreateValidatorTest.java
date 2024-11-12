import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {
	CommandValidator commandValidator;
	CreateValidator createValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank, null, null);
		createValidator = new CreateValidator(bank, commandValidator);
		commandValidator = new CommandValidator(bank, createValidator, null);
	}

	@Test
	void valid_command() {
		boolean actual = commandValidator.validate("create savings 12345676 0.5");
		assertTrue(actual);
	}

	@Test
	void create_with_apr_as_text_is_invalid() {
		boolean actual = commandValidator.validate("create checking 12345678 wowweee");
		assertFalse(actual);
	}

	@Test
	void apr_as_negative_is_invalid() {
		boolean actual = commandValidator.validate("create checking 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void apr_higher_than_max_is_invalid() {
		boolean actual = commandValidator.validate("create checking 12345678 11");
		assertFalse(actual);
	}

	@Test
	void apr_one_less_than_max_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 9");
		assertTrue(actual);
	}

	@Test
	void apr_within_the_bounds_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 5");
		assertTrue(actual);
	}

	@Test
	void apr_one_above_the_minimum_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 1");
		assertTrue(actual);
	}

	@Test
	void apr_as_zero_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 0");
		assertTrue(actual);
	}

	@Test
	void apr_of_maximum_value_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 10");
		assertTrue(actual);
	}

	@Test
	void apr_that_not_a_whole_number_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 2.2");
		assertTrue(actual);
	}

	@Test
	void no_id_provided_invalid_test() {
		boolean actual = commandValidator.validate("create cd 7 1100");
		assertFalse(actual);
	}

	@Test
	void duplicate_id_is_invalid() {
		bank.addAccount("12345678", new Savings("12345678", 0.5));
		boolean actual = commandValidator.validate("create savings 12345678 0.5");
		assertFalse(actual);
	}

	@Test
	void text_as_id_invalid_test() {
		boolean actual = commandValidator.validate("create cd Efcecell 7 1100");
		assertFalse(actual);
	}

	@Test
	void too_long_id_length_invalid_test() {
		boolean actual = commandValidator.validate("create cd 123456789 7 1100");
		assertFalse(actual);
	}

	@Test
	void too_short_of_id_length_invalid_test() {
		boolean actual = commandValidator.validate("create cd 123456 7 1100");
		assertFalse(actual);
	}

	@Test
	void normal_id_valid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 7 1100");
		assertTrue(actual);
	}

	@Test
	void cd_balance_below_minimum_invalid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 999");
		assertFalse(actual);
	}

	@Test
	void cd_balance_above_maximum_invalid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 10001");
		assertFalse(actual);
	}

	@Test
	void cd_negative_balance_invalid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 -1000");
		assertFalse(actual);
	}

	@Test
	void cd_balance_not_specified_invalid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5");
		assertFalse(actual);
	}

	@Test
	void cd_balance_as_text_invalid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 cewe");
		assertFalse(actual);
	}

	@Test
	void cd_balance_minimum_valid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 1000");
		assertTrue(actual);
	}

	@Test
	void cd_balance_within_normal_range_valid_test() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 5000");
		assertTrue(actual);
	}

	@Test
	void savings_initiated_with_a_balance_is_invalid_test() {
		boolean actual = commandValidator.validate("create savings 87654321 0.5 300");
		assertFalse(actual);
	}

	@Test
	void checking_initiated_with_a_balance_is_invalid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 0.5 500");
		assertFalse(actual);
	}

	@Test
	void missing_parameters_is_an_invalid_test() {
		boolean actual = commandValidator.validate("deposit");
		assertFalse(actual);
	}

	@Test
	void missing_create_parameter_invalid_test() {
		boolean actual = commandValidator.validate("cd 12345678 2.5");
		assertFalse(actual);
	}

	@Test
	void missing_account_type_invalid_test() {
		boolean actual = commandValidator.validate("create 12345678 2.5");
		assertFalse(actual);
	}

	@Test
	void missing_apr_for_account_type_that_requires_it_is_invalid() {
		boolean actual = commandValidator.validate("create checking 12345678");
		assertFalse(actual);
	}

	@Test
	void invalid_account_type_is_invalid() {
		boolean actual = commandValidator.validate("create unknown 12345678 2.5");
		assertFalse(actual);
	}
}