package banking;

public class TransferValidator {
	private final Bank bank;

	public TransferValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String[] command) {
		if (command.length != 4) {
			return false;
		}

		String senderId = command[1];
		String receiverId = command[2];
		String amountStr = command[3];

		if (!isValidAccountId(senderId) || !isValidAccountId(receiverId) || !isValidAmount(amountStr)) {
			return false;
		}

		Accounts senderAccount = bank.retrieveAccount(senderId);
		Accounts receiverAccount = bank.retrieveAccount(receiverId);

		if (senderAccount == null || receiverAccount == null
				|| senderAccount.getAccountType().equals("Certificate of Deposit")
				|| receiverAccount.getAccountType().equals("Certificate of Deposit")) {
			return false;
		}

		double amount = Double.parseDouble(amountStr);

		String senderAccountType = senderAccount.getAccountType();
		switch (senderAccountType) {
		case "banking.Savings":
			Savings savingsAccount = (Savings) senderAccount;
			return validateSavingsTransfer(savingsAccount, amount);
		case "banking.Checking":
			return validateCheckingTransfer(amount);
		default:
			return false;
		}
	}

	private boolean validateSavingsTransfer(Savings savingsAccount, double amount) {

		return amount <= 1000 && !savingsAccount.hasWithdrawnThisMonth();
	}

	private boolean validateCheckingTransfer(double amount) {
		return amount <= 400;
	}

	public boolean isValidAccountId(String id) {
		if (id.length() != 8) {
			return false;
		}
		for (char c : id.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	private boolean isValidAmount(String amountStr) {
		// Validate that the amount is a non-negative number
		try {
			double amount = Double.parseDouble(amountStr);
			return amount >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
