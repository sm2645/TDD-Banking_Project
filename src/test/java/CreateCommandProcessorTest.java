import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateCommandProcessorTest {
	public static final String ACCOUNT_ID = "01234567";
	public static final double APR = 1;
	public static final double ZERO_BALANCE = 0;
	public static final double CD_BALANCE = 1000;

	private Bank bank;
	private CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void create_checking_commands_account_id_exists_after_creation() {
		commandProcessor.process("create checking 01234567 1000 1");
		assertTrue(bank.ifAccountExists(ACCOUNT_ID));
	}

	@Test
	public void create_checking_command_actually_creates_a_checking_type_account() {
		commandProcessor.process("create checking 01234567 1");
		assertEquals("Checking", bank.retrieveAccount("01234567").getAccountType());

	}

	@Test
	public void create_checking_command_actually_creates_account_with_given_id() {
		commandProcessor.process("create checking 01234567 1");
		assertEquals(ACCOUNT_ID, bank.retrieveAccount("01234567").getID());
	}

	@Test
	public void create_checking_command_actually_creates_an_account_with_the_defined_apr() {
		commandProcessor.process("create checking 01234567 1");
		assertEquals(APR, bank.retrieveAccount("01234567").getAPR());

	}

	@Test
	public void create_checking_command_initializes_with_zero_balance() {
		commandProcessor.process("create checking 01234567 1");
		assertEquals(ZERO_BALANCE, bank.retrieveAccount("01234567").getBalance());
	}

	// savings
	@Test
	public void create_savings_commands_account_id_exists_after_creation() {
		commandProcessor.process("create savings 01234567 1");
		assertTrue(bank.ifAccountExists(ACCOUNT_ID));
	}

	@Test
	public void create_savings_command_actually_creates_a_savings_type_account() {
		commandProcessor.process("create savings 01234567 1");
		assertEquals("Savings", bank.retrieveAccount("01234567").getAccountType());

	}

	@Test
	public void create_savings_command_actually_creates_account_with_given_id() {
		commandProcessor.process("create savings 01234567 1");
		assertEquals(ACCOUNT_ID, bank.retrieveAccount("01234567").getID());
	}

	@Test
	public void create_savings_command_actually_creates_an_account_with_the_defined_apr() {
		commandProcessor.process("create savings 01234567 1");
		assertEquals(APR, bank.retrieveAccount("01234567").getAPR());

	}

	@Test
	public void create_savings_command_initializes_with_zero_balance() {
		commandProcessor.process("create savings 01234567 1");
		assertEquals(ZERO_BALANCE, bank.retrieveAccount("01234567").getBalance());
	}

	// cd
	@Test
	public void create_cd_commands_account_id_exists_after_creation() {
		commandProcessor.process("create cd 01234567 1 1000");
		assertTrue(bank.ifAccountExists(ACCOUNT_ID));
	}

	@Test
	public void create_cd_command_actually_creates_a_cd_type_account() {
		commandProcessor.process("create cd 01234567 1 1000");
		assertEquals("CD", bank.retrieveAccount("01234567").getAccountType());

	}

	@Test
	public void create_cd_command_actually_creates_account_with_given_id() {
		commandProcessor.process("create cd 01234567 1 1000");
		assertEquals(ACCOUNT_ID, bank.retrieveAccount("01234567").getID());
	}

	@Test
	public void create_cd_command_actually_creates_an_account_with_the_defined_apr() {
		commandProcessor.process("create cd 01234567 1 1000");
		assertEquals(APR, bank.retrieveAccount("01234567").getAPR());
	}

	@Test
	public void create_cd_command_initializes_with_defined_balance() {
		commandProcessor.process("create cd 01234567 1 1000");
		assertEquals(CD_BALANCE, bank.retrieveAccount("01234567").getBalance());
	}
}