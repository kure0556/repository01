package zircuf_tools.gen.core.base;

import java.util.Map;

public interface CodeTemplate {

	public static final String FIX_ME = "__FIX_ME__";

	default public String header(String pName, String lName) {
		return header(pName, lName, null);
	}

	public String header(String pName, String lName, String option);

	default public String headerChild(String pName, String lName) {
		return headerChild(pName, lName, null);
	}

	public String headerChild(String pName, String lName, String option);

	default public String addField(FieldTemplate field, String pName, String lName) {
		return field(field, pName, lName, null, null);
	}

	default public String addField(FieldTemplate field, String pName, String lName, String option) {
		return field(field, pName, lName, option, null);
	}

	public String field(FieldTemplate field, String pName, String lName, String option, String[] annotation);

	public String footer();

	default public String footerChild() {
		return footer();
	};

	public Map<String, FieldTemplate> fieldSet();

	public default boolean isInnerChilds() {
		return this instanceof IInnerChilds;
	}

}
