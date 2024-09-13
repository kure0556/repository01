package zircuf.util.text.function;

import java.util.ArrayList;
import java.util.List;

public final class Split {

	public static final String[] csv(String text) {
		return text.split(",");
	}

	public static final String[] csvDQ(String text) {
	    List<String> result = new ArrayList<>();
	    StringBuilder current = new StringBuilder();
	    boolean inQuotes = false;

	    for (char c : text.toCharArray()) {
	        if (c == '"') {
	            inQuotes = !inQuotes;
	        } else if (c == ',' && !inQuotes) {
	            result.add(current.toString());
	            current.setLength(0);
	        } else {
	            current.append(c);
	        }
	    }

	    // 最後の要素を追加
	    if (current.length() > 0) {
	        result.add(current.toString());
	    }
	    return (String[]) result.toArray();
	}

	public static final String[] tsv(String text) {
		return text.split("\t");
	}

	public static final String[] pipe(String text) {
		return text.split("\\|");
	}

}
