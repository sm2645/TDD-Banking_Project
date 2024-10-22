import java.util.HashMap;
import java.util.Map;

public class Bank {
	private final Map<Integer, Accounts> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<Integer, Accounts> getAccounts() {
		return accounts;
	}

	public void addAccount(Integer accountId, Accounts accountName) {
		accounts.put(accountId, accountName);
	}

	public Accounts retrieveAccount(int accountId) {
		return accounts.get(accountId);
	}

	public void depositToAccount(int accountId, double depositAmount) {
		accounts.get(accountId).deposit(depositAmount);
	}

	public void withdrawFromAccount(int accountId, double withdrawAmount) {
		accounts.get(accountId).withdraw(withdrawAmount);
	}
}
