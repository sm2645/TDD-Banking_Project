package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WithdrawValidatorTest {

	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		bank.addAccount("13345678", new Checking("13345678", 0.01));
		bank.depositToAccount("13345678", 1000.0);
		bank.addAccount("97654321", new Savings("97654321", 0.01));
		bank.depositToAccount("97654321", 1000.0);
		bank.addAccount("18345679", new CD("18345679", 2.5, 1000));

	}

	@Test
	void withdraw_with_missing_parameters_is_invalid() {
		boolean actual = commandValidator.validate("withdraw");
		assertFalse(actual);
	}

	@Test
	void withdraw_with_missing_id_parameters_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 1000"));
	}

	@Test
	void withdraw_with_missing_amount_parameters_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 12345678"));
	}

	@Test
	void withdraw_with_too_many_parameters_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 12345678 3.5 100"));
	}

	@Test
	void withdraw_to_new_cd_account_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 18345679 1000"));
	}

	@Test
	void withdraw_from_13m_old_cd_account_is_valid() {
		bank.incrementAnAccountsAge("18345679", 12);
		assertTrue(commandValidator.validate("withdraw 18345679 1001"));
	}

	@Test
	void withdraw_less_than_balance_to_13m_old_cd_account_is_valid() {
		bank.incrementAnAccountsAge("18345679", 12);
		assertFalse(commandValidator.validate("withdraw 18345679 100"));
	}

	@Test
	void withdraw_balance_to_13m_old_cd_account_is_valid() {
		bank.incrementAnAccountsAge("18345679", 12);
		assertTrue(commandValidator.validate("withdraw 18345679 1000"));
	}

	@Test
	void withdraw_more_than_balance_to_13m_old_cd_account_is_valid() {
		bank.incrementAnAccountsAge("18345679", 12);
		assertTrue(commandValidator.validate("withdraw 18345679 1100"));
	}

	@Test
	void withdraw_exceeding_savings_limit_of_1000_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 97654321 3000"));
	}

	@Test
	void max_of_withdraw_of_1000_is_valid() {
		assertTrue(commandValidator.validate("withdraw 97654321 1000"));
	}

	@Test
	void withdraw_less_than_savings_limit_of_1000_is_valid() {
		assertTrue(commandValidator.validate("withdraw 97654321 999"));
	}

	@Test
	void one_dollar_withdraw_to_savings_is_valid() {
		assertTrue(commandValidator.validate("withdraw 97654321 1"));
	}

	@Test
	void zero_withdraw_to_savings_is_valid() {
		assertTrue(commandValidator.validate("withdraw 97654321 0"));
	}

	@Test
	void negative_withdraw_to_savings_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 97654321 -100"));
	}

	@Test
	void withdraw_exceeding_checking_limit_of_400_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 13345678 401"));
	}

	@Test
	void withdrawing_checking_max_of_400_is_valid() {
		assertTrue(commandValidator.validate("withdraw 13345678 400"));
	}

	@Test
	void withdraw_less_than_checking_accounts_limit_of_400_is_valid() {
		assertTrue(commandValidator.validate("withdraw 97654321 399"));
	}

	@Test
	void one_dollar_withdraw_is_valid() {
		assertTrue(commandValidator.validate("withdraw 97654321 1"));
	}

	@Test
	void zero_withdraw_is_invalid() {
		assertTrue(commandValidator.validate("withdraw 97654321 0"));
	}

	@Test
	void negative_withdraw_to_checking_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 13345678 -1"));
	}

	@Test
	void invalid_account_id_as_text_is_invalid() {
		assertFalse(commandValidator.validate("withdraw invalidId 500"));
	}

	@Test
	void invalid_balance_format_as_text_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 97654321 mclwest"));
	}

	@Test
	void incorrect_account_id_for_withdraw_is_invalid() {
		assertFalse(commandValidator.validate("withdraw 67654321 500"));
	}

}
