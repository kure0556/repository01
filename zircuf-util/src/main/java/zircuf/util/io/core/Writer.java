package zircuf.util.io.core;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import zircuf.util.text.function.Join;

/**
 * テキストを様々な形式で書き込むインターフェース
 */
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
		return write(tsv, Join::tsv);
	}

	default public Path writeCsv(List<String[]> csv) {
		return write(csv, Join::csv);
	}

	default public Path writeCsvDQ(List<String[]> csv) {
		return write(csv, Join::csvDQ);
	}

	default public Path write(List<String[]> table, Function<? super String[], ? extends String> mapper) {
		Stream<String> stream = table.stream().map(mapper);
		Iterable<String> iterable = stream::iterator;
		return writeAll(iterable);
	}

}
