package banking;

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
	void deposit_with_missing_parameters_is_invalid() {
		boolean actual = commandValidator.validate("deposit");
		assertFalse(actual);
	}

	@Test
	void deposit_with_missing_id_parameters_is_invalid() {
		assertFalse(commandValidator.validate("deposit 12345678"));
	}

	@Test
	void deposit_with_too_many_parameters_is_invalid() {
		assertFalse(commandValidator.validate("deposit 12345678 3.5 100"));
	}

	@Test
	void deposit_to_cd_account_is_invalid() {
		assertFalse(commandValidator.validate("deposit 18345679 500"));
	}

	@Test
	void deposit_exceeding_savings_limit_of_2500_is_invalid() {
		assertFalse(commandValidator.validate("deposit 97654321 3000"));
	}

	@Test
	void max_of_deposit_of_2500_is_valid() {
		assertTrue(commandValidator.validate("deposit 97654321 2500"));
	}

	@Test
	void deposit_less_than_savings_limit_of_2500_is_valid() {
		assertTrue(commandValidator.validate("deposit 97654321 2499"));
	}

	@Test
	void negative_deposit_to_savings_is_invalid() {
		assertFalse(commandValidator.validate("deposit 97654321 -100"));
	}

	@Test
	void deposit_exceeding_checking_limit_of_1000_is_invalid() {
		assertFalse(commandValidator.validate("deposit 13345678 2000"));
	}

	@Test
	void depositing_checking_max_of_1000_is_valid() {
		assertTrue(commandValidator.validate("deposit 13345678 1000"));
	}

	@Test
	void deposit_less_than_checking_accounts_limit_of_1000_is_valid() {
		assertTrue(commandValidator.validate("deposit 97654321 999"));
	}

	@Test
	void zero_deposit_is_valid() {
		assertTrue(commandValidator.validate("deposit 97654321 0"));
	}

	@Test
	void negative_deposit_to_checking_is_invalid() {
		assertFalse(commandValidator.validate("deposit 13345678 -100"));
	}

	@Test
	void invalid_account_id_is_invalid() {
		assertFalse(commandValidator.validate("deposit invalidId 500"));
	}

	@Test
	void invalid_balance_format_is_invalid() {
		assertFalse(commandValidator.validate("deposit 97654321 mclwest"));
	}

	@Test
	void incorrect_account_id_for_deposit_is_invalid() {
		assertFalse(commandValidator.validate("deposit 8765432 500"));
	}

}
