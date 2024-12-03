package banking;

public class PassTimeValidator {
	public boolean validate(String[] command) {
		if (command.length != 2) {
			return false;
		}

		String monthsStr = command[1];

		if (!isValidMonths(monthsStr)) {
			return false;
		}

		int months = Integer.parseInt(monthsStr);
		return months >= 1 && months <= 60;
	}

	private boolean isValidMonths(String monthsStr) {
		try {
			int months = Integer.parseInt(monthsStr);
			return months > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
