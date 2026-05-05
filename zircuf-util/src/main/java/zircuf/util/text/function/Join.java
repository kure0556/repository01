package zircuf.util.text.function;

/**
 * 文字列配列を区切り文字で連結するユーティリティ。
 *
 * <p>{@link String#join} と同様に、配列内の null は許容される。
 * ただし挙動はメソッドごとに異なる：</p>
 *
 * <ul>
 *   <li><b>csv / tsv / pipe</b>  
 *       {@link String#join} と同じく、null は文字列 "null" として扱われる。</li>
 *
 *   <li><b>csvDQ</b>  
 *       null は「未記入セル」として扱われ、<code>,,</code> の形で出力される。</li>
 * </ul>
 *
 * <p>用途としては CSV / TSV / パイプ区切りの簡易生成を想定している。
 * より高度な CSV 仕様（RFC4180 など）が必要な場合は専用ライブラリの利用を推奨する。</p>
 *
 * <h3>使用例</h3>
 * <pre>
 * String[] arr = { "A", "B", null, "C" };
 *
 * Join.csv(arr);     // A,B,null,C
 * Join.tsv(arr);     // A\tB\tnull\tC
 * Join.pipe(arr);    // A|null|null|C
 *
 * Join.csvDQ(arr);   // "A","B",,"C"
 *                    //   ↑ null は未記入セルとして扱われる
 * </pre>
 */
public final class Join {

	/**
	 * カンマ区切りで連結する。
	 * null は "null" として扱われる（String.join と同じ）。
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
	 *   <li>null は未記入セルとして扱われ、,, の形で出力される</li>
	 * </ul>
	 *
	 * <p>例：</p>
	 * <pre>
	 * ["A", "B,C", null, "D\"E"]
	 * → "A","B,C",,"D""E"
	 * </pre>
	 */
	public static String csvDQ(String[] text) {
		StringBuilder sb = new StringBuilder();
		for (String s : text) {
			if (s == null) {
				sb.append(','); // 未記入セル
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
	 * null は "null" として扱われる（String.join と同じ）。
	 */
	public static String tsv(String[] text) {
		return String.join("\t", text);
	}

	/**
	 * パイプ区切りで連結する。
	 * null は "null" として扱われる（String.join と同じ）。
	 */
	public static String pipe(String[] text) {
		return String.join("|", text);
	}
}
