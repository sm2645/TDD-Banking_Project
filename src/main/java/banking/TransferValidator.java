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

		if (!bank.isValidAccountId(senderId) || !bank.isValidAccountId(receiverId) || !bank.isValidAmount(amountStr)) {
			return false;
		}

		Accounts senderAccount = bank.retrieveAccount(senderId);
		Accounts receiverAccount = bank.retrieveAccount(receiverId);

		if (senderAccount == null || receiverAccount == null || senderAccount.getAccountType().equals("banking.CD")
				|| receiverAccount.getAccountType().equals("banking.CD")) {
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

}
