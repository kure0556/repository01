package zircuf.util.io.core;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import zircuf.util.text.function.Join;

public interface Writer {

	public Path write(CharSequence text);

	default public Path writeAll(Iterable<? extends CharSequence> lines) {
		StringBuilder sb = new StringBuilder();
		for (CharSequence charSequence : lines) {
			// サブクラスPathWriterは高速書き込み(chanel)をサポートしているが
			// そちらの改行のデフォルトがSystem.lineSeparator()であるため
			// 互換性のため改行コード固定にする
			sb.append(charSequence).append(System.lineSeparator());
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
