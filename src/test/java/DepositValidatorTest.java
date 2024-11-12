import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepositValidatorTest {

	CommandValidator commandValidator;
	DepositValidator depositValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank, null, null);
		depositValidator = new DepositValidator(bank, commandValidator);
		commandValidator = new CommandValidator(bank, null, depositValidator);
		bank.addAccount("13345678", new Checking("13345678", 0.01));
		bank.addAccount("97654321", new Savings("97654321", 0.01));
		bank.addAccount("18345679", new CD("18345679", 2.5, 1000));
	}

	@Test
	void invalidate_commands_missing_id_parameters() {
		assertFalse(commandValidator.validate("deposit 12345678"));
	}

	@Test
	void invalidate_command_with_too_many_parameters() {
		assertFalse(commandValidator.validate("deposit 12345678 3.5 100"));
	}

	@Test
	void invalidate_deposit_to_cd_account() {
		assertFalse(commandValidator.validate("deposit 18345679 500"));
	}

	@Test
	void invalidate_deposits_exceeding_deposit_limit_for_savings() {
		assertFalse(commandValidator.validate("deposit 97654321 3000"));
	}

	@Test
	void invalidate_deposit_exceeding_limit_for_checking() {
		assertFalse(commandValidator.validate("deposit 13345678 2000"));
	}

	@Test
	void invalidate_negative_deposit_to_checking() {
		assertFalse(commandValidator.validate("deposit 13345678 -100"));
	}

	@Test
	void invalidate_invalid_account_id() {
		assertFalse(commandValidator.validate("deposit invalidId 500"));
	}

	@Test
	void invalidate_invalid_balance_format() {
		assertFalse(commandValidator.validate("deposit 97654321 abc"));
	}

	@Test
	void validate_correct_deposit_to_checking() {
		assertTrue(commandValidator.validate("deposit 13345678 500"));
	}

	@Test
	void validate_proper_deposit_to_savings() {
		assertTrue(commandValidator.validate("deposit 97654321 1000"));
	}

	@Test
	void attempt_to_deposit_into_cd_invalid_test() {
		assertFalse(commandValidator.validate("deposit 18345679 500"));
	}

	@Test
	void exceeds_savings_deposit_limit_invalid_test() {
		assertFalse(commandValidator.validate("deposit 97654321 3000"));
	}

	@Test
	void exceeds_checking_deposit_limit_invalid_test() {
		assertFalse(commandValidator.validate("deposit 13345678 2000"));
	}

	@Test
	void invalid_account_id_invalid_test() {
		assertFalse(commandValidator.validate("deposit 8765432 500"));
	}

	@Test
	void non_number_parameters_invalid_test() {
		assertFalse(commandValidator.validate("deposit 97654321 mclwest"));
	}

	@Test
	void valid_deposit_checking_valid_test() {
		assertTrue(commandValidator.validate("deposit 13345678 500"));
	}

	@Test
	void valid_deposit_savings_valid_test() {
		assertTrue(commandValidator.validate("deposit 97654321 200"));
	}

	@Test
	void zero_deposit_valid_test() {
		assertTrue(commandValidator.validate("deposit 97654321 0"));
	}
}
