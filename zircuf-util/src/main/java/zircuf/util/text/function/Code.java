package zircuf.util.text.function;

public class Code {

	/**
	 * スネークケースから先頭小文字キャメルケースに変換
	 * @param input
	 * @return
	 */
	public static String toLowerCamel(String input) {
		return toCamelCase(input, true);
	}

	/**
	 * スネークケースから先頭大文字キャメルケースに変換
	 * @param input
	 * @return
	 */
	public static String toUpperCamel(String input) {
		return toCamelCase(input, false);
	}

	private static String toCamelCase(String snakeCase, boolean isFirstLower) {
		if (snakeCase.isEmpty()) {
			return snakeCase;
		}
		StringBuilder sb = new StringBuilder();
		boolean capitalizeNext = false;
		boolean isFirstChar = true;
		for (char c : snakeCase.toCharArray()) {
			if (isFirstChar) {
				sb.append(isFirstLower ? Character.toLowerCase(c) : Character.toUpperCase(c));
				isFirstChar = false;
			} else if (c == '_') {
				capitalizeNext = true;
			} else if (capitalizeNext) {
				sb.append(Character.toUpperCase(c));
				capitalizeNext = false;
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	/**
	 * 先頭文字のみを大文字に変換
	 * @param input
	 * @return
	 */
	public static String firstCharOnlyToUpper(String input) {
		return input.isEmpty()
				? input
				: Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}

	/**
	 * 先頭文字のみを子文字に変換
	 * @param input
	 * @return
	 */
	public static String firstCharOnlyToLower(String input) {
		return input.isEmpty()
				? input
				: Character.toLowerCase(input.charAt(0)) + input.substring(1);
	}

	/**
	 * 英単語の複数形を単一系の名称に変換する
	 * <ul>
	 * <li>items -> item</li>
	 * <li>boxes -> box</li>
	 * <li>entries -> entry</li>
	 * <li>fooList -> foo</li>
	 * <li>fooMap -> foo</li>
	 * <li>fooSet -> foo</li>
	 * </ul>
	 * @param input
	 * @return
	 */
	public static String convertSingletonName(String input) {
		if (input.endsWith("s") && input.length() > 1) {
			if (input.endsWith("es") && input.length() > 2) {
				if (input.endsWith("ies") && input.length() > 3) {
					// entries -> entry
					return input.substring(0, input.length() - 3) + "y";
				} else if (input.endsWith("ses")
						|| input.endsWith("oes")
						|| input.endsWith("xes")
						|| input.endsWith("shes")
						|| input.endsWith("ches")) {
					// boxes -> box
					return input.substring(0, input.length() - 2);
				} else {
					// resources -> resource
					return input.substring(0, input.length() - 1);
				}
			} else {
				// items -> item
				return input.substring(0, input.length() - 1);
			}
		} else if (input.endsWith("List")) {
			// fooList -> foo
			return input.substring(0, input.length() - 4);
		} else if (input.endsWith("Map")) {
			// fooMap -> foo
			return input.substring(0, input.length() - 3);
		} else if (input.endsWith("Set")) {
			// fooSet -> foo
			return input.substring(0, input.length() - 3);
		}
		return input;
	}

}
