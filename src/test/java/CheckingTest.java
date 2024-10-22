import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {
	@Test
	public void checking_starting_balance_of_zero() {
		Savings saving = new Savings();
		assertEquals(saving.getBalance(), 0);
	}
}
