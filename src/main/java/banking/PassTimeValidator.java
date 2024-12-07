package banking;

public class PassTimeValidator {
	public boolean validate(String[] command) {
		if (command.length != 2) {
			return false;
		}

		String monthsStr = command[1];

		return isValidMonths(monthsStr);
	}

	private boolean isValidMonths(String monthsStr) {
		try {
			int months = Integer.parseInt(monthsStr);
			return (months > 0) && (months <= 60);
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
