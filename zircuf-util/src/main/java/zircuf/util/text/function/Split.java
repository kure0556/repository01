package zircuf.util.text.function;

public final class Split {

	public static final String[] csv(String text) {
		return text.split(",");
	}

	public static final String[] tsv(String text) {
		return text.split("\t");
	}

}
