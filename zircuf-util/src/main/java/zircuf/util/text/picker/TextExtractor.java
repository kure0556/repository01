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

	public static void main(String[] args) {
		//                           1         2 
		//                 012345678901234567890123456
		String template = "aaa{hoge}bbb{fuga:2}{piyo}c.cc{yyyy:4}{mm:2}{dd:2}-{}.csv";
		String input = "aaaxxxbbb123c.cc20241023-eoapijfwih.csv";

		System.out.println(new TextExtractor(template));
		Map<String, String> map = new TextExtractor(template).extract(input);
		System.out.println(map); // 出力: {hoge=xxx, fuga=123}
	}

	public static TextExtractor of(String template) {
		return new TextExtractor(template);
	}

	private final String template;
	private final String keyRegx;
	private final ArrayList<String> keyList = new ArrayList<String>();

	protected TextExtractor(String template) {
		this.template = template;
		Pattern pattern = Pattern.compile("\\{([^:\\}]*)(:([0-9]+))?\\}");
		Matcher matcher = pattern.matcher(template);

		StringBuilder sb = new StringBuilder();
		int templateIndex = 0;
		int injectIdx = 0;
		while (matcher.find(templateIndex)) {
			injectIdx++;
			String key = Texts.requireNonBlankElse(matcher.group(1), "{%d}".formatted(injectIdx)); // xxx
			String num = matcher.group(3); // n
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
			if (Objects.nonNull(num)) {
				// 桁数の指定あり
				sb.append("(.{").append(num).append("})");
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
			throw new IllegalArgumentException("入力された値はテンプレートの情報にマッチしていません template=%d input=%d".formatted(template, input));
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
		return template + " -> " + keyRegx +" -> " + keyList;
	}

}