package zircuf.util.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zircuf.util.io.common.Encode;
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
	 * @param prefix ファイル名
	 * @param suffix 拡張子
	 * @return TextFileインスタンス
	 */
	public static final TextFile ofTemp(String prefix, String suffix) {
		try {
			return new TextFile(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Getter
	protected final Path path;

	/** エンコード */
	protected Optional<Charset> oCharSet = Optional.empty();

	/**
	 * エンコードの指定
	 * @param encode エンコード
	 * @return 自インスタンス
	 */
	public TextFile encode(Encode encode) {
		this.oCharSet = Optional.of(encode.getCharset());
		return this;
	}

}
