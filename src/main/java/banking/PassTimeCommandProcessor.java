package banking;

import java.util.Iterator;
import java.util.Map;

public class PassTimeCommandProcessor {

	private final Bank bank;

	public PassTimeCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] commandSeparated) {
		int months = Integer.parseInt(commandSeparated[1]);

		for (int i = 0; i < months; i++) {
			processOneMonthPass();
		}
	}

	private void processOneMonthPass() {
		Iterator<Map.Entry<String, Accounts>> iterator = bank.getAccounts().entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Accounts> entry = iterator.next();
			Accounts account = entry.getValue();

			account.incrementAccountAge(1);

			String accountType = account.getAccountType();

			if (accountType.equals("Savings")) {
				((Savings) account).resetWithdrawals();

			}

			if (account.getBalance() == 0) {
				iterator.remove();
				continue;
			}
			if (account.getBalance() < 100) {
				account.withdraw(25);
				continue;
			}
			if (accountType.equals("Cd")) {
				for (int i = 0; i < 4; i++) {
					account.accrueApr();
				}
			} else {
				account.accrueApr();
			}
		}
	}
}
