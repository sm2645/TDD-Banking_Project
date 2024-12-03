package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private final Map<String, Accounts> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Accounts> getAccounts() {
		return accounts;
	}

	public void addAccount(String accountId, Accounts accountName) {
		accounts.put(accountId, accountName);
	}

	public Accounts retrieveAccount(String accountId) {
		return accounts.get(accountId);
	}

	public void depositToAccount(String accountId, double depositAmount) {
		accounts.get(accountId).deposit(depositAmount);
	}

	public void withdrawFromAccount(String accountId, double withdrawAmount) {
		accounts.get(accountId).withdraw(withdrawAmount);
	}

	public void incrementAnAccountsAge(String accountId, int ageByAmount) {
		accounts.get(accountId).incrementAccountAge(ageByAmount);
	}

	public boolean ifAccountExists(String accountId) {
		Accounts account = accounts.get(accountId);
		return account != null;
	}
}
