package zircuf.util.data.table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import zircuf.util.performance.Performance;
import zircuf.util.text.function.Split;
import zircuf.util.text.function.Text;

@RequiredArgsConstructor(staticName = "of")
public class CodeTable {

	public static void main(String[] args) {
		List<String[]> list = IntStream.range(1, 10000).mapToObj(i -> {
			return i + "," + i;
		}).map(Split::csv).toList();

		var data = List.of(
				List.of("123", "ひふみ").toArray(String[]::new),
				List.of("456", "しごろ").toArray(String[]::new));
		String[] convert = CodeTable.of(data).get("123", 0);
		System.out.println(Arrays.toString(convert));

//		new Performance() {
//			@Override
//			protected void proc() {
//				CodeTable.of(list).get("5000", 0);
//			}
//		}.keisoku();

		//		Converter converter = CodeTable.of(list).complie();
		//		String string = converter.get("5000", 1);
		//		System.out.println(string);

		Converter converter = CodeTable.of(list).converter();
		new Performance() {
			@Override
			protected void proc() {
				converter.get("5000", 1);
			}
		}.keisoku();

	}

	@NonNull
	private final List<String[]> table;

	List<String[]> getTable() {
		return table;
	}

	public final Stream<String[]> stream() {
		return table.stream();
	}

	public final Optional<String[]> find(String input, int fromIdx) {
		return Optional.ofNullable(get(input, fromIdx));
	}

	public final Optional<String> find(String input, int fromIdx, int toIdx) {
		return Optional.ofNullable(get(input, fromIdx, toIdx));
	}

	@Deprecated
	public final String[] get(String input, int fromIdx) {
		Objects.requireNonNull(input, "input");
		try {
			for (String[] line : table)
				if (input.equals(line[fromIdx]))
					return line;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("配列長エラー fromIdx=" + fromIdx, e);
		}
		return null;
	}

	@Deprecated
	public final String get(String input, int fromIdx, int toIdx) {
//		return get(input, fromIdx)[toIdx];
		return Text.lineIdx(get(input, fromIdx), toIdx);
	}

	public final Converter converter() {
		return new Converter(table, 0);
	}

	public final Converter converter(int fromIdx) {
		return new Converter(table, fromIdx);
	}

	public static final class Converter {

		HashMap<String, String[]> compiledMap = new HashMap<>();

		private Converter(@NonNull List<String[]> table, int fromIdx) {
			try {
				table.stream().forEach(line -> {
					compiledMap.put(line[fromIdx], line);
				});
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new RuntimeException("配列長エラー fromIdx=" + fromIdx, e);
			}
		}

		public final Optional<String[]> find(String input) {
			return Optional.ofNullable(get(input));
		}

		public final Optional<String> find(String input, int toIdx) {
			return Optional.ofNullable(get(input, toIdx));
		}

		@Deprecated
		public final String[] get(String input) {
			Objects.requireNonNull(input, "input");
			return compiledMap.get(input);
		}

		@Deprecated
		public final String get(String input, int toIdx) {
			Objects.requireNonNull(input, "input");
//			return compiledMap.get(input)[toIdx];
			return Text.lineIdx(compiledMap.get(input), toIdx);
		}
	}
}
