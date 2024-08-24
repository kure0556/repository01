package zircuf_tools.gen;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import zircuf.env.Resource;
import zircuf.util.text.function.Code;
import zircuf_tools.gen.core.base.CodeTemplate;
import zircuf_tools.gen.core.base.FieldTemplate;
import zircuf_tools.gen.core.ts.TypeScriptCodeTemplate;

public class CodeGenSample {

	public static void main(String[] args) {

		List<String[]> table = Resource.local().of("codegen.tsv").asTsv().getTable();

//		CodeTemplate codeTemplate = new JavaCodeTemplate();
		CodeTemplate codeTemplate = new TypeScriptCodeTemplate();

		Map<String, FieldTemplate> fieldSet = codeTemplate.fieldSet();
		StringBuilder javaCode = new StringBuilder();

		// ヘッダ
		javaCode.append(codeTemplate.header(null, null));
		int childCnt = 0;

		for (String[] line : table) {
			String typeStr = line[0];
			String pysicalName = Code.lowerSnakeToLowerCamel(line[1]); // キャメルケース変換
			String logicalName = line[2];

			// フィールド型の特定
			FieldTemplate fieldTemplate = fieldSet.get(typeStr);
			if (Objects.isNull(fieldTemplate)) {
				System.out.println("サポートされていない型です -> " + typeStr);
				continue;
			} else if (fieldTemplate.hasChild()) {
				String childName = "Child" + childCnt;
				// フィールド追加
				javaCode.append(codeTemplate.addField(fieldTemplate, pysicalName, logicalName, childName));
				// 子クラスヘッダー
				javaCode.append(codeTemplate.headerChild(childName, "子クラス" + childCnt));
				// 子クラスフッター
				javaCode.append(codeTemplate.footerChild());
				childCnt++;
			} else {
				// フィールド追加
				javaCode.append(codeTemplate.addField(fieldTemplate, pysicalName, logicalName));
			}
		}

		// フッタ
		javaCode.append(codeTemplate.footer());

		System.out.println(javaCode.toString());
	}

}
