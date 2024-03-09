package zircuf.util.text.template;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {

	public static TextTemplate<String[]> ofArray(String template) {
		return compile(new ArrayTemplate(), template);
	}

	public static TextTemplate<Map<String, String>> ofMap(String template) {
		return compile(new MapTemplate(), template);
	}

	private static final <T> TextTemplate<T> compile(TemplateCore<T, ?> templateX, final String template) {
		Pattern pattern = Pattern.compile("\\$\\{([^\\$\\{\\}]+)\\}");
		Matcher m = pattern.matcher(template);
		int i = 0;
		while (i < template.length() && m.find(i)) {
			if (i < m.start()) {
				// マッチ箇所前までを抽出
				String previus = template.substring(i, m.start());
				templateX.addText(previus);
			}
			String key = m.group(1);
			String defaultText = null;
			if (key.contains(":")) {
				// デフォルト値を抽出
				String[] split = key.split(":", 2);
				key = split[0];
				defaultText = split[1];
			}
			// マッチ箇所を抽出
			templateX.addParts(defaultText, key);
			i = m.end();
		}
		if (i < template.length()) {
			// 末尾までを抽出
			String end = template.substring(i, template.length());
			templateX.addText(end);
		}
		return templateX;
	}

}
