package zircuf.util.data.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import lombok.NonNull;

public interface TableMapper {

	public List<String[]> getTable();

	default public LinkedHashMap<String, String> asMap() {
		return asMap(0, 1);
	}

	default public LinkedHashMap<String, String> asMap(int fromIdx, int toIdx) {
		LinkedHashMap<String, String> compiledMap = new LinkedHashMap<>();
		getTable().forEach(line -> {
			String key = line[fromIdx];
			compiledMap.put(key, line[toIdx]);
		});
		return compiledMap;
	}

	default public Mapper mapper() {
		return new Mapper(getTable(), 0, 1);
	}

	default public Mapper mapper(int fromIdx, int toIdx) {
		return new Mapper(getTable(), fromIdx, toIdx);
	}

	default public LinkedHashMap<String, List<String>> asListMap() {
		return new Mapper(getTable(), 0, 1).getListMap();
	}

	default public LinkedHashMap<String, List<String>> asListMap(int fromIdx, int toIdx) {
		return new Mapper(getTable(), fromIdx, toIdx).getListMap();
	}

	public static final class Mapper {

		private LinkedHashMap<String, List<String>> compiledMap = new LinkedHashMap<>();

		LinkedHashMap<String, List<String>> getListMap() {
			return compiledMap;
		}

		private Mapper(@NonNull List<String[]> table, int fromIdx, int toIdx) {
			try {
				table.forEach(line -> {
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

		public final List<String> get(String key) {
			Objects.requireNonNull(key, "key");
			return Objects.requireNonNullElse(compiledMap.get(key), Collections.emptyList());
		}

		public final Optional<String> findKey(String value) {
			Objects.requireNonNull(value, "value");
			for (Entry<String, List<String>> entry : compiledMap.entrySet()) {
				List<String> valueList = entry.getValue();
				for (int i = 0; i < valueList.size(); i++) {
					String value2 = valueList.get(i);
					if (value.equals(value2)) {
						// 最初に見つけたKeyを返却する
						return Optional.of(entry.getKey());
					}
				}
			}
			return Optional.empty();
		}

		public final List<String> findKeyAll(String value) {
			Objects.requireNonNull(value, "value");
			ArrayList<String> result = new ArrayList<>();
			for (Entry<String, List<String>> entry : compiledMap.entrySet()) {
				List<String> valueList = entry.getValue();
				for (int i = 0; i < valueList.size(); i++) {
					String value2 = valueList.get(i);
					if (value.equals(value2)) {
						// 見つけたKeyすべてを返却する
						result.add(entry.getKey());
					}
				}
			}
			return result;
		}
	}

}
