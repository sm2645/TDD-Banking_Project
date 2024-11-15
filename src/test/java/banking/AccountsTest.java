package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountsTest {

	private Accounts account;

	@BeforeEach
	public void setUp() {
		account = new CD("12345678", 2.0, 1000.0);
	}

	@Test
	public void tests_all_accounts_apr_when_created() {
		assertEquals(2.0, account.getAPR());
	}

	@Test
	public void balance_increases_by_amount_deposited() {
		account.deposit(250.00);
		assertEquals(1250.00, account.getBalance());
	}

	@Test
	public void balance_decreases_by_amount_withdrawn() {
		account.withdraw(500.0);
		assertEquals(500.0, account.getBalance());
	}

	@Test
	public void tests_balance_cannot_go_negative() {
		account.withdraw(1500.0);
		assertEquals(0.0, account.getBalance());
	}

	@Test
	public void tests_multiple_deposits() {
		account.deposit(200.0);
		account.deposit(300.0);
		assertEquals(1500.0, account.getBalance());
	}

	@Test
	public void tests_multiple_withdraws() {
		account.withdraw(250.0);
		account.withdraw(250.0);
		assertEquals(500.0, account.getBalance());
	}
}
