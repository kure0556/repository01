package zircuf.util.data.table;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.NonNull;
import zircuf.util.text.Texts;

public interface TableConverter {

	public List<String[]> getTable();

	default public Converter converter() {
		return new Converter(getTable(), 0);
	}

	default public Converter converter(int fromIdx) {
		return new Converter(getTable(), fromIdx);
	}

	/**
	 * Converter<br/>
	 * 特定の列（fromIdx）を起点に該当行に変換
	 */
	public static final class Converter {

		private LinkedHashMap<String, String[]> compiledMap = new LinkedHashMap<>();

		private Converter(@NonNull List<String[]> table, int fromIdx) {
			try {
				table.forEach(line -> {
					compiledMap.put(line[fromIdx], line);
				});
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new RuntimeException("配列長エラー fromIdx=" + fromIdx, e);
			}
		}

		public final Optional<String[]> find(String input) {
			return Optional.ofNullable(get(input));
		}

		public final String[] get(String input) {
			Objects.requireNonNull(input, "input");
			return compiledMap.get(input);
		}

		public final String get(String input, int toIdx) {
			Objects.requireNonNull(input, "input");
			String[] line = compiledMap.get(input);
			Objects.requireNonNull(line, "line");
			return Texts.lineIdx(line, toIdx);
		}
	}

}
