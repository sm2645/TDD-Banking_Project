import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {
	@Test
	public void checking_starting_balance_of_zero() {
		Checking checking = new Checking("12345678", 7.5);
		assertEquals(checking.getBalance(), 0);
	}
}
