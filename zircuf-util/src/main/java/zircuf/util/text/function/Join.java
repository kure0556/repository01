package zircuf.util.text.function;

/**
 * 文字列配列を区切り文字で連結するユーティリティ。
 *
 * <p>本クラスは {@link String#join} と同じ仕様に従い、
 * <b>null を許容しない</b>。配列自体が null または要素に null が含まれる場合、
 * {@link NullPointerException} が発生する。</p>
 *
 * <p>用途としては CSV / TSV / パイプ区切りの簡易生成を想定している。
 * より高度な CSV 仕様（RFC4180 など）を満たす必要がある場合は
 * 専用ライブラリの利用を推奨する。</p>
 *
 * <h3>使用例</h3>
 * <pre>
 * String[] arr = { "A", "B", "C" };
 *
 * Join.csv(arr);     // A,B,C
 * Join.csvDQ(arr);   // "A","B","C"
 * Join.tsv(arr);     // A\tB\tC
 * Join.pipe(arr);    // A|B|C
 * </pre>
 */
public final class Join {

	/**
	 * カンマ区切りで連結する。
	 * null は許容しない（String.join と同じ）。
	 */
	public static String csv(String[] text) {
		return String.join(",", text);
	}

	/**
	 * CSV のダブルクォート形式で連結する。
	 *
	 * <p>各要素は以下のように変換される：</p>
	 * <ul>
	 *   <li>値全体をダブルクォートで囲む</li>
	 *   <li>内部のダブルクォートは "" にエスケープする</li>
	 * </ul>
	 *
	 * <p>例：</p>
	 * <pre>
	 * ["A", "B,C", null, "D\"E"]
	 * → "A","B,C",,"D""E"
	 * </pre>
	 *
	 * <p>null は許容しない（String.join と同じ）。</p>
	 */
	public static String csvDQ(String[] text) {
	    StringBuilder sb = new StringBuilder();
	    for (String s : text) {
	        if (s == null) {
	            sb.append(','); // "" ではなく未記入セルとして扱う
	            continue;
	        }
	        String v = s.replace("\"", "\"\"");
	        sb.append('"').append(v).append('"').append(',');
	    }
	    if (sb.length() > 0) {
	        sb.deleteCharAt(sb.length() - 1);
	    }
	    return sb.toString();
	}

	/**
	 * タブ区切りで連結する。
	 * null は許容しない（String.join と同じ）。
	 */
	public static String tsv(String[] text) {
		return String.join("\t", text);
	}

	/**
	 * パイプ区切りで連結する。
	 * null は許容しない（String.join と同じ）。
	 */
	public static String pipe(String[] text) {
		return String.join("|", text);
	}
}
