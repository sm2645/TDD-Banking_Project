package banking;

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

//ID tests
	@Test
	void no_id_provided_invalid_test() {
		boolean actual = commandValidator.validate("create cd 7 1100");
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
	// Balance for banking.CD

	@Test
	void create_cd_balance_as_text_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 cewe");
		assertFalse(actual);
	}

	@Test
	void create_cd_balance_as_negative_is_invalid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 -100");
		assertFalse(actual);
	}

	@Test
	void create_cd_balance_within_normal_range_is_valid() {
		boolean actual = commandValidator.validate("create cd 12345678 2.5 5000");
		assertTrue(actual);
	}

//bal deposit
	@Test
	void invalid_deposit_amount_as_text_is_invalid() {
		boolean actual = commandValidator.validate("deposit 13345678 abc");
		assertFalse(actual);
	}

	@Test
	void deposit_amount_as_negative_is_invalid() {
		boolean actual = commandValidator.validate("deposit 13345678 -100");
		assertFalse(actual);
	}

	@Test
	void deposit_amount_within_normal_range_is_valid() {
		boolean actual = commandValidator.validate("deposit 13345678 200");
		assertTrue(actual);

	}

	@Test
	void valid_create_command() {
		boolean actual = commandValidator.validate("create savings 12345676 0.5");
		assertTrue(actual);
	}

	@Test
	void invalid_create_command_missing_balance() {
		boolean actual = commandValidator.validate("create savings 12345676");
		assertFalse(actual);
	}

	@Test
	void valid_deposit_command() {
		boolean actual = commandValidator.validate("deposit 13345678 500");
		assertTrue(actual);
	}

	@Test
	void invalid_deposit_command_missing_amount() {
		boolean actual = commandValidator.validate("deposit 13345678");
		assertFalse(actual);
	}

	@Test
	void invalid_command_with_unknown_function() {
		boolean actual = commandValidator.validate("unknown 12345678 500");
		assertFalse(actual);
	}

	@Test
	void valid_command_with_case_insensitive_account_type() {
		boolean actual = commandValidator.validate("create SaVinGs 12345678 0.5");
		assertTrue(actual);
	}

	@Test
	void missing_parameters_invalid_test() {
		boolean actual = commandValidator.validate("deposit");
		assertFalse(actual);
	}

}