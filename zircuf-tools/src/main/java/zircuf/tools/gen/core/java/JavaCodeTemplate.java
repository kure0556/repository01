package zircuf.tools.gen.core.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import zircuf.tools.gen.core.base.CodeTemplate;
import zircuf.tools.gen.core.base.FieldTemplate;
import zircuf.tools.gen.core.base.IChildsInside;
import zircuf.tools.gen.core.base.IHasPackage;

public class JavaCodeTemplate implements CodeTemplate, IHasPackage, IChildsInside {

	@Override
	public String header(String pName, String lName, String option) {
		pName = Objects.requireNonNullElse(pName, FIX_ME);
		lName = Objects.requireNonNullElse(lName, FIX_ME);
		option = Objects.requireNonNullElse(option, "");
		return """
				/**
				 * %s
				 */
				@Data
				@Builder
				public class %s %s {
				""".formatted(lName, pName, option);
	};

	@Override
	public String headerChild(String pName, String lName, String option) {
		pName = Objects.requireNonNullElse(pName, FIX_ME);
		lName = Objects.requireNonNullElse(lName, FIX_ME);
		option = Objects.requireNonNullElse(option, "");
		return """

				/**
				 * %s
				 */
				@Data
				@Builder
				public static class %s %s {
				""".formatted(lName, pName, option);
	};

	@Override
	public String field(FieldTemplate field, String pName, String lName, String option, String[] annotation) {
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
				package %s;

				""".formatted(packagePath);
	}

}
