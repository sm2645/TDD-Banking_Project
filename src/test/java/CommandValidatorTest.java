import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	CommandValidator commandValidator;
	CreateValidator createValidator;
	DepositValidator depositValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);
		commandValidator = new CommandValidator(bank, createValidator, depositValidator);
	}

	@Test
	void valid_command() {
		boolean actual = commandValidator.validate("create savings 12345678 0.5");
		assertTrue(actual);
	}

	@Test
	void testIsValidAccountType() {
		assertTrue(commandValidator.isValidAccountType("savings"));
		assertTrue(commandValidator.isValidAccountType("cd"));

	@Test
	void duplicateAccountIDInvalid() {
		commandValidator.createAccount("create savings 12345678 0.5");
		boolean actual = commandValidator.createAccount("create savings 12345678 0.6");
		assertFalse(actual);
	}


	}

}