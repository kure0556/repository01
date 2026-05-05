package zircuf.util.text.template;

/**
 * テンプレート文字列から適切な TextTemplate 実装を生成するための
 * ファクトリクラス。
 *
 * <p>本クラスはテンプレートエンジンの利用者向け API として、
 * 以下の 2 種類のテンプレートを簡潔に生成できるようにする：</p>
 *
 * <h3>1. MapTemplate（${key} 形式）</h3>
 * <p>テンプレート内の <code>${key}</code> を
 * <code>Map&lt;String, String&gt;</code> の値で置換する。</p>
 *
 * <pre>{@code
 * MapTemplate t = Template.ofMap("Hello ${name:Guest}");
 * String s = t.inject(Map.of("name", "Alice"));
 * // → "Hello Alice"
 * }</pre>
 *
 * <h3>2. ArrayTemplate（${0}, ${1} 形式）</h3>
 * <p>テンプレート内の <code>${0}</code> や <code>${1}</code> を
 * 配列の要素で置換する。</p>
 *
 * <pre>{@code
 * ArrayTemplate t = Template.ofArray("Hello ${0}, today is ${1}");
 * String s = t.injectArgs("Alice", "Tuesday");
 * // → "Hello Alice, today is Tuesday"
 * }</pre>
 *
 * <p>本クラスはユーティリティであり、インスタンス化はできない。</p>
 */
public class Template {

	private Template() {
	}

	public static ArrayTemplate ofArray(String template) {
		return TextTemplate.compile(new ArrayTemplate(), template);
	}

	public static MapTemplate ofMap(String template) {
		return TextTemplate.compile(new MapTemplate(), template);
	}

}
