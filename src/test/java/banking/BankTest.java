package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ACCOUNT_ID = "12345678";
	public static final String SECOND_ACCOUNT_ID = "12345679";
	public static final double SAVINGS_APR = 2.5;
	Bank bank;
	CD cdAccount;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		cdAccount = new CD(ACCOUNT_ID, SAVINGS_APR, 100);
	}

	@Test
	void bank_has_no_accounts_initially() {

		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	void add_single_account_to_bank() {
		bank.addAccount(ACCOUNT_ID, cdAccount);
		assertEquals(cdAccount, bank.getAccounts().get(ACCOUNT_ID));
	}

	@Test
	void add_two_accounts_with_modified_id_and_name() {
		bank.addAccount(ACCOUNT_ID, cdAccount);
		bank.addAccount(SECOND_ACCOUNT_ID, new Savings(SECOND_ACCOUNT_ID, SAVINGS_APR));

		assertEquals(cdAccount, bank.getAccounts().get(ACCOUNT_ID));
		assertEquals(SAVINGS_APR, bank.getAccounts().get(SECOND_ACCOUNT_ID).getAPR());
	}

	@Test
	void retrieving_returns_the_correct_account() {
		bank.addAccount(ACCOUNT_ID, cdAccount);
		assertEquals(cdAccount, bank.retrieveAccount(ACCOUNT_ID));

	}

	@Test
	void depositing_money_by_id_credits_correct_amount_to_accounts_balance() {
		bank.addAccount(ACCOUNT_ID, cdAccount);
		bank.depositToAccount(ACCOUNT_ID, 100.0);

		Accounts actual = bank.getAccounts().get(ACCOUNT_ID);
		assertEquals(200, actual.getBalance());
	}

	@Test
	void depositing_money_twice_by_id_credits_correct_amount_to_accounts_balance() {
		bank.addAccount(ACCOUNT_ID, cdAccount);
		bank.depositToAccount(ACCOUNT_ID, 100.0);
		bank.depositToAccount(ACCOUNT_ID, 200);
		Accounts actual = bank.getAccounts().get(ACCOUNT_ID);
		assertEquals(400, actual.getBalance());
	}

	@Test
	void withdrawing_money_with_id_debits_correct_amount_from_accounts_balance() {
		bank.addAccount(ACCOUNT_ID, cdAccount);
		bank.withdrawFromAccount(ACCOUNT_ID, 100.0);

		Accounts actual = bank.getAccounts().get(ACCOUNT_ID);
		assertEquals(0, actual.getBalance());
	}

	@Test
	void double_withdrawing_money_with_id_debits_correct_amount_from_accounts_balance() {
		bank.addAccount(ACCOUNT_ID, cdAccount);
		bank.withdrawFromAccount(ACCOUNT_ID, 50.0);
		bank.withdrawFromAccount(ACCOUNT_ID, 25.0);

		Accounts actual = bank.getAccounts().get(ACCOUNT_ID);
		assertEquals(25, actual.getBalance());
	}

}
