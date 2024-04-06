package zircuf.util.data.table;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import zircuf.util.text.function.Text;

interface TableBase {

	public List<String[]> getTable();

	default public String toSummry() {
		return Text.summry(getTable());
	}

	default public int size() {
		return getTable().size();
	}

	default public void forEach(Consumer<String[]> action) {
		getTable().forEach(action);
	}

	default public Stream<String[]> stream() {
		return getTable().stream();
	}

	default public Optional<String[]> find(String input, int fromIdx) {
		return Optional.ofNullable(get(input, fromIdx));
	}

	default public Optional<String> find(String input, int fromIdx, int toIdx) {
		return Optional.ofNullable(get(input, fromIdx, toIdx));
	}

	@Deprecated
	default public String[] get(String input, int fromIdx) {
		Objects.requireNonNull(input, "input");
		try {
			for (String[] line : getTable())
				if (input.equals(line[fromIdx]))
					return line;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException("配列長エラー fromIdx=" + fromIdx, e);
		}
		return null;
	}

	@Deprecated
	default public String get(String input, int fromIdx, int toIdx) {
		return Text.lineIdx(get(input, fromIdx), toIdx);
	}

}
