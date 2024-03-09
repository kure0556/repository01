package zircuf.util.text.function;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Text {

	public static Stream<String> lines(String text) {
		return text.lines();
	}

	public static String lineIdx(String[] line, int idx) {
		if (idx < line.length)
			return line[idx];
		throw new ArrayIndexOutOfBoundsException("Index %d out of bounds for length %d : %s"
				.formatted(idx, line.length, Arrays.toString(line)));
	}

	public static <T> String summry(List<T> data, Function<T, String> henkan) {
		if (Objects.isNull(data)) {
			return "table data is null.";
		}
		StringBuilder sb = new StringBuilder();
		data.stream().limit(10).forEach(line -> {
			sb.append(henkan.apply(line));
		});
		return sb.toString();
	}
}
