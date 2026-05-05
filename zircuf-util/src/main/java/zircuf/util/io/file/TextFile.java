package zircuf.util.io.file;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zircuf.util.io.common.Encoding;
import zircuf.util.io.core.path.PathReader;
import zircuf.util.io.core.path.PathWriter;

@RequiredArgsConstructor
public class TextFile implements BaseFile, PathReader, PathWriter {

	/**
	 * テキストファイルの指定（ファイルパス文字列指定）
	 * @param pathStr ファイルパス文字列
	 * @return TextFileインスタンス
	 */
	public static final TextFile of(String pathStr) {
		return new TextFile(Path.of(pathStr));
	}

	/**
	 * テキストファイルの指定（ファイルパス指定）
	 * @param path ファイルパス
	 * @return TextFileインスタンス
	 */
	public static final TextFile of(Path path) {
		return new TextFile(path);
	}

	/**
	 * テキストファイルの指定（File指定）
	 * @param file Fileインスタンス
	 * @return TextFileインスタンス
	 */
	public static final TextFile of(File file) {
		return new TextFile(file.toPath());
	}

	/**
	 * 一時テキストファイルの指定
	 * @param prefix 一時ファイル名のプレフィックス（prefixとsuffixの間に衝突回避のためのユニークIDが入る）
	 * @param suffix 一時ファイル名のサフィックス（拡張子等で使用）
	 * @return TextFileインスタンス
	 */
	public static final TextFile ofTemp(String prefix, String suffix) {
		try {
			return new TextFile(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Getter
	protected final Path path;

	@Getter
	protected Optional<Charset> charset = Optional.empty();

	/**
	 * エンコードの指定
	 * @param encoding エンコード
	 * @return 自インスタンス
	 */
	public TextFile withEncoding(Encoding encoding) {
		this.charset = Optional.of(encoding.getCharset());
		return this;
	}

}
