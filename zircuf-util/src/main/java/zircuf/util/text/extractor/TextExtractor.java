package zircuf.util.text.extractor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zircuf.util.general.IOptional;
import zircuf.util.text.Texts;

/**
 * テンプレート文字列に基づいて、固定文字列と可変長ブロックからなる
 * 構造化テキスト（ファイル名・ログ行など）を抽出するユーティリティ。
 *
 * <p>テンプレート内では以下の 3 種類のブロックを使用できる：</p>
 *
 * <ul>
 *   <li><b>{}</b>  
 *       任意長の文字列をキャプチャする。キー名が省略された場合は
 *       出現順に <code>{0}</code>, <code>{1}</code> ... のように自動採番される。
 *       主に「位置合わせ」や「捨てキャプチャ」として利用する。</li>
 *
 *   <li><b>{name}</b>  
 *       任意長の文字列をキャプチャし、指定したキー名で取得する。</li>
 *
 *   <li><b>{name:min,max}</b>  
 *       最小 <code>min</code> 文字、最大 <code>max</code> 文字の範囲でキャプチャする。
 *       <code>min</code> または <code>max</code> は省略可能。
 *       <ul>
 *         <li><code>{name:4,}</code> → 最小 4 文字</li>
 *         <li><code>{name:,3}</code> → 最大 3 文字</li>
 *         <li><code>{name:2,3}</code> → 2〜3 文字</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <p>テンプレートに含まれる通常の文字列はそのまま固定文字列として扱われ、
 * 正規表現として安全にエスケープされる。</p>
 *
 * <p><b>※注意：</b>  
 * 本クラスは「テンプレートに基づく構造抽出」を目的としており、  
 * 数字抽出や任意の正規表現マッチングを行うための DSL ではない。  
 * より複雑なパターンマッチが必要な場合は通常の {@link java.util.regex.Pattern} を使用すること。</p>
 *
 * <h3>使用例</h3>
 *
 * <pre>
 * 【例1】無名キャプチャと固定長の組み合わせ
 * テンプレート:  aaa{}bbb{:2}{}_{}.csv
 * 入力:          aaaxxxbbb12YYY_ZZZ.csv
 *
 * 抽出結果:
 *   {0} = "xxx"   (aaa と bbb の間)
 *   {1} = "12"    (bbb の後の 2 文字)
 *   {2} = "YYY"   (2 文字と '_' の間)
 *   {3} = "ZZZ"   ('_' と .csv の間)
 *
 * 【例2】名前付きキーと固定長の組み合わせ
 * テンプレート:  aaa{hoge}bbb{fuga:2}{piyo}_{}.csv
 * 入力:          aaaxxxbbb12YYY_ZZZ.csv
 *
 * 抽出結果:
 *   hoge = "xxx"
 *   fuga = "12"
 *   piyo = "YYY"
 *   {3}  = "ZZZ"
 *
 * 【例3】最小・最大長の指定
 * テンプレート:  {hoge:2,3}{fuga:,3}{}{piyo:4,}
 * 入力:          aaabbbxxxxxxx.csv
 *
 * 抽出結果:
 *   hoge = "aaa"      (2〜3 文字)
 *   fuga = "bbb"      (最大 3 文字)
 *   {2}  = "xxxxxxx"  (任意長)
 *   piyo = ".csv"     (最小 4 文字)
 * </pre>
 *
 * <p>抽出結果は {@link ResultMap} として返され、キー名順に保持される。</p>
 */

public class TextExtractor {

	private static final Pattern TEMPLATE_PARSE_PATTERN = Pattern.compile("\\{([^:\\}]*)(:(([0-9]+)?(,([0-9]+)?)?))?\\}");

	public static TextExtractor of(String template) {
		return new TextExtractor(template);
	}

	private final String template;
	private final String keyRegx;
	private final Pattern compiledPattern;
	private final ArrayList<String> keyList = new ArrayList<>();

	protected TextExtractor(String template) {
		this.template = template;

		// {name:min,max} のパターン
		Pattern pattern = TEMPLATE_PARSE_PATTERN;
		Matcher matcher = pattern.matcher(template);

		StringBuilder sb = new StringBuilder();
		int templateIndex = 0;
		int injectIdx = 0; // キー部の登場毎の通番（キー未指定時のキーに使用）

		while (matcher.find(templateIndex)) {
			// キー部分「{}」で囲まれた範囲（キー名称がない場合は0始まりの通番を設定）
			String key = Texts.requireNonBlankElse(matcher.group(1), "{%d}".formatted(injectIdx));
			// キーが指定されていない場合用のインデックスをカウントアップ
			injectIdx++;

			// キーの桁数部分「:」以降の解析
			String option = matcher.group(3); // "n", "n,m", ",m" など
			String min = matcher.group(4);

			int start = matcher.start(); // {xxx:n}の先頭位置
			String beforeMatch = template.substring(templateIndex, start); // {xxx:n}が登場する直前までの文字列
			templateIndex = matcher.end(); // {xxx:n}の末端位置

			// 固定文字列を追加（正規表現エスケープ）
			if (!beforeMatch.isBlank()) {
				sb.append(Pattern.quote(beforeMatch));
			}

			// キー登録
			keyList.add(key);

			// 正規表現の追加
			if (option != null) {
				// min が空 → "0" を補完
				if (min == null) {
					option = "0" + option;
				}
				sb.append("(.{").append(option).append("})");
			} else {
				// 桁数の指定がない
				sb.append("(.*)");
			}
		}

		// 残りの固定文字列
		String other = template.substring(templateIndex);
		if (!other.isBlank()) {
			sb.append(Pattern.quote(other));
		}

		this.keyRegx = sb.toString();
		this.compiledPattern = Pattern.compile("^" + keyRegx + "$");
	}

	/**
	 * テンプレートに合致する指定された文字列から変数を抽出する
	 * @param input
	 * @return
	 */
	public final ResultMap extract(String input) {
		Matcher matcher = compiledPattern.matcher(input);
		if (!matcher.find()) {
			return new ResultMap(false);
		}

		ResultMap result = new ResultMap(true);
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String val = matcher.group(i + 1);
			result.put(key, val);
		}
		return result;
	}

	public TextExtractor peek(Consumer<String> cons) {
		cons.accept(this.toString());
		return this;
	}

	@Override
	public String toString() {
		return template + " -> " + keyRegx + " -> " + keyList;
	}

	@AllArgsConstructor
	public static final class ResultMap extends LinkedHashMap<String, String> implements IOptional<ResultMap> {

		@Getter
		private boolean isMatch = false;

		public final LinkedHashMap<String, String> toMap() {
			return this;
		}

		public final String[] toArray() {
			return this.values().stream().toArray(String[]::new);
		}

		public final List<String> toList() {
			return this.values().stream().toList();
		}

		@Override
		public boolean isPresent() {
			return isMatch;
		}

		@Override
		public ResultMap get() {
			return this;
		}

		public ResultMap peek(Consumer<LinkedHashMap<String, String>> action) {
			action.accept(this);
			return this;
		}
	}
}
