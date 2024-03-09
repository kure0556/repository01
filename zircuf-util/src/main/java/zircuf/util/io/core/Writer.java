package zircuf.util.io.core;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import zircuf.util.io.common.OLineSeparator;
import zircuf.util.text.function.Join;

public interface Writer extends OLineSeparator {

	public Path write(CharSequence text);

	default public Path writeAll(Iterable<? extends CharSequence> lines) {
		// TODO:書き直す
		String ls = oLineSeparator().orElse(System.lineSeparator());
		StringBuilder sb = new StringBuilder();
		for (CharSequence charSequence : lines) {
			sb.append(charSequence).append(ls);
		}
		return write(sb);
	}

	default public Path writeLines(List<String> lines) {
		return writeAll(lines);
	}

	default public Path writeTsv(List<String[]> tsv) {
		Stream<String> stream = tsv.stream().map(Join::tsv);
		Iterable<String> iterable = stream::iterator;
		return writeAll(iterable);
	}

	default public Path writeCsv(List<String[]> tsv) {
		Stream<String> stream = tsv.stream().map(Join::csv);
		Iterable<String> iterable = stream::iterator;
		return writeAll(iterable);
	}

}
