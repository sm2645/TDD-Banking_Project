package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandProcessorTest {
	public static final String ACCOUNT_ID = "01234567";
	public static final double APR = 1;
	public static final double BALANCE = 0;
	public static final double DEPOSIT = 500;

	private Bank bank;
	private CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

//banking.Checking
	@Test
	public void depositing_zero_works_for_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 0");
		assertEquals(BALANCE, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void deposit_works_for_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 500");
		assertEquals(DEPOSIT, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void deposit_works_for_max_limit_of_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 1000");
		assertEquals(1000, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void deposit_works_multiple_times_for_checking_account() {
		bank.addAccount(ACCOUNT_ID, new Checking(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 500");
		commandProcessor.process("deposit 01234567 500");
		commandProcessor.process("deposit 01234567 500");
		assertEquals(3 * DEPOSIT, bank.retrieveAccount("01234567").getBalance());
	}

//banking.Savings
	@Test
	public void depositing_zero_works_for_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 0");
		assertEquals(BALANCE, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void deposit_works_for_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 500");
		assertEquals(DEPOSIT, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void deposit_works_for_max_limit_of_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 2500");
		assertEquals(2500, bank.retrieveAccount("01234567").getBalance());
	}

	@Test
	public void deposit_works_multiple_times_for_savings_account() {
		bank.addAccount(ACCOUNT_ID, new Savings(ACCOUNT_ID, APR));

		commandProcessor.process("deposit 01234567 500");
		commandProcessor.process("deposit 01234567 500");
		commandProcessor.process("deposit 01234567 500");
		assertEquals(3 * DEPOSIT, bank.retrieveAccount("01234567").getBalance());
	}

}
