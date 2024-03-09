package zircuf.util.data.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import lombok.NonNull;

public interface TableMapper {

	public List<String[]> getTable();

	default public Mapper mapper(int fromIdx, int toIdx) {
		return new Mapper(getTable(), fromIdx, toIdx);
	}

	public static final class Mapper {

		private HashMap<String, List<String>> compiledMap = new HashMap<>();

		private Mapper(@NonNull List<String[]> table, int fromIdx, int toIdx) {
			try {
				table.stream().forEach(line -> {
					String key = line[fromIdx];
					List<String> list = compiledMap.get(key);
					if (Objects.isNull(list)) {
						list = new ArrayList<>();
						compiledMap.put(key, list);
					}
					list.add(line[toIdx]);
				});
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new RuntimeException("配列長エラー fromIdx=" + fromIdx + " toIdx=" + toIdx, e);
			}
		}

		public final List<String> find(String input) {
			return zircuf.util.general.NonNull.of(get(input));
		}

		public final List<String> get(String input) {
			Objects.requireNonNull(input, "input");
			return compiledMap.get(input);
		}

	}

}
