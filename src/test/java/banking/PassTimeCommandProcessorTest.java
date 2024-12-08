package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	public static final String ACCOUNT_ID = "01234567";
	private static final double APR = 1.2;
	private static final double INITIAL_BALANCE = 1000;

	private Bank bank;
	private CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void pass_time_increments_CD_account_age_by_one_month() {
		bank.addAccount(ACCOUNT_ID, new CD(ACCOUNT_ID, APR, INITIAL_BALANCE));
		commandProcessor.process("pass 1");
		assertEquals(1, bank.retrieveAccount(ACCOUNT_ID).getAccountAge());
	}

	@Test
	public void pass_time_increments_CD_account_age_by_twelve_months() {
		bank.addAccount(ACCOUNT_ID, new CD(ACCOUNT_ID, APR, INITIAL_BALANCE));
		commandProcessor.process("pass 12");
		assertEquals(12, bank.retrieveAccount(ACCOUNT_ID).getAccountAge());
	}

	@Test
	public void pass_time_increments_CD_account_age_by_maximum_months() {
		bank.addAccount(ACCOUNT_ID, new CD(ACCOUNT_ID, APR, INITIAL_BALANCE));
		commandProcessor.process("pass 60");
		assertEquals(60, bank.retrieveAccount(ACCOUNT_ID).getAccountAge());
	}

	@Test
	public void pass_time_closes_zero_balance_checking_account_after_one_month() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		commandProcessor.process("pass 1");

		assertFalse(bank.ifAccountExists(ACCOUNT_ID));
	}

	@Test
	public void pass_time_closes_zero_balance_saving_account_after_one_month() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		commandProcessor.process("pass 1");

		assertFalse(bank.ifAccountExists(ACCOUNT_ID));
	}

	@Test
	public void pass_time_deducts_fee_if_checking_has_low_balance() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		bank.retrieveAccount(ACCOUNT_ID).deposit(50);

		commandProcessor.process("pass 1");
		assertEquals(25.0, bank.retrieveAccount(ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_time_deducts_fee_if_saving_has_low_balance() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		bank.retrieveAccount(ACCOUNT_ID).deposit(50);

		commandProcessor.process("pass 1");
		assertEquals(25.0, bank.retrieveAccount(ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_time_doesnt_deducts_fee_if_saving_has_minimum_balance() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, 0));
		bank.retrieveAccount(ACCOUNT_ID).deposit(INITIAL_BALANCE);

		commandProcessor.process("pass 1");
		assertEquals(INITIAL_BALANCE, bank.retrieveAccount(ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_time_doesnt_deducts_fee_if_checking_has_minimum_balance() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, 0));
		bank.retrieveAccount(ACCOUNT_ID).deposit(INITIAL_BALANCE);

		commandProcessor.process("pass 1");
		assertEquals(INITIAL_BALANCE, bank.retrieveAccount(ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_time_accrues_apr_and_is_applied_correctly_to_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		bank.retrieveAccount(ACCOUNT_ID).deposit(INITIAL_BALANCE);

		commandProcessor.process("pass 1");
		assertEquals(1001.00, bank.retrieveAccount(ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_time_apr_is_applied_correctly_to_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		bank.retrieveAccount(ACCOUNT_ID).deposit(INITIAL_BALANCE);

		commandProcessor.process("pass 1");
		assertEquals(1001.0, bank.retrieveAccount(ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_time_apr_is_applied_correctly_to_cd_account() {
		bank.addAccount(ACCOUNT_ID, new CD(ACCOUNT_ID, APR, INITIAL_BALANCE));

		commandProcessor.process("pass 1");
		assertEquals(1004.0, bank.retrieveAccount(ACCOUNT_ID).getBalance());
	}

	@Test
	public void pass_time_resets_withdraw_of_saving_account_after_one_month() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));

		Savings account = (Savings) bank.retrieveAccount(ACCOUNT_ID);
		account.changeWithdrawalStatus();

		commandProcessor.process("pass 1");
		assertFalse(account.hasWithdrawnThisMonth());
	}

}
