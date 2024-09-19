package zircuf.util.text.template;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @param <D> テンプレートに注入するデータ形式
 * @param <E> テンプレートに注入するデータ形式の注入処理
 */
interface TextTemplate<D, E extends TemplateItem<D>> {

	static <T> TextTemplate<T, ?> compile(TextTemplate<T, ?> templateX, final String template) {
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

	public List<E> itemList();

	public void addText(String text);

	public void addParts(String defaultValue, String key);

	default public String inject(D data) {
		StringBuilder sb = new StringBuilder();
		itemList().forEach(item -> item.inject(sb, data));
		return sb.toString();
	}

	default public String inject(StringBuilder sb, D data) {
		sb.setLength(0);
		itemList().forEach(item -> item.inject(sb, data));
		return sb.toString();
	}

	default public StringBuilder append(StringBuilder sb, D data) {
		itemList().forEach(item -> item.inject(sb, data));
		return sb;
	}

}

interface TemplateItem<T> {
	public StringBuilder inject(StringBuilder sb, T data);
}
