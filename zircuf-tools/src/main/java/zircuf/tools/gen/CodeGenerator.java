package zircuf.tools.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import zircuf.tools.gen.core.base.CodeTemplate;
import zircuf.tools.gen.core.base.FieldTemplate;
import zircuf.tools.gen.core.base.IHasPackage;
import zircuf.util.text.function.Code;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
@Builder
public class CodeGenerator {

	/** 入力データのテーブル */
	private final List<String[]> table;
	/** コードテンプレート */
	private final CodeTemplate codeTemplate;

	/** 物理名の列インデックス */
	private final int pysicalNameIdx;
	/** 論理名の列インデックス */
	private final int logicalNameIdx;
	/** 型名の列インデックス */
	private final int typeTxetIdx;

	/** クラスの物理名 */
	private String classPhysicalName;
	/** クラスの論理名 */
	private String classLogicalName;
	/** クラスに付与する継承関係 */
	private String extendsOrImplementsText;
	/** クラスのパッケージ */
	private String packageText;

	public String generateCode() {
		Objects.requireNonNull(table, "table");
		Objects.requireNonNull(codeTemplate, "codeTemplate");

		// コードビルダースタック
		CodeBuilderStack builder = new CodeBuilderStack();

		// packageテキスト
		if (codeTemplate instanceof IHasPackage iHasPackage) {
			builder.append(iHasPackage.packageText(packageText));
		}

		// ヘッダ
		builder.append(codeTemplate.header(classPhysicalName, classLogicalName, extendsOrImplementsText));
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
			String pysicalName = Code.toLowerCamel(line[pysicalNameIdx + builder.stackSize()]);
			String logicalName = line[logicalNameIdx];
			String typeStr = line[typeTxetIdx];

			// フィールド型の特定
			FieldTemplate fieldTemplate = fieldSet.get(typeStr);
			if (Objects.isNull(fieldTemplate)) {
				System.err.println("サポートされていない型です typeStr=%s pysicalName=%s logicalName=%s"
						.formatted(typeStr, pysicalName, logicalName));
				continue;
			} else if (fieldTemplate.hasChild()) {
//				String childPysiName = "Child" + childCnt;
				String childPysiName = Code.firstCharOnlyToUpper(Code.convertSingletonName(pysicalName));
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
			allBuilder.add(code);
		}

		public void pop() {
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
