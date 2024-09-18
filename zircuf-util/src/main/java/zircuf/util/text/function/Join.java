package zircuf.util.text.function;

public final class Join {

	public static final String csv(String[] text) {
		return String.join(",", text);
	}

	public static final String csvDQ(String[] text) {
        StringBuilder sb = new StringBuilder();
        for (String s : text) {
            sb.append('"').append(s.replace("\"", "\"\"")).append('"').append(',');
        }
        // 最後のカンマを削除
        sb.deleteCharAt(sb.length() - 1);
		return String.join(",", text);
	}

	public static final String tsv(String[] text) {
		return String.join("\t", text);
	}

	public static final String pipe(String[] text) {
		return String.join("|", text);
	}

}
