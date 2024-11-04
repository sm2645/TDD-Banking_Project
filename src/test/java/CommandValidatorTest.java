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
		commandValidator = new CommandValidator(bank);
		createValidator = new CreateValidator(bank):
		depositValidator = new DepositValidator(bank);

	}

	@Test
	void valid_command() {
		boolean actual = commandValidator.validate("create savings 12345678 0.5");
		assertTrue(actual);
	}
}