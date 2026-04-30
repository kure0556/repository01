package zircuf.util.io.core;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import zircuf.util.data.table.TableConverter;
import zircuf.util.data.table.TableListMapper;
import zircuf.util.text.function.Split;

/**
 * テキストを様々な形式で取り出すインターフェース
 */
public interface Reader {

	public String toText();

	public Stream<String> lines();

	default public List<String> toLines() {
		return lines().toList();
	}

	default public TableStream asTsv() {
		return new TableStream(lines(), Split::tsv);
	}

	default public TableStream asCsv() {
		return new TableStream(lines(), Split::csv);
	}

	default public TableStream asCsvDQ() {
		return new TableStream(lines(), Split::csvDQ);
	}

	public static class TableStream implements TableConverter, TableListMapper {

		private Stream<String[]> tableStream;

		public TableStream(Stream<String> lines, Function<String, String[]> func) {
			tableStream = lines.map(func);
		}

		public Stream<String[]> stream() {
			return tableStream;
		}

		@Override
		public List<String[]> getTable() {
			return tableStream.toList();
		}

		public TableStream titled() {
			tableStream = tableStream.skip(1);
			return this;
		}

		public TableStream titled(int skipRows) {
			tableStream = tableStream.skip(skipRows);
			return this;
		}

	}
}
