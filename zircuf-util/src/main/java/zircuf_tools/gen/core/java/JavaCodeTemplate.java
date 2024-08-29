package zircuf_tools.gen.core.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import zircuf_tools.gen.core.base.CodeTemplate;
import zircuf_tools.gen.core.base.FieldTemplate;
import zircuf_tools.gen.core.base.IHasPackage;
import zircuf_tools.gen.core.base.IInnerChilds;

public class JavaCodeTemplate implements CodeTemplate, IHasPackage, IInnerChilds {

	public String header(String pName, String lName, String option) {
		pName = Objects.requireNonNullElse(pName, FIX_ME);
		lName = Objects.requireNonNullElse(lName, FIX_ME);
		option = Objects.requireNonNullElse(option, "");
		return """
				/**
				 * %s
				 */
				@Data
				public class %s %s {
				""".formatted(lName, pName, option);
	};

	public String headerChild(String pName, String lName, String option) {
		pName = Objects.requireNonNullElse(pName, FIX_ME);
		lName = Objects.requireNonNullElse(lName, FIX_ME);
		option = Objects.requireNonNullElse(option, "");
		return """

				/**
				 * %s
				 */
				@Data
				public static class %s %s {
				""".formatted(lName, pName, option);
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
		hashMap.put("文字列", JavaFieldType.STRING);
		hashMap.put("整数", JavaFieldType.INTEGER);
		hashMap.put("真偽値", JavaFieldType.BOOLEAN);
		hashMap.put("オブジェクト", JavaFieldType.OBJECT);
		hashMap.put("リスト", JavaFieldType.LIST);
		hashMap.put("マップ", JavaFieldType.MAP);
		hashMap.put("リストマップ", JavaFieldType.LIST_MAP);
		hashMap.put("文字列リスト", JavaFieldType.STRING_LIST);
		return hashMap;
	}

	@Override
	public String packageText(String packagePath) {
		packagePath = Objects.requireNonNullElse(packagePath, FIX_ME);
		return """
				import %s;

				""".formatted(packagePath);
	}

}
