package zircuf.util.text.function;

import java.util.ArrayList;
import java.util.List;

/**
 * 区切り文字で連結された文字列を分割するユーティリティ。
 *
 * <p>{@link Join} と対になる仕様を持つ。特に以下の点に注意：</p>
 *
 * <ul>
 *   <li><b>csv / tsv / pipe</b><br>
 *       {@link String#split} を使用し、空セルも保持する（split の -1 指定）。<br>
 *       Join 側が null を "null" として出力するため、Split 側では "null" を
 *       特別扱いせず文字列として扱う。</li>
 *
 *   <li><b>csvDQ</b><br>
 *       Join.csvDQ の逆変換を行う。<br>
 *       ・ダブルクォートで囲まれた値を復元<br>
 *       ・内部の "" を " に戻す<br>
 *       ・未記入セル（,,）は null として扱う</li>
 * </ul>
 *
 * <h3>使用例</h3>
 * <pre>
 * String text = "\"A\",\"B\",,\"C\",\"D\"\"E\"";
 *
 * Split.csvDQ(text);
 * // → ["A", "B", null, "C", "D\"E"]
 *
 * Split.csv("A,B,null,C");
 * // → ["A", "B", "null", "C"]
 * </pre>
 */
public final class Split {

	private Split() {
	}

	/**
	 * カンマ区切りで分割する。
	 * 空セルも保持する（"A,,C" → ["A", "", "C"]）。
	 * null は "null" として扱われる（Join.csv と同じ）。
	 */
	public static String[] csv(String text) {
		return text.split(",", -1);
	}

    /**
     * CSV のダブルクォート形式を分割する。
     *
     * <p>{@link Join#csvDQ(String[])} の逆変換を行う。以下の仕様を持つ：</p>
     *
     * <ul>
     *   <li>"A" → A</li>
     *   <li>"B,C" → B,C</li>
     *   <li>"D""E" → D"E</li>
     *   <li>未記入セル（,,） → null</li>
     *   <li>空文字（""） → ""（空文字として復元）</li>
     * </ul>
     *
     * <p>例：</p>
     * <pre>
     * "\"A\",\"\",\"C\""
     * → ["A", "", "C"]
     *
     * "\"A\",\"B\",,\"C\""
     * → ["A", "B", null, "C"]
     * </pre>
     *
     * <p>ダブルクォートの有無を判定することで
     * 空文字と未記入セル（null）を正しく区別する。</p>
     */

	public static String[] csvDQ(String text) {
		List<String> result = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		boolean inQuotes = false;
		boolean wasQuoted = false; // 空文字と null を区別するため

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (c == '"') {
				if (inQuotes && i + 1 < text.length() && text.charAt(i + 1) == '"') {
					current.append('"');
					i++;
				} else {
					inQuotes = !inQuotes;
					wasQuoted = true; // ダブルクォートがあった
				}
			} else if (c == ',' && !inQuotes) {
				// wasQuoted が true → 空文字 "" の可能性あり
				result.add(current.length() == 0 && !wasQuoted ? null : current.toString());
				current.setLength(0);
				wasQuoted = false;
			} else {
				current.append(c);
			}
		}

		result.add(current.length() == 0 && !wasQuoted ? null : current.toString());

		return result.toArray(new String[0]);
	}

	/**
	 * タブ区切りで分割する。
	 * 空セルも保持する。
	 */
	public static String[] tsv(String text) {
		return text.split("\t", -1);
	}

	/**
	 * パイプ区切りで分割する。
	 * 空セルも保持する。
	 */
	public static String[] pipe(String text) {
		return text.split("\\|", -1);
	}
}
