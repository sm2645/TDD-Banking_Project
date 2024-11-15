package banking;

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
	public void command_processor_properly_processes_create_commands() {
		commandProcessor.process("create checking 12345678 1.0");
		assertEquals(1.0, bank.retrieveAccount("12345678").getAPR());
	}

	@Test
	public void command_processor_properly_processes_create_cd_commands_with_5_parameters() {
		commandProcessor.process("create cd 12345678 1.0 1000");
		assertEquals(1000, bank.retrieveAccount("12345678").getBalance());
	}

	@Test
	public void command_processor_properly_processes_deposit_commands() {
		commandProcessor.process("create checking 12345678 1.0");

		commandProcessor.process("deposit 12345678 100");
		assertEquals(100.0, bank.retrieveAccount("12345678").getBalance());
	}//
}
