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

	default public AsTable asTsv() {
		return new AsTable(lines(), Split::tsv);
	}

	default public AsTable asCsv() {
		return new AsTable(lines(), Split::csv);
	}

	default public AsTable asCsvDQ() {
		return new AsTable(lines(), Split::csvDQ);
	}

	public static class AsTable implements TableConverter, TableListMapper {

		public Stream<String[]> tableLines;

		public AsTable(Stream<String> lines, Function<String, String[]> func) {
			tableLines = lines.map(func);
		}

		public Stream<String[]> stream() {
			return tableLines;
		}

		@Override
		public List<String[]> getTable() {
			return tableLines.toList();
		}

		public AsTable titled() {
			tableLines = tableLines.skip(1);
			return this;
		}

		public AsTable titled(int skipRows) {
			tableLines = tableLines.skip(skipRows);
			return this;
		}

	}
}
