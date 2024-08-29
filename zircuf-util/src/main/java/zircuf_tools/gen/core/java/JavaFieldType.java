package zircuf_tools.gen.core.java;

import java.util.Objects;

import zircuf_tools.gen.core.base.FieldTemplate;

public enum JavaFieldType implements FieldTemplate {

	STRING("String") {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			if (Objects.isNull(option)) {
				return "private String %s;".formatted(name);
			} else {
				return "private String %s = \"%s\";".formatted(name, option);
			}
		}
	},
	INTEGER("Integer") {

	},
	BOOLEAN("Boolean") {

	},
	OBJECT() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "private %s %s;".formatted(option, name);
		}
	},
	LIST() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "private List<%s> %s = new ArrayList<%s>();".formatted(option, name, option);
		}
	},
	MAP() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "private Map<String, %s> %s = new LinkedHashMap<String, %s>();".formatted(option, name, option);
		}
	},
	LIST_MAP() {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			Objects.requireNonNull(option, "option");
			return "private Map<String, List<%s>> %s = new LinkedHashMap<String, List<%s>>();".formatted(option, name,
					option);
		}
	},
	STRING_LIST("List<String>") {
		@Override
		public String fieldCode(String name, String option) {
			Objects.requireNonNull(name, "name");
			return "private List<String> %s = new ArrayList<String>();".formatted(name);
		}
	};

	private boolean hasChild = false;
	private String typeName = "";

	private JavaFieldType() {
		this.hasChild = true;
	}

	private JavaFieldType(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public boolean hasChild() {
		return hasChild;
	}

	@Override
	public String fieldCode(String name, String option) {
		Objects.requireNonNull(name, "name");
		if (Objects.isNull(option)) {
			return "private %s %s;".formatted(typeName, name);
		} else {
			return "private %s %s = %s;".formatted(typeName, name, option);
		}
	}

}
