package zircuf.util.io.core;

import java.util.List;
import java.util.stream.Stream;

import zircuf.util.data.table.CodeTable;
import zircuf.util.text.function.Split;

public interface Reader {

	public String toText();

	public Stream<String> lines();

	default public List<String> toLines() {
		return lines().toList();
	}

	default public List<String[]> toTsv() {
		return lines().map(Split::tsv).toList();
	}

	default public List<String[]> toCsv() {
		return lines().map(Split::csv).toList();
	}

	default public Stream<String[]> tsv() {
		return lines().map(Split::tsv);
	}

	default public Stream<String[]> csv() {
		return lines().map(Split::csv);
	}

	default public CodeTable toCodeTable() {
		return CodeTable.of(toTsv());
	}

}
