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
 * 
 * <p>【性能に関する注意】
 * Java 17 以降の String::formatted は、内部的に invokedynamic と
 * StringConcatFactory によって強力に最適化されており、固定フォーマットの
 * 文字列生成では非常に高速です。特に JSON のように構造が固定されている場合、
 * TextTemplate よりも formatted のほうが高速になることがあります。
 *
 * 【TextTemplate を使うべき場面】
 * - キーの有無による default 値の挿入が必要な場合
 * - Map / DTO など任意データを柔軟に埋め込みたい場合
 * - テンプレートを外部ファイルとして管理したい場合
 * - JSON 構造が動的に変わる場合
 * - escape 処理を統一的に扱いたい場合
 *
 * 固定フォーマット → formatted が最速  
 * 可変フォーマット・柔軟性 → TextTemplate が有効</p>
 * 
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
