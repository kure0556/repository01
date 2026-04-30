package zircuf.util.io.core.path;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import zircuf.util.io.common.CharsetOpt;
import zircuf.util.io.core.Writer;

/**
 * Pathが与えられた場合に様々なデータに変換するインターフェース
 */
public interface PathWriter extends Writer<Path>, CharsetOpt {

	public Path getPath();

	/**
	 * ファイルへの書き込み（テキストデータ）
	 */
	default public Path write(CharSequence text) {
		try {
			Path path = getPath();
			// 出力先ディレクトリの作成
			Files.createDirectories(path.getParent());
			return Files.writeString(path, text, getCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ファイルへの書き込み（複数行データ）
	 */
	default public Path writeAll(Iterable<? extends CharSequence> lines) {
		try {
			Path path = getPath();
			// 出力先ディレクトリの作成
			Files.createDirectories(path.getParent());
			return Files.write(path, lines, getCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
