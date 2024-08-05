package zircuf_tools.gen.core.java;

import java.util.Objects;

import zircuf_tools.gen.core.base.CodeTemplate;
import zircuf_tools.gen.core.base.FieldTemplate;

public class JavaCodeTemplate implements CodeTemplate {
	
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
				/**
				 * %s
				 */
				%s
				""".formatted(lName, fieldCode);
	};

	public String footer() {
		return """
				}
				""";
	};

}
