package zircuf.util.text.extractor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zircuf.util.general.IOptional;
import zircuf.util.text.Texts;

public class TextExtractor {

	public static TextExtractor of(String template) {
		return new TextExtractor(template);
	}

	private final String template;
	private final String keyRegx;
	private final ArrayList<String> keyList = new ArrayList<String>();

	/**
	 * 
	 * <pre>
	 * ex1) aaa{}bbb{:2}{}_{}.csv
	 *   - [0] "{0}" : aaaとbbbに挟まれた文字列を取得
	 *   - [1] "{1}" : bbbの後の2桁の文字列を取得
	 *   - [2] "{2}" : 2桁と_に挟まれた文字列を取得
	 *   - [3] "{3}" : _と.csvの間の変数名が省略された{}の位置の文字を取得
	 * ex1) aaa{hoge}bbb{fuga:2}{piyo}_{}.csv
	 *   - [0] "hoge" : aaaとbbbに挟まれた文字列を取得
	 *   - [1] "fuga" : bbbの後の2桁の文字列を取得
	 *   - [2] "piyo" : fuga2桁と_に挟まれた文字列を取得
	 *   - [3] "{3}"  : _と.csvの間の変数名が省略された{}の位置の文字を取得
	 * </pre>
	 * @param template
	 */
	protected TextExtractor(String template) {
		this.template = template;
		//                                    1         2 34
		Pattern pattern = Pattern.compile("\\{([^:\\}]*)(:(([0-9]+)?(,([0-9]+)?)?))?\\}");
		Matcher matcher = pattern.matcher(template);

		StringBuilder sb = new StringBuilder();
		int templateIndex = 0;
		int injectIdx = 0;
		while (matcher.find(templateIndex)) {
			// キー部分
			String key = Texts.requireNonBlankElse(matcher.group(1), "{%d}".formatted(injectIdx));
			// キーが指定されていない場合用のインデックスをカウントアップ
			injectIdx++;
			// キーの桁数部分「:」を含む
			String option = matcher.group(3); // 桁数部
			String min = matcher.group(4); // 最小値
			int start = matcher.start(); // {xxx:n}の先頭位置
			String pre = template.substring(templateIndex, start); // {xxx:n}が登場する直前までの文字列
			templateIndex = matcher.end(); // {xxx:n}の末端位置

			// 正規表現に固定文字列を追加
			if (!pre.isBlank()) {
				// エスケープして追加
				sb.append(Pattern.quote(pre));
			}

			// キーリストにキーを追加
			keyList.add(key);

			// 正規表現の追加
			if (Objects.nonNull(option)) {
				// 桁数の指定あり
				if (Objects.isNull(min)) {
					// 最小桁部がない場合は、以下の例外が出るため0を補完する
					// PatternSyntaxException: Illegal repetition
					option = "0" + option;
				}
				// 桁数判定を正規表現に追加
				sb.append("(.{").append(option).append("})");
			} else {
				// 桁数の指定なし
				sb.append("(.*)");
			}
		}

		// 正規表現に固定文字列を追加
		String other = template.substring(templateIndex);
		if (!other.isBlank()) {
			sb.append(Pattern.quote(other));
		}
		this.keyRegx = sb.toString();

	}

	/**
	 * テンプレートに合致する指定された文字列から変数を抽出する
	 * @param input
	 * @return
	 */
	public final ResultMap extract(String input) {
		Pattern pattern = Pattern.compile("^" + keyRegx + "$"); // 完全一致
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if (!found) {
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