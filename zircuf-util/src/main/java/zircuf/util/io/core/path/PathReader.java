package zircuf.util.io.core.path;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import zircuf.util.io.common.CharsetOpt;
import zircuf.util.io.core.Reader;

/**
 * Pathが与えられた場合に様々なデータに変換するインターフェース
 */
public interface PathReader extends Reader, CharsetOpt {

	public Path getPath();

	/**
	 * String形式での取得
	 */
	default public String toText() {
		try {
			return Files.readString(getPath(), getCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * StringのStream形式での取得
	 */
	default public Stream<String> lines() {
		try {
			return Files.lines(getPath(), getCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
