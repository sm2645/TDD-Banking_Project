package banking;

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
	void apr_as_zero_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 0");
		assertTrue(actual);
	}

	@Test
	void apr_one_above_the_minimum_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 1");
		assertTrue(actual);
	}

	@Test
	void apr_within_the_bounds_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 5");
		assertTrue(actual);
	}

	@Test
	void apr_one_less_than_max_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 9");
		assertTrue(actual);
	}

	@Test
	void apr_of_maximum_value_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 10");
		assertTrue(actual);
	}

	@Test
	void apr_higher_than_max_is_invalid() {
		boolean actual = commandValidator.validate("create checking 12345678 11");
		assertFalse(actual);
	}

	@Test
	void apr_that_is_not_a_whole_number_is_valid() {
		boolean actual = commandValidator.validate("create checking 12345678 2.2");
		assertTrue(actual);
	}

	@Test
	void create_checking_account_with_valid_apr() {
		boolean actual = commandValidator.validate("create checking 12345678 5");
		assertTrue(actual);
	}

	@Test
	void create_savings_account_with_valid_apr() {
		boolean actual = commandValidator.validate("create savings 12345678 3");
		assertTrue(actual);
	}

	@Test
	void create_CD_account_with_valid_apr_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 7");
		assertFalse(actual);
	}

	@Test
	void create_with_no_id_provided_is_invalid() {
		boolean actual = commandValidator.validate("create cd 7 1100");
		assertFalse(actual);
	}

	@Test
	void create_with_duplicate_id_is_invalid() {
		bank.addAccount("12345678", new Savings("12345678", 0.5));
		boolean actual = commandValidator.validate("create savings 12345678 0.5");
		assertFalse(actual);
	}

	@Test
	void create_with_text_as_id_is_invalid() {
		boolean actual = commandValidator.validate("create cd Efcecell 7 1100");
		assertFalse(actual);
	}

	@Test
	void create_with_nine_digit_id_is_invalid() {
		boolean actual = commandValidator.validate("create cd 123456789 7 1100");
		assertFalse(actual);
	}

	@Test
	void create_with_seven_digit_id_is_invalid() {
		boolean actual = commandValidator.validate("create cd 123456 7 1100");
		assertFalse(actual);
	}

	@Test
	void create_with_normal_eight_digit_id_is_valid() {
		boolean actual = commandValidator.validate("create cd 12345678 7 1100");
		assertTrue(actual);
	}

	// cd

	@Test
	void create_cd_without_balance_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5");
		assertFalse(actual);
	}

	@Test
	void create_cd_with_balance_as_text_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 cewe");
		assertFalse(actual);
	}

	@Test
	void create_cd_with_negative_balance_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 -1000");
		assertFalse(actual);
	}

	@Test
	void create_cd_with_balance_below_1000_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 999");
		assertFalse(actual);
	}

	@Test
	void create_cd_with_balance_at_minimum_is_valid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 1000");
		assertTrue(actual);
	}

	@Test
	void create_cd_with_balance_within_normal_range_is_valid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 5000");
		assertTrue(actual);
	}

	@Test
	void create_cd_with_balance_at_maximum_is_valid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 10000");
		assertTrue(actual);
	}

	@Test
	void create_cd_with_balance_above_10000_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 10001");
		assertFalse(actual);
	}

//savings

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
	void create_with_missing_parameters_is_invalid() {
		boolean actual = commandValidator.validate("create");
		assertFalse(actual);
	}

	@Test
	void missing_create_parameter_is_invalid() {
		boolean actual = commandValidator.validate("cd 12345678 2.5");
		assertFalse(actual);
	}

	@Test
	void create_with_missing_account_type_is_invalid() {
		boolean actual = commandValidator.validate("create 12345678 2.5");
		assertFalse(actual);
	}

	@Test
	void create_with_missing_apr_is_invalid() {
		boolean actual = commandValidator.validate("create checking 12345678");
		assertFalse(actual);
	}

	@Test
	void create_with_invalid_account_type_is_invalid() {
		boolean actual = commandValidator.validate("create unknown 12345678 2.5");
		assertFalse(actual);
	}

	@Test
	void account_type_is_case_insensitive_for_create() {
		boolean actual = commandValidator.validate("create SaVinGs 12345678 2.5");
		assertTrue(actual);
	}

}