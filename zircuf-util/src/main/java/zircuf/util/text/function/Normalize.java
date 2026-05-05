package zircuf.util.text.function;

import java.text.Normalizer;

/**
 * 文字列の正規化処理を統一的に提供するユーティリティクラス。
 *
 * <p>本クラスは文字列処理ポリシーを一元化し、
 * 入力値の揺れ（全角/半角、Unicode 合成文字、空白、改行コードなど）を
 * 安定した形式に整えることを目的とする。</p>
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>NFKC による Unicode 正規化</li>
 *   <li>空白の統一（全角スペース → 半角、strip、連続空白の圧縮）</li>
 *   <li>改行コードの統一（LF）</li>
 *   <li>改行の除去（スペース化）</li>
 *   <li>制御文字の除去</li>
 * </ul>
 *
 * <p>これらの処理を組み合わせたプリセットとして
 * {@code standard(String)} および {@code standardNoLines(String)} を提供する。</p>
 */
public final class Normalize {

	private Normalize() {
	}

	/**
	 * Unicode 正規化（NFKC）を行う。
	 *
	 * <p>NFKC（Normalization Form Compatibility Composition）は、
	 * 「互換文字を正規化し、文字の揺れを統一する」ための最も強力な正規化方式である。
	 * 以下のようなケースで有効：</p>
	 *
	 * <ul>
	 *   <li>全角英数字 → 半角英数字（例：ＡＢＣ → ABC）</li>
	 *   <li>全角記号 → 半角記号（例：￥ → \、ｕ → u）</li>
	 *   <li>濁点結合文字 → 1 文字に統合（例：ｶﾞ → ガ）</li>
	 *   <li>合成文字の統一（例：e + ´ → é）</li>
	 *   <li>丸数字 → 通常数字（例：① → 1）</li>
	 * </ul>
	 *
	 * <p>外部入力（ユーザー入力、CSV、Excel、Web API）には
	 * 全角・半角・結合文字などの揺れが混入しやすいため、
	 * 比較・検索・保存の前に NFKC で統一することが推奨される。</p>
	 *
	 * @param s 入力文字列（null 非許容）
	 * @return NFKC 正規化された文字列
	 */
	public static String nfkc(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFKC);
	}

	/**
	 * 空白を統一する。
	 *
	 * <p>以下の処理を行う：</p>
	 * <ul>
	 *   <li>全角スペース（U+3000）を半角スペースに変換</li>
	 *   <li>Unicode の空白を strip（前後除去）</li>
	 *   <li>連続スペースを 1 個に圧縮</li>
	 * </ul>
	 *
	 * <p>trim() や strip() より強力で、空白の揺れを完全に除去したい場合に使用する。</p>
	 *
	 * @param s 入力文字列
	 * @return 空白が統一された文字列
	 */
	public static String spaces(String s) {
		if (s == null || s.isEmpty())
			return s;
		s = s.replace('\u3000', ' '); // 全角スペース → 半角
		s = s.strip();
		return s.replaceAll(" +", " ");
	}

	/**
	 * 改行コードを LF（\n）に統一する。
	 *
	 * <p>Windows（CRLF）、Mac（CR）、Linux（LF）の差異を吸収する。</p>
	 *
	 * @param s 入力文字列
	 * @return 改行コードが LF に統一された文字列
	 */
	public static String newlines(String s) {
		if (s == null || s.isEmpty())
			return s;
		return s.replace("\r\n", "\n").replace("\r", "\n");
	}

	/**
	 * 改行をすべて半角スペースに置き換える。
	 *
	 * <p>ログの 1 行化、CSV/TSV のフィールド整形、UI 表示用の短文化などに有効。</p>
	 *
	 * <p>以下の処理を行う：</p>
	 * <ul>
	 *   <li>CRLF / CR / LF をすべてスペースに変換</li>
	 *   <li>連続スペースを 1 個に圧縮</li>
	 *   <li>前後の空白を strip</li>
	 * </ul>
	 *
	 * @param s 入力文字列
	 * @return 改行がスペースに置き換えられた文字列
	 */
	public static String noLines(String s) {
		if (s == null || s.isEmpty())
			return s;
		s = newlines(s);
		s = s.replace("\n", " ");
		s = s.replaceAll(" +", " ");
		return s.strip();
	}

	/**
	 * 制御文字（タブ・改行を除く）を除去する。
	 *
	 * <p>ログ・外部入力・ファイル読み込み時に混入する不可視文字を除去する用途。</p>
	 *
	 * @param s 入力文字列
	 * @return 制御文字が除去された文字列
	 */
	public static String control(String s) {
		if (s == null || s.isEmpty())
			return s;
		return s.replaceAll("[\\p{Cntrl}&&[^\t\n]]", "");
	}

	/**
	 * 標準的な正規化処理をまとめて行うプリセット。
	 *
	 * <p>以下の順で処理する：</p>
	 * <ol>
	 *   <li>NFKC 正規化</li>
	 *   <li>改行コードの統一（LF）</li>
	 *   <li>空白の統一（全角→半角、strip、圧縮）</li>
	 *   <li>制御文字の除去</li>
	 * </ol>
	 *
	 * <p>外部入力の正規化、DB 保存前の整形、比較用の前処理などに適する。</p>
	 *
	 * @param s 入力文字列
	 * @return 標準正規化された文字列
	 */
	public static String standard(String s) {
		if (s == null || s.isEmpty())
			return s;
		s = nfkc(s);
		s = newlines(s);
		s = spaces(s);
		s = control(s);
		return s;
	}

	/**
	 * 標準正規化 + 改行除去（スペース化）を行うプリセット。
	 *
	 * <p>ログの 1 行化、CSV/TSV のフィールド整形、UI 表示用の短文化などに適する。</p>
	 *
	 * @param s 入力文字列
	 * @return 標準正規化 + 改行除去が行われた文字列
	 */
	public static String standardNoLines(String s) {
		return noLines(standard(s));
	}
}
