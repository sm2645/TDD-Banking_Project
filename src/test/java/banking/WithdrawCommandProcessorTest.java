package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {
	public static final String ACCOUNT_ID = "01234567";
	public static final double APR = 1;
	public static final double BALANCE = 0;
	public static final double DEPOSIT = 1000;

	private Bank bank;
	private CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	// banking.Checking
	@Test
	public void withdraw_zero_works_for_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 0");
		assertEquals(DEPOSIT, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_one_dollar_works_for_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 1");
		assertEquals((DEPOSIT - 1), bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_works_for_max_limit_of_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 400");
		assertEquals((DEPOSIT - 400), bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_for_amount_over_balance_of_checking_account_is_zero() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, 300);

		commandProcessor.process("withdraw 01234567 400");
		assertEquals((0), bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_works_multiple_times_for_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 100");
		commandProcessor.process("withdraw 01234567 100");
		commandProcessor.process("withdraw 01234567 100");
		assertEquals((DEPOSIT - (100 * 3)), bank.retrieveAccount("01234567").getBalance());
	}

	// Savings
	@Test
	public void withdraw_zero_works_for_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 0");
		assertEquals(DEPOSIT, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_one_dollar_works_for_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 1");
		assertEquals((DEPOSIT - 1), bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_works_for_one_less_than_max_limit_of_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 999");
		assertEquals((DEPOSIT - 999), bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_works_for_max_limit_of_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, DEPOSIT);

		commandProcessor.process("withdraw 01234567 1000");
		assertEquals((DEPOSIT - 1000), bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_for_amount_over_balance_of_savings_account_is_zero() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));
		bank.depositToAccount(ACCOUNT_ID, 300);

		commandProcessor.process("withdraw 01234567 400");
		assertEquals((0), bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void withdraw_balance_for_13mo_old_cd_account() {
		bank.addAccount(ACCOUNT_ID, new CD(ACCOUNT_ID, APR, DEPOSIT));
		bank.incrementAnAccountsAge(ACCOUNT_ID, 13);

		commandProcessor.process("withdraw 01234567 1000");
		assertEquals(0, bank.retrieveAccount("01234567").getBalance());
	}

}