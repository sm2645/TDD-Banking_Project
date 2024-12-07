package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {
	public static final String SENDER_ACCOUNT_ID = "01234567";
	public static final String RECEIVER_ACCOUNT_ID = "09876543";
	public static final double APR = 1;
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
	public void transfer_zero_from_checking_to_checking() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 0");
		assertEquals(0, bank.retrieveAccount("09876543").getBalance());
	}

	@Test
	public void transfer_one_from_checking_to_checking() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 1");
		assertEquals(DEPOSIT - 1, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(1, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());

	}

	@Test
	public void transfer_checking_maximum_from_checking_to_checking() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 400");
		assertEquals(DEPOSIT - 400, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(400, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_more_than_bal_from_checking_to_checking_deposits_senders_balance() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, 200);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 200");
		assertEquals(0, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(200, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void multiple_transfers_from_checking_to_checking_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 100");
		commandProcessor.process("transfer 01234567 09876543 200");
		commandProcessor.process("transfer 01234567 09876543 100");

		assertEquals(DEPOSIT - 400, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(400, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	// Checking to Savings
	@Test
	public void transfer_zero_from_checking_to_savings_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Savings(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 0");
		assertEquals(DEPOSIT, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(0, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_one_from_checking_to_savings_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Savings(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 1");
		assertEquals(DEPOSIT - 1, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(1, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_checking_maximum_from_checking_to_savings_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Savings(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 400");
		assertEquals(DEPOSIT - 400, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(400, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void multiple_transfers_from_checking_to_savings_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Checking(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Savings(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 100");
		commandProcessor.process("transfer 01234567 09876543 200");
		commandProcessor.process("transfer 01234567 09876543 100");

		assertEquals(DEPOSIT - 400, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(400, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	// Savings to Checking
	@Test
	public void transfer_zero_from_savings_to_checking_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Savings(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 0");
		assertEquals(DEPOSIT, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(0, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_one_from_savings_to_checking_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Savings(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 1");
		assertEquals(DEPOSIT - 1, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(1, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_savings_maximum_from_savings_to_checking_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Savings(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Checking(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 1000");
		assertEquals(0, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(1000, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	// Savings to Savings
	@Test
	public void transfer_zero_from_savings_to_savings_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Savings(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Savings(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 0");
		assertEquals(DEPOSIT, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(0, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_one_from_savings_to_savings_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Savings(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Savings(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 1");
		assertEquals(DEPOSIT - 1, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(1, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

	@Test
	public void transfer_savings_maximum_from_savings_to_savings_is_valid() {
		bank.addAccount(SENDER_ACCOUNT_ID, new Savings(SENDER_ACCOUNT_ID, APR));
		bank.depositToAccount(SENDER_ACCOUNT_ID, DEPOSIT);
		bank.addAccount(RECEIVER_ACCOUNT_ID, new Savings(RECEIVER_ACCOUNT_ID, APR));

		commandProcessor.process("transfer 01234567 09876543 1000");
		assertEquals(0, bank.retrieveAccount(SENDER_ACCOUNT_ID).getBalance());
		assertEquals(1000, bank.retrieveAccount(RECEIVER_ACCOUNT_ID).getBalance());
	}

}