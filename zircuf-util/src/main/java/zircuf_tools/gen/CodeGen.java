package zircuf_tools.gen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zircuf.env.Resource;
import zircuf.util.text.function.Code;
import zircuf_tools.gen.core.base.FieldTemplate;
import zircuf_tools.gen.core.java.JavaCodeTemplate;
import zircuf_tools.gen.core.java.JavaFieldType;

public class CodeGen {

	public static void main(String[] args) {

		List<String[]> table = Resource.local().of("").asTsv().getTable();

		JavaCodeTemplate javaCodeTemplate = new JavaCodeTemplate();
		CodeGen codeGen = new CodeGen();
		Map<String, FieldTemplate> javaSet = codeGen.javaSet();
		StringBuilder javaCode = new StringBuilder();

		// ヘッダ
		javaCode.append(javaCodeTemplate.header(null, null));

		for (String[] line : table) {
			String typeStr = line[0];
			String pysicalName = Code.lowerSnakeToLowerCamel(line[1]); // キャメルケース変換
			String logicalName = line[2];

			// フィールド型の特定
			FieldTemplate fieldTemplate = javaSet.get(typeStr);
			if (fieldTemplate.hasChild()) {
				// フィールド追加
				javaCode.append(javaCodeTemplate.addField(fieldTemplate, pysicalName, logicalName));
				// 子クラスヘッダー
				javaCode.append(javaCodeTemplate.headerChild("Child1", "子クラス1"));
				// 子クラスフッター
				javaCode.append(javaCodeTemplate.footerChild());
			} else {
				// フィールド追加
				javaCode.append(javaCodeTemplate.addField(fieldTemplate, pysicalName, logicalName));
			}
		}

		// フッタ
		javaCode.append(javaCodeTemplate.footer());

	}

	public Map<String, FieldTemplate> javaSet() {
		HashMap<String, FieldTemplate> hashMap = new HashMap<String, FieldTemplate>();
		hashMap.put("文字列", JavaFieldType.STRING);
		hashMap.put("整数", JavaFieldType.INTEGER);
		hashMap.put("真偽値", JavaFieldType.BOOLEAN);
		hashMap.put("オブジェクト", JavaFieldType.OBJECT);
		hashMap.put("リスト", JavaFieldType.LIST);
		hashMap.put("マップ", JavaFieldType.MAP);
		hashMap.put("リストマップ", JavaFieldType.LIST_MAP);
		return hashMap;
	}
}
