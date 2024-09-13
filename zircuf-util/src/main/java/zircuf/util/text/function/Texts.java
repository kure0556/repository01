package zircuf.util.text.function;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Texts {

	public static Stream<String> lines(String text) {
		return text.lines();
	}

	public static String lineIdx(String[] line, int idx) {
		if (idx < line.length)
			return line[idx];
		throw new ArrayIndexOutOfBoundsException("Index %d out of bounds for length %d : %s"
				.formatted(idx, line.length, Arrays.toString(line)));
	}

	public static String summry(List<String[]> data) {
		return summry(data, Join::pipe);
	}

	public static <T> String summry(List<T> data, Function<T, String> toStr) {
		if (Objects.isNull(data)) {
			return "table data is null.";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.min(data.size(), 10); i++) {
			sb.append(toStr.apply(data.get(i))).append("\n");
		}
		if (data.size() > 10) {
			if (data.size() > 11) {
				sb.append("  :").append("\n");
			}
			sb.append(toStr.apply(data.get(data.size() - 1))).append("\n");
		}
		sb.append("--- table data has %d line(s).".formatted(data.size()));
		return sb.toString();
	}
}
