package zircuf.util.general;

import java.util.regex.Pattern;

/**
 * 文字列検証
 */
public class Check {

	private static final Pattern DOUBLE_PATTERN = Pattern.compile("[+-]?\\d+(\\.\\d+)?");

	public static boolean isDecimal(String text) {
		return false;
	}

	public static boolean isPositiveInt(String text) {
		text.trim().chars().allMatch(Character::isDigit);
		return false;
	}
}
