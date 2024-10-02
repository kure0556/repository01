package zircuf.util.text.picker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * ex1) aaa{hoge}bbb{fuga:2}{piyo}_{}.csv
	 *   - hoge : aaaとbbbに挟まれた文字列を取得
	 *   - fuga : bbbの後の2桁の文字列を取得
	 *   - piyo : fuga2桁と_に挟まれた文字列を取得
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
			// キーが指定されていない場合用のインデックスをカウントアップ
			injectIdx++;
			// キー部分
			String key = Texts.requireNonBlankElse(matcher.group(1), "{%d}".formatted(injectIdx));
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

	public final Map<String, String> extract(String input) {
		Pattern pattern = Pattern.compile("^" + keyRegx + "$"); // 完全一致
		Matcher matcher = pattern.matcher(input);
		boolean found = matcher.find();
		if (!found) {
			throw new IllegalArgumentException(
					"入力された値はテンプレートの情報にマッチしていません template=%s input=%s".formatted(template, input));
		}

		Map<String, String> result = new LinkedHashMap<>();
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String val = matcher.group(i + 1);
			result.put(key, val);
		}
		return result;
	}

	public final String[] extractToArray(String input) {
		return extract(input).values().stream().toArray(String[]::new);
	}

	public final List<String> extractToList(String input) {
		return extract(input).values().stream().toList();
	}

	@Override
	public String toString() {
		return template + " -> " + keyRegx + " -> " + keyList;
	}

}