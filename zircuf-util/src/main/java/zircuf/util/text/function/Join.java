package zircuf.util.text.function;

public final class Join {

	public static final String csv(String[] text) {
		return String.join(",", text);
	}

	public static final String tsv(String[] text) {
		return String.join("\t", text);
	}

	public static final String pipe(String[] text) {
		return String.join("|", text);
	}

}
