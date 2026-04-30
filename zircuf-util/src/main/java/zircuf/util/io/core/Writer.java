package zircuf.util.io.core;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import zircuf.util.text.function.Join;

/**
 * テキストを様々な形式で書き込むインターフェース
 * @param <R> 戻り値の型
 */
public interface Writer<R> {

	public R write(CharSequence text);

	default public R writeAll(Iterable<? extends CharSequence> lines) {
		StringBuilder sb = new StringBuilder();
		for (CharSequence charSequence : lines) {
			// サブクラスPathWriterは高速書き込み(chanel)をサポートしているが
			// そちらの改行のデフォルトがSystem.lineSeparator()であるため
			// 互換性のため改行コード固定にする
			sb.append(charSequence).append(System.lineSeparator());
		}
		return write(sb);
	}

	default public R writeLines(List<String> lines) {
		return writeAll(lines);
	}

	default public R writeTsv(List<String[]> tsv) {
		return write(tsv, Join::tsv);
	}

	default public R writeCsv(List<String[]> csv) {
		return write(csv, Join::csv);
	}

	default public R writeCsvDQ(List<String[]> csv) {
		return write(csv, Join::csvDQ);
	}

	private R write(List<String[]> table, Function<? super String[], ? extends String> mapper) {
		Stream<String> stream = table.stream().map(mapper);
		Iterable<String> iterable = stream::iterator;
		return writeAll(iterable);
	}

}
