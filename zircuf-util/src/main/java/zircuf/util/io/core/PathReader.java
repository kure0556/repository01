package zircuf.util.io.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import zircuf.util.general.To;
import zircuf.util.io.common.OCharset;

/**
 * Pathが与えられた場合に様々なデータに変換するインターフェース
 */
public interface PathReader extends Reader, OCharset {

	public Path getPath();

	default public String toText() {
		try {
			return Files.readString(getPath(), oCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

	default public Stream<String> lines() {
		try {
			return Files.lines(getPath(), oCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

}
