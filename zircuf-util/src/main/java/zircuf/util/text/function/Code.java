package zircuf.util.text.function;

import java.util.Objects;

public class Code {

	/**
	 * 小文字スネークケースから先頭小文字キャメルケースに変換
	 * @param input
	 * @return
	 */
	public static String lowerSnakeToLowerCamel(String input) {
		return lowerSnakeToCamel(input, true);
	}

	/**
	 * 小文字スネークケースから先頭大文字キャメルケースに変換
	 * @param input
	 * @return
	 */
	public static String lowerSnakeToUpperCamel(String input) {
		return lowerSnakeToCamel(input, false);
	}

	private static String lowerSnakeToCamel(String input, boolean isFirstLower) {
		Objects.requireNonNull(input, "input");
		String[] split = input.split("_");
		StringBuilder sb = new StringBuilder();
		int n = 0;
		if (isFirstLower) {
			sb.append(split[0]);
			n = 1;
		}
		for (int i = n; i < split.length; i++) {
			sb.append(firstCharOnlyToUpper(split[i]));
		}
		return sb.toString();
	}

	private static String firstCharOnlyToUpper(String str) {
		return str.isEmpty()
				? str
				: toUpperCase(str.charAt(0)) + str.substring(1);
	}

	private static final char CASE_MASK = 0x20;

	private static char toUpperCase(char c) {
		return isLowerCase(c) ? (char) (c ^ CASE_MASK) : c;
	}

	private static boolean isLowerCase(char c) {
		return (c >= 'a') && (c <= 'z');
	}

}
