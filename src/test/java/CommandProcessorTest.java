import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private Bank bank;
	private CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void testCreateCommand() {
		commandProcessor.process("create checking 12345678 1.0");
		Accounts account = bank.retrieveAccount("12345678");
		assertEquals(1.0, account.getAPR());
	}

	@Test
	public void testDepositCommand() {
		commandProcessor.process("create checking 12345678 1.0");

		commandProcessor.process("deposit 12345678 100");
		Accounts account = bank.retrieveAccount("12345678");
		assertEquals(100.0, account.getBalance());
	}
}
