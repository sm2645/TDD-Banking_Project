package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferValidatorTest {

	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);

		bank.addAccount("12345678", new Checking("12345678", 0.01));
		bank.depositToAccount("12345678", 1000.0);
		bank.addAccount("12345679", new Checking("12345679", 0.01));
		bank.depositToAccount("12345678", 1000.0);

		bank.addAccount("87654321", new Savings("87654321", 0.01));
		bank.depositToAccount("87654321", 1000.0);
		bank.addAccount("87654329", new Savings("87654329", 0.01));
		bank.depositToAccount("87654321", 1000.0);

		bank.addAccount("22345678", new CD("22345678", 2.5, 1000));
		bank.addAccount("22345679", new CD("22345679", 2.5, 1000));
	}

	@Test
	void transfer_with_missing_parameters_is_invalid() {
		assertFalse(commandValidator.validate("transfer"));
	}

	@Test
	void transfer_with_both_ids_missing_is_invalid() {
		assertFalse(commandValidator.validate("transfer 200"));
	}

	@Test
	void transfer_with_a_missing_id_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 200"));
	}

	@Test
	void transfer_with_missing_amount_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 87654321"));
	}

	@Test
	void transfer_with_wrong_sender_account_id_is_invalid() {
		assertFalse(commandValidator.validate("transfer 92839938 87654321 100"));
	}

	@Test
	void transfer_with_senders_id_as_text_is_invalid() {
		assertFalse(commandValidator.validate("transfer invalidId 12345678 100"));
	}

	@Test
	void transfer_with_wrong_receiver_account_id_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 92839938 100"));
	}

	@Test
	void transfer_with_receivers_id_as_text_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 invalidId 100"));
	}

	@Test
	void transfer_with_amount_as_text_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 87654321 Seventy"));
	}

	@Test
	void transfer_with_amount_as_negative_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 87654321 -350"));
	}

	@Test
	void transfer_from_cd_account_is_invalid() {
		assertFalse(commandValidator.validate("transfer 22345678 87654321 100"));
	}

	@Test
	void transfer_to_cd_account_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 22345679 100"));
	}

	@Test
	void transfer_from_cd_to_cd_account_is_invalid() {
		assertFalse(commandValidator.validate("transfer 22345678 22345679 1000"));
	}

	@Test
	void transfer_from_savings_to_savings_exceeding_limit_is_invalid() {
		assertFalse(commandValidator.validate("transfer 87654321 87654329 1001"));
	}

	@Test
	void transfer_from_savings_to_savings_at_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 87654321 87654329 1000"));
	}

	@Test
	void transfer_from_savings_to_savings_one_dollar_less_than_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 87654321 87654329 999"));
	}

	@Test
	void transfer_zero_from_savings_to_savings_is_valid() {
		assertTrue(commandValidator.validate("transfer 87654321 87654329 0"));
	}

	@Test
	void transfer_a_negative_amount_from_savings_to_savings_is_invalid() {
		assertFalse(commandValidator.validate("transfer 87654321 87654329 -1"));
	}

	@Test
	void transfer_from_savings_to_savings_with_prior_withdraw_for_sender_is_invalid() {
		((Savings) bank.retrieveAccount("87654321")).changeWithdrawalStatus();
		assertFalse(commandValidator.validate("transfer 87654321 87654329 1000"));
	}

	@Test
	void transfer_from_savings_to_savings_with_prior_withdraw_for_receiver_is_valid() {
		((Savings) bank.retrieveAccount("87654329")).changeWithdrawalStatus();
		assertTrue(commandValidator.validate("transfer 87654321 87654329 1000"));
	}

	@Test
	void transfer_from_savings_to_checking_exceeding_limit_is_invalid() {
		assertFalse(commandValidator.validate("transfer 87654321 12345678 1001"));
	}

	@Test
	void transfer_from_savings_to_checking_at_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 87654321 12345678 1000"));
	}

	@Test
	void transfer_from_savings_to_checking_one_dollar_less_than_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 87654321 12345678 999"));
	}

	@Test
	void transfer_zero_from_savings_to_checking_is_valid() {
		assertTrue(commandValidator.validate("transfer 87654321 12345678 0"));
	}

	@Test
	void transfer_a_negative_amount_from_savings_to_checking_is_invalid() {
		assertFalse(commandValidator.validate("transfer 87654321 12345678 -1"));
	}

	@Test
	void transfer_from_savings_to_checking_with_prior_withdraw_is_invalid() {
		((Savings) bank.retrieveAccount("87654321")).changeWithdrawalStatus();
		assertFalse(commandValidator.validate("transfer 87654321 12345678 1000"));
	}

	@Test
	void transfer_from_checking_to_savings_exceeding_limit_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 87654329 401"));
	}

	@Test
	void transfer_from_checking_to_savings_at_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 12345678 87654329 400"));
	}

	@Test
	void transfer_from_checking_to_savings_one_dollar_less_than_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 12345678 87654329 399"));
	}

	@Test
	void transfer_zero_from_checking_to_savings_is_valid() {
		assertTrue(commandValidator.validate("transfer 12345678 87654329 0"));
	}

	@Test
	void transfer_a_negative_amount_from_checking_to_savings_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 87654329 -1"));
	}

	@Test
	void transfer_from_checking_to_savings_with_prior_withdraw_for_receiver_is_valid() {
		((Savings) bank.retrieveAccount("87654329")).changeWithdrawalStatus();
		assertTrue(commandValidator.validate("transfer 12345678 87654329 250"));
	}

	@Test
	void transfer_from_checking_to_checking_exceeding_limit_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 12345679 401"));
	}

	@Test
	void transfer_from_checking_to_checking_at_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 12345678 12345679 400"));
	}

	@Test
	void transfer_from_checking_to_checking_one_dollar_less_than_limit_is_valid() {
		assertTrue(commandValidator.validate("transfer 12345678 12345679 399"));
	}

	@Test
	void transfer_zero_from_checking_to_checking_is_valid() {
		assertTrue(commandValidator.validate("transfer 12345678 12345679 0"));
	}

	@Test
	void transfer_a_negative_amount_from_checking_to_checking_is_invalid() {
		assertFalse(commandValidator.validate("transfer 12345678 12345679 -1"));
	}
}
