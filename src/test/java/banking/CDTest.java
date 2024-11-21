package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CDTest {
	@Test
	public void cd_starts_with_value_it_was_supplied_with() {
		CD cd = new CD("12345678", 0, 200);
		assertEquals(cd.getBalance(), 200);
	}
}
