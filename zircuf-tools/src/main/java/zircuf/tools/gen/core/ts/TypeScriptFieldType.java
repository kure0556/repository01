package zircuf.tools.gen.core.ts;

import java.util.Objects;

import zircuf.tools.gen.core.base.FieldTemplate;

public enum TypeScriptFieldType implements FieldTemplate {

	STRING("string") {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			return "%s?: string;".formatted(name);
		}
	},
	INTEGER("number") {

	},
	BOOLEAN("boolean") {

	},
	OBJECT() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "%s?: %s;".formatted(name, option);
		}
	},
	LIST() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "%s?: Array<%s>;".formatted(name, option);
		}
	},
	MAP() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "%s?: Map<string, %s>;".formatted(name, option);
		}
	},
	LIST_MAP() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "%s?: Map<string, Array<%s>>;".formatted(name, option);
		}
	},
	STRING_LIST("Array<string>") {
	};

	private boolean hasChild = false;
	private String typeName = "";

	private TypeScriptFieldType() {
		this.hasChild = true;
	}

	private TypeScriptFieldType(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public boolean hasChild() {
		return hasChild;
	}

	@Override
	public String fieldCode(String name, String option) {
		Objects.requireNonNull(name, "name");
		return "%s?: %s;".formatted(name, typeName);
	}

}
