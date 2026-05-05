package zircuf.util.text.template;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * テンプレート文字列を構造化し、データを注入して文字列を生成するための
 * 抽象インターフェース。
 *
 * <p>テンプレートは以下の 2 種類の要素から構成される：</p>
 * <ul>
 *   <li><b>テキスト要素</b>（そのまま出力される文字列）</li>
 *   <li><b>パラメータ要素</b>（データから値を取得して出力する）</li>
 * </ul>
 *
 * <h3>テンプレートのパース</h3>
 * <p>{@link #compile(TextTemplate, String)} はテンプレート文字列を解析し、
 * 実装クラスが提供する {@code addText()} と {@code addParts()} を呼び出して
 * TemplateItem のリストを構築する。</p>
 *
 * <p>パラメータの構文（例：<code>${key}</code> や <code>${0}</code>）や
 * デフォルト値（<code>${key:default}</code>）の扱いは、
 * 各実装クラス（MapTemplate / ArrayTemplate など）が定義する。</p>
 *
 * <h3>テンプレートの拡張方法</h3>
 * <p>独自のテンプレート形式を追加したい場合は、以下を実装する：</p>
 *
 * <ol>
 *   <li>{@code TextTemplate<D, E>} を実装したクラスを作成する</li>
 *   <li>{@code addText(String)} でプレーンテキスト要素を追加する</li>
 *   <li>{@code addParts(String defaultValue, String key)} で
 *       パラメータ要素を追加する</li>
 *   <li>{@code TemplateItem&lt;D&gt;} を実装した要素クラス（E）を作成し、
 *       {@code inject()} でデータ注入ロジックを定義する</li>
 * </ol>
 *
 * <p>これにより、MapTemplate や ArrayTemplate と同様の仕組みで
 * 任意のテンプレート構文を扱える。</p>
 *
 * <h3>データ注入</h3>
 * <p>{@code inject()} は内部の TemplateItem を順に処理し、
 * StringBuilder に出力を構築する。</p>
 *
 * @param <D> テンプレートに注入するデータ型
 * @param <E> テンプレートを構成する要素（TemplateItem の実装）
 */
interface TextTemplate<D, E extends TemplateItem<D>> {

	public static final Pattern TEMPLATE_PARSE_PATTERN = Pattern.compile("\\$\\{([^\\$\\{\\}]+)\\}");

	static <T extends TextTemplate<?, ?>> T compile(T templateX, final String template) {
		Pattern pattern = TEMPLATE_PARSE_PATTERN;
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
