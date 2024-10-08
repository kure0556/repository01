package zircuf.tools.gen.core.ts;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import zircuf.tools.gen.core.base.CodeTemplate;
import zircuf.tools.gen.core.base.FieldTemplate;

public class TypeScriptCodeTemplate implements CodeTemplate {

	@Override
	public String header(String pName, String lName, String option) {
		pName = Objects.requireNonNullElse(pName, FIX_ME);
		lName = Objects.requireNonNullElse(lName, FIX_ME);
		option = Objects.requireNonNullElse(option, "");
		return """
				/**
				 * %s
				 */
				export interface %s %s {
				""".formatted(lName, pName, option);
	};

	@Override
	public String headerChild(String pName, String lName, String option) {
		return System.lineSeparator() + header(pName, lName, option);
	};

	@Override
	public String field(FieldTemplate field, String pName, String lName, String option,
			String[] annotation) {
		Objects.requireNonNull(field);
		pName = Objects.requireNonNullElse(pName, FIX_ME);
		lName = Objects.requireNonNullElse(lName, FIX_ME);
		if (field.hasChild()) {
			option = Objects.requireNonNullElse(option, FIX_ME);
		}
		String fieldCode = field.fieldCode(pName, option);
		return """
				
				/** %s */
				%s
				""".formatted(lName, fieldCode);
	};

	@Override
	public String footer() {
		return """
				}
				""";
	};

	@Override
	public Map<String, FieldTemplate> fieldSet() {
		HashMap<String, FieldTemplate> hashMap = new HashMap<String, FieldTemplate>();
		hashMap.put("文字列", TypeScriptFieldType.STRING);
		hashMap.put("整数", TypeScriptFieldType.INTEGER);
		hashMap.put("真偽値", TypeScriptFieldType.BOOLEAN);
		hashMap.put("オブジェクト", TypeScriptFieldType.OBJECT);
		hashMap.put("リスト", TypeScriptFieldType.LIST);
		hashMap.put("マップ", TypeScriptFieldType.MAP);
		hashMap.put("リストマップ", TypeScriptFieldType.LIST_MAP);
		hashMap.put("文字列リスト", TypeScriptFieldType.STRING_LIST);
		return hashMap;
	}
}
