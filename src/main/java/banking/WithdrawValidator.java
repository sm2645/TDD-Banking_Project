package banking;

public class WithdrawValidator {
	private final Bank bank;

	public WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

//
	public boolean validate(String[] command) {
		if (command.length != 3) {
			return false;
		}

		String accountId = command[1];
		String amountStr = command[2];

		if (!bank.isValidAccountId(accountId) || !bank.isValidAmount(amountStr)) {
			return false;
		}
		double amount = Double.parseDouble(amountStr);

		Accounts account = bank.retrieveAccount(accountId);
		if (account == null) {
			return false;
		} else {

			String accountType = account.getAccountType();
			switch (accountType) {
			case "banking.Savings":
				return validateWithdrawalFromSavings((Savings) account, amount);
			case "banking.Checking":
				return validateWithdrawalFromChecking(amount);
			case "banking.CD":
				return validateWithdrawalFromCD(account, amount);
			}
			return false;
		}
	}

	private boolean validateWithdrawalFromSavings(Savings account, double amount) {
		return amount <= 1000 && !account.hasWithdrawnThisMonth();
	}

	private boolean validateWithdrawalFromChecking(double amount) {
		return amount <= 400;
	}

	private boolean validateWithdrawalFromCD(Accounts account, double amount) {
		if (account.getAccountAge() < 12) {
			return false;
		}
		return amount >= account.getBalance();
	}
}
