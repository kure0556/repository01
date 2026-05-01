package zircuf.util.io.core.path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

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
	    Objects.requireNonNull(text, "text is null");

		try {
			// 出力先ディレクトリの作成
			Path path = getPath();
	        if (path.getParent() != null) {
	            Files.createDirectories(path.getParent());
	        }
			return Files.writeString(path, text, getCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * ファイルへの書き込み（複数行データ）
	 */
	default public Path writeAll(Iterable<? extends CharSequence> lines) {
	    Objects.requireNonNull(lines, "lines is null");

		try {
			// 出力先ディレクトリの作成
			Path path = getPath();
	        if (path.getParent() != null) {
	            Files.createDirectories(path.getParent());
	        }
			return Files.write(path, lines, getCharset().orElse(Charset.defaultCharset()));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * ファイルへの書き込み（複数行データ）（強制CRLF）
	 */
	default public Path writeAllCrlf(Iterable<? extends CharSequence> lines) {
	    Objects.requireNonNull(lines, "lines is null");

	    try {
			// 出力先ディレクトリの作成
	    	Path path = getPath();
	        if (path.getParent() != null) {
	            Files.createDirectories(path.getParent());
	        }

	        try (BufferedWriter writer = Files.newBufferedWriter(
	                path,
	                getCharset().orElse(Charset.defaultCharset()))) {

	            for (CharSequence line : lines) {
	                if (line == null) {
	                    throw new IllegalArgumentException("line is null");
	                }
	                writer.append(line).append("\r\n"); // 強制CRLF
	            }
	        }

	        return path;

	    } catch (IOException e) {
	        throw new UncheckedIOException(e);
	    }
	}
}
