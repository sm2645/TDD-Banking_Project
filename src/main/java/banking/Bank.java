package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public boolean isValidAccountId(String id) {
		if (id.length() != 8) {
			return false;
		}

		for (int i = 0; i < id.length(); i++) {
			if (!Character.isDigit(id.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidAmount(String balance) {
		try {
			double balanceInteger = Double.parseDouble(balance);
			return balanceInteger >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isValidApr(String apr) {
		try {
			double aprValue = Double.parseDouble(apr);
			return aprValue >= 0 && aprValue <= 10;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Method to format account details for output
	public List<String> formattedAccDetails() {
		List<String> formattedDetails = new ArrayList<>();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		for (Accounts account : accounts.values()) {
			String accountType = account.getAccountType();
			String id = account.getID();
			String balance = decimalFormat.format(account.getBalance());
			String apr = decimalFormat.format(account.getAPR());
			String accountState = accountType + " " + id + " " + balance + " " + apr;
			formattedDetails.add(accountState);

			// Add transaction history for the account
			formattedDetails.addAll(account.getTransactionHistory());
		}

		return formattedDetails;
	}
}
