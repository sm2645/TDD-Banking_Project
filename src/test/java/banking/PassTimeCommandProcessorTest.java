package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	private static final String CHECKING_ID = "00000001";
	private static final String SAVINGS_ID = "00000002";
	private static final String CD_ID = "00000003";
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
	public void zero_balance_account_is_closed_after_one_month() {
		bank.addAccount(CHECKING_ID, new Checking(CHECKING_ID, APR));
		commandProcessor.process("pass 1");
		assertFalse(bank.ifAccountExists(CHECKING_ID));
	}

	@Test
	public void fee_is_deducted_for_low_balance_account() {
		bank.addAccount(SAVINGS_ID, new Savings(SAVINGS_ID, APR));
		bank.retrieveAccount(SAVINGS_ID).deposit(50);

		commandProcessor.process("pass 1");
		assertEquals(25.0, bank.retrieveAccount(SAVINGS_ID).getBalance());
	}

	@Test
	public void apr_is_applied_correctly_to_checking_account() {
		bank.addAccount(CHECKING_ID, new Checking(CHECKING_ID, APR));
		bank.retrieveAccount(CHECKING_ID).deposit(INITIAL_BALANCE);

		commandProcessor.process("pass 1");
		assertEquals(1001.0, bank.retrieveAccount(CHECKING_ID).getBalance());
	}

	@Test
	public void apr_is_applied_correctly_to_savings_account() {
		bank.addAccount(SAVINGS_ID, new Savings(SAVINGS_ID, APR));
		bank.retrieveAccount(SAVINGS_ID).deposit(INITIAL_BALANCE);

		commandProcessor.process("pass 1");
		assertEquals(1001.0, bank.retrieveAccount(SAVINGS_ID).getBalance());
	}

	@Test
	public void apr_is_applied_correctly_to_cd_account() {
		bank.addAccount(CD_ID, new CD(CD_ID, APR, INITIAL_BALANCE));

		commandProcessor.process("pass 1");
		assertEquals(1004.0, bank.retrieveAccount(CD_ID).getBalance());
	}

	@Test
	public void accounts_with_zero_balance_do_not_incur_fee_or_interest() {
		bank.addAccount(CHECKING_ID, new Checking(CHECKING_ID, APR));

		commandProcessor.process("pass 1");
		assertNull(bank.retrieveAccount(CHECKING_ID));
	}

}
