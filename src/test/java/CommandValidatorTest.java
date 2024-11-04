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
		commandValidator = new CommandValidator(bank, null, null);
		createValidator = new CreateValidator(bank, commandValidator);
		depositValidator = new DepositValidator(bank);
		commandValidator = new CommandValidator(bank, createValidator, depositValidator);
	}

	@Test
	void valid_command() {
		boolean actual = commandValidator.validate("create savings 12345676 0.5");
		assertTrue(actual);
	}

	@Test
	void is_account_type_valid() {
		assertTrue(createValidator.isValidAccountType("savings"));
		assertTrue(createValidator.isValidAccountType("cd"));
	}

	@Test
	void is_apr_valid() {
		assertTrue(createValidator.isValidApr("0.0"));
	}

	@Test
	void duplicate_id_is_invalid() {
		bank.addAccount("12345678", new Savings("12345678", 0.5));
		boolean actual = commandValidator.validate("create savings 12345678 0.5");
		assertFalse(actual);
	}

}