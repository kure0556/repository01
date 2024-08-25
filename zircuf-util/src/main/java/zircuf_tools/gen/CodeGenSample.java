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
		StringBuilder code = new StringBuilder();

		// ヘッダ
		code.append(codeTemplate.header(null, null));
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
				code.append(codeTemplate.addField(fieldTemplate, pysicalName, logicalName, childName));
				// 子クラスヘッダー
				code.append(codeTemplate.headerChild(childName, "子クラス" + childCnt));
				// 子クラスフッター
				code.append(codeTemplate.footerChild());
				childCnt++;
			} else {
				// フィールド追加
				code.append(codeTemplate.addField(fieldTemplate, pysicalName, logicalName));
			}
		}

		// フッタ
		code.append(codeTemplate.footer());

		System.out.println(code.toString());
	}

}
