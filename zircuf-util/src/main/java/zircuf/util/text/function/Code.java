package zircuf.util.text.function;

/**
 * 文字列の命名変換を扱うユーティリティクラス。
 *
 * <p>主に Java コード生成や命名規約の統一を目的として、
 * 以下のような文字列操作を提供する：</p>
 *
 * <ul>
 *   <li>スネークケース（snake_case）からキャメルケース（camelCase / PascalCase）への変換</li>
 *   <li>先頭文字の大文字化・小文字化</li>
 *   <li>コレクション名から単数形名称（Singleton 名）を推測する</li>
 * </ul>
 *
 * <h3>スネークケース → キャメルケース変換</h3>
 * <p>snake_case を camelCase または PascalCase に変換する。
 * アンダースコアは単語区切りとして扱い、連続アンダースコアは無視される。
 * 英字は基本的に小文字化され、単語の先頭のみ大文字化される。</p>
 *
 * <h3>先頭文字の変換</h3>
 * <p>文字列の先頭 1 文字のみを大文字または小文字に変換する。
 * 空文字列の場合は入力をそのまま返す。</p>
 *
 * <h3>Singleton 名推測（Eclipse 互換）</h3>
 * <p>変数名がコレクションを表す場合に、その要素名（単数形）を推測する。
 * Eclipse の拡張 for 文が行う要素名推測と同等の簡易ヒューリスティックに基づく。</p>
 *
 * <p>以下の優先順位で語尾を処理する：</p>
 * <ol>
 *   <li><b>List / Map / Set</b> で終わる場合は除去する。</li>
 *   <li><b>ies → y</b> に変換する。</li>
 *   <li><b>s を削除</b>（長さ 2 以上の場合）。</li>
 *   <li>上記に該当しない場合は変換せず、末尾に <b>Item</b> を付与する。</li>
 * </ol>
 *
 * <p>この変換は英語の複数形を厳密に扱うものではなく、
 * gas → ga や class → clas のような変換も発生し得る。
 * ただし変換不能時は Item を付与するため、
 * 自動生成において Singleton 名が必ず得られる。</p>
 *
 * <p>本クラスは null を入力として想定していないため、
 * null が渡された場合の動作は未定義である。</p>
 */
public final class Code {

	/**
	 * スネークケースから先頭小文字キャメルケースに変換
	 * @param input
	 * @return
	 */
	public static String toLowerCamel(final String input) {
		return toCamelCase(input, true);
	}

	/**
	 * スネークケースから先頭大文字キャメルケースに変換
	 * @param input
	 * @return
	 */
	public static String toUpperCamel(final String input) {
		return toCamelCase(input, false);
	}

	private static String toCamelCase(final String snakeCase, final boolean isFirstLower) {
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
	public static String firstCharOnlyToUpper(final String input) {
		return input.isEmpty()
				? input
				: Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}

	/**
	 * 先頭文字のみを子文字に変換
	 * @param input
	 * @return
	 */
	public static String firstCharOnlyToLower(final String input) {
		return input.isEmpty()
				? input
				: Character.toLowerCase(input.charAt(0)) + input.substring(1);
	}

	/**
	 * 変数名から単数形の名称を推測するユーティリティ。
	 *
	 * <p>Eclipse の拡張 for 文における要素名推測と同等の
	 * シンプルなヒューリスティックに基づいて変換を行う。</p>
	 *
	 * <p>以下の優先順位で語尾を処理する：</p>
	 *
	 * <ol>
	 *   <li><b>List / Map / Set</b> で終わる場合は除去する。</li>
	 *   <li><b>ies → y</b> に変換する。</li>
	 *   <li><b>s を削除</b>（長さ 2 以上の場合）。</li>
	 *   <li>上記に該当しない場合は変換せず、末尾に <b>Item</b> を付与する。</li>
	 * </ol>
	 *
	 * <p>このメソッドは英語の複数形を厳密に扱うものではなく、
	 * Eclipse と同様に「変数名の慣習」に基づく簡易推測である。
	 * そのため、gas → ga や class → clas のような変換も発生し得る。</p>
	 *
	 * <p>ただし変換不能な場合は Item を付与するため、
	 * 自動生成において Singleton 名が必ず得られる。</p>
	 *
	 * @param input 変数名（キャメルケース想定）
	 * @return 推測された単数形名称（変換不能時は Item 付与）
	 */
	public static String convertSingletonName(final String input) {

		// 1. List / Map / Set（最優先）
		if (input.endsWith("List")) {
			return input.substring(0, input.length() - 4);
		}
		if (input.endsWith("Map")) {
			return input.substring(0, input.length() - 3);
		}
		if (input.endsWith("Set")) {
			return input.substring(0, input.length() - 3);
		}

		// 2. ies → y
		if (input.endsWith("ies") && input.length() > 3) {
			return input.substring(0, input.length() - 3) + "y";
		}

		// 3. s を削る（長さ 2 以上）
		if (input.endsWith("s") && input.length() > 1) {
			return input.substring(0, input.length() - 1);
		}

		// 4. 変換不能 → Item を付与
		return input + "Item";
	}

}
