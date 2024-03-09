package zircuf.util.io.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import zircuf.util.general.To;
import zircuf.util.io.common.OCharset;

/**
 * Pathが与えられた場合に様々なデータに変換するインターフェース
 */
public interface PathWriter extends Writer, OCharset {

	public Path getPath();

	default public Path write(CharSequence text) {
		try {
			Files.createDirectories(getPath().getParent());
			return Files.writeString(getPath(), text, oCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

	default public Path writeAll(Iterable<? extends CharSequence> lines) {
		try {
			Files.createDirectories(getPath().getParent());
			return Files.write(getPath(), lines, oCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

}
