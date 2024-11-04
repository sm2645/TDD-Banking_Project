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
		createValidator = new CreateValidator(bank, commandValidator);
		depositValidator = new DepositValidator(bank);
		commandValidator = new CommandValidator(bank, createValidator, depositValidator);
	}

	@Test
	void valid_command() {
		boolean actual = commandValidator.validate("create savings 12345678 0.5");
		assertTrue(actual);
	}

	@Test
	void test_is_valid_account_type() {
		assertTrue(createValidator.isValidAccountType("savings"));
		assertTrue(createValidator.isValidAccountType("cd"));
	}

	@Test
	void testIsValidApr() {
		assertTrue(createValidator.isValidApr("0.0"));
	}

}