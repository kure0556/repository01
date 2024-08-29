package zircuf_tools.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import zircuf.env.Resource;
import zircuf.util.text.function.Code;
import zircuf_tools.gen.core.base.CodeTemplate;
import zircuf_tools.gen.core.base.FieldTemplate;
import zircuf_tools.gen.core.base.IHasPackage;
import zircuf_tools.gen.core.java.JavaCodeTemplate;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
@Builder
public class CodeGenerator {

	public static void main(String[] args) {

		List<String[]> table = Resource.local().of("codegen.tsv").asTsv().getTable();

		CodeTemplate codeTemplate = new JavaCodeTemplate();
//		CodeTemplate codeTemplate = new TypeScriptCodeTemplate();

		CodeGenerator codeGenerator = CodeGenerator.builder()
				.table(table)
				.codeTemplate(codeTemplate)
				.pysicalNameIdx(0)
				.logicalNameIdx(10)
				.typeTxetIdx(9)
				.classPysicalName(null)
				.classLogicalName(null)
				.extendsOrImplementsText(null)
				.packageText(null)
				.build();
		String string = codeGenerator.generateCode();

		System.out.println(string);
	}

	/**
	 * 入力データのテーブル
	 */
	private final List<String[]> table;
	/**
	 * コードテンプレート
	 */
	private final CodeTemplate codeTemplate;

	private final int pysicalNameIdx;
	private final int logicalNameIdx;
	private final int typeTxetIdx;

	private String classPysicalName;
	private String classLogicalName;
	private String extendsOrImplementsText;
	private String packageText;

	private String generateCode() {
		Objects.requireNonNull(table, "table");
		Objects.requireNonNull(codeTemplate, "codeTemplate");

		// コードビルダースタック
		CodeBuilderStack builder = new CodeBuilderStack();

		// packageテキスト
		if (codeTemplate instanceof IHasPackage iHasPackage) {
			builder.append(iHasPackage.packageText(packageText));
		}

		// ヘッダ
		builder.append(codeTemplate.header(classPysicalName, classLogicalName, extendsOrImplementsText));
		int childCnt = 0;

		// field定義
		Map<String, FieldTemplate> fieldSet = codeTemplate.fieldSet();
		for (String[] line : table) {

			// 子クラスのフィールド終了判定
			while (line[pysicalNameIdx + builder.stackSize()].isBlank() && builder.stackIsPresent()) {
				builder.append(codeTemplate.footerChild());
				builder.pop();
			}

			// スタックが空で物理名が取れない場合は行をスキップ
			if (line[pysicalNameIdx + builder.stackSize()].isBlank() && builder.stackIsEmpty()) {
				continue;
			}

			// 物理名をキャメルケース変換
			String pysicalName = Code.lowerSnakeToLowerCamel(line[pysicalNameIdx + builder.stackSize()]);
			String logicalName = line[logicalNameIdx];
			String typeStr = line[typeTxetIdx];

			// フィールド型の特定
			FieldTemplate fieldTemplate = fieldSet.get(typeStr);
			if (Objects.isNull(fieldTemplate)) {
				System.err.println("サポートされていない型です typeStr=%s pysicalName=%s logicalName=%s".formatted(typeStr,
						pysicalName, logicalName));
				continue;
			} else if (fieldTemplate.hasChild()) {
				String childPysiName = "Child" + childCnt;
				String childLogiName = "子クラス" + childCnt;
				childCnt++;

				// フィールド追加
				builder.append(codeTemplate.addField(fieldTemplate, pysicalName, logicalName, childPysiName));

				// 子クラス開始
				builder.push();
				// 子クラスヘッダー
				builder.append(codeTemplate.headerChild(childPysiName, childLogiName));

			} else {
				// フィールド追加
				builder.append(codeTemplate.addField(fieldTemplate, pysicalName, logicalName));
			}
		}
		// フィールドの終了判定 スタックが空になるまで子クラスフッターを出力
		while (builder.stackIsPresent()) {
			// 子クラスフッター
			builder.append(codeTemplate.footerChild());
			builder.pop();
		}

		// フッタ（子クラスが外部の場合）
		if (!codeTemplate.isInnerChilds()) {
			builder.append(codeTemplate.footer());
		}

		// 子クラスの全内容出力
		builder.appendAll();

		// フッタ（子クラスが内部の場合）
		if (codeTemplate.isInnerChilds()) {
			builder.append(codeTemplate.footer());
		}

		return builder.toString();
	}

	public static class CodeBuilderStack {

		Stack<StringBuilder> stack = new Stack<StringBuilder>();

		private StringBuilder code = new StringBuilder();

		private List<StringBuilder> allBuilder = new ArrayList<StringBuilder>();

		public CodeBuilderStack append(String str) {
			code.append(str);
			return this;
		}

		public CodeBuilderStack append(StringBuilder sb) {
			code.append(sb);
			return this;
		}

		public String toString() {
			return code.toString();
		}

		public int stackSize() {
			return stack.size();
		}

		public boolean stackIsEmpty() {
			return stack.isEmpty();
		}

		public boolean stackIsPresent() {
			return !stack.isEmpty();
		}

		public void push() {
			stack.push(code);
			code = new StringBuilder();
		}

		public void pop() {
			allBuilder.add(code);
			code = stack.pop();
		}

		public void appendAll() {
			for (StringBuilder child : allBuilder) {
				code.append(child);
			}
			allBuilder.clear();
		}
	}
}
