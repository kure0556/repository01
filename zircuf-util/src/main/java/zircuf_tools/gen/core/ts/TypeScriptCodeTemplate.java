package zircuf_tools.gen.core.ts;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import zircuf_tools.gen.core.base.CodeTemplate;
import zircuf_tools.gen.core.base.FieldTemplate;

public class TypeScriptCodeTemplate implements CodeTemplate {
	
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

	public String headerChild(String pName, String lName, String option) {
		return System.lineSeparator() + header(pName, lName, option);
	};

	public String addField(FieldTemplate field, String pName, String lName, String option,
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

	public String footer() {
		return """
				}
				""";
	};

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
