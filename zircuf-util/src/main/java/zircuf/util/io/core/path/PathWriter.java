package zircuf.util.io.core.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import zircuf.util.io.common.OCharset;
import zircuf.util.io.core.Writer;

/**
 * Pathが与えられた場合に様々なデータに変換するインターフェース
 */
public interface PathWriter extends Writer, OCharset {

	public Path getPath();

	default public Path write(CharSequence text) {
		try {
			Path path = getPath();
			// 出力先ディレクトリの作成
			Files.createDirectories(path.getParent());
			return Files.writeString(path, text, getCharset());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	default public Path writeAll(Iterable<? extends CharSequence> lines) {
		try {
			Path path = getPath();
			// 出力先ディレクトリの作成
			Files.createDirectories(path.getParent());
			return Files.write(path, lines, getCharset());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
