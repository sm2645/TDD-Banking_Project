import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SavingsTest {
	Savings savings;

	@Test
	public void saving_starting_balance_of_one() {
		savings = new Savings();
		assertEquals(savings.getBalance(), 0);
	}
}
