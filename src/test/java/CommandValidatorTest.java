import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	CreateValidator createValidator;
	DepositValidator depositValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank, null, null);
		createValidator = new CreateValidator(bank, commandValidator);
		depositValidator = new DepositValidator(bank, commandValidator);
		commandValidator = new CommandValidator(bank, createValidator, depositValidator);
		bank.addAccount("13345678", new Checking("13345678", 0.01));
		bank.addAccount("97654321", new Savings("97654321", 0.01));
		bank.addAccount("18345679", new CD("18345679", 2.5, 1000));

	}

	@Test
	void valid_command() {
		boolean actual = commandValidator.validate("create savings 12345676 0.5");
		assertTrue(actual);
	}

//Apr tests
	@Test
	void checking_apr_as_text_invalid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 wowweee");
		assertFalse(actual);
	}

	@Test
	void checking_negative_apr_invalid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void checking_too_high_apr_invalid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 11");
		assertFalse(actual);
	}

	@Test
	void checking_zero_apr_valid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 0");
		assertTrue(actual);
	}

	@Test
	void checking_normal_apr_valid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 7");
		assertTrue(actual);
	}

	@Test
	void max_apr_valid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 10");
		assertTrue(actual);
	}

//ID tests
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
	// Balance for CD

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
	void savings_balance_specified_valid_test() {
		boolean actual = commandValidator.validate("create savings 87654321 0.5 300");
		assertFalse(actual);
	}

	@Test
	void test_checking_balance_specified_valid_test() {
		boolean actual = commandValidator.validate("create checking 12345678 0.5 500");
		assertFalse(actual);
	}

//deposit
	@Test
	void attempt_to_deposit_into_cd_invalid_test() {
		boolean actual = commandValidator.validate("deposit 18345679 500");
		assertFalse(actual);
	}

	@Test
	void exceeds_savings_deposit_limit_invalid_test() {
		boolean actual = commandValidator.validate("deposit 97654321 3000");
		assertFalse(actual);
	}

	@Test
	void exceeds_checking_deposit_limit_invalid_test() {
		boolean actual = commandValidator.validate("deposit 13345678 1500");
		assertFalse(actual);
	}

	@Test
	void invalid_account_id_invalid_test() {
		boolean actual = commandValidator.validate("deposit 8765432 500");
		assertFalse(actual);
	}

	@Test
	void non_number_parameters_invalid_test() {
		boolean actual = commandValidator.validate("deposit 13345678 mclwest");
		assertFalse(actual);
	}

	@Test
	void valid_deposit_checking_valid_test() {
		boolean actual = commandValidator.validate("deposit 13345678 500");
		assertTrue(actual);
	}

	@Test
	void valid_deposit_savings_valid_test() {
		boolean actual = commandValidator.validate("deposit 97654321 200");
		assertTrue(actual);
	}

	@Test
	void zero_deposit_valid_test() {
		boolean actual = commandValidator.validate("deposit 13345678 0");
		assertTrue(actual);
	}
//Missing parameters
	// Missing Apr and ID is above in its respective sections

	@Test
	void missing_parameters_invalid_test() {
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

}