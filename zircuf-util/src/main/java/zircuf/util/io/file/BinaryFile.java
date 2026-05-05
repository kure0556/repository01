package zircuf.util.io.file;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BinaryFile implements BaseFile {

	/**
	 * バイナリファイルの指定（ファイルパス文字列指定）
	 * @param pathStr ファイルパス文字列
	 * @return BinaryFileインスタンス
	 */
	public static final BinaryFile of(String pathStr) {
		return new BinaryFile(Path.of(pathStr));
	}

	/**
	 * バイナリファイルの指定（ファイルパス指定）
	 * @param path ファイルパス
	 * @return BinaryFileインスタンス
	 */
	public static final BinaryFile of(Path path) {
		return new BinaryFile(path);
	}

	/**
	 * バイナリファイルの指定（File指定）
	 * @param file Fileインスタンス
	 * @return BinaryFileインスタンス
	 */
	public static final BinaryFile of(File file) {
		return new BinaryFile(file.toPath());
	}

	/**
	 * 一時バイナリファイルの指定
	 * @param prefix 一時ファイル名のプレフィックス（prefixとsuffixの間に衝突回避のためのユニークIDが入る）
	 * @param suffix 一時ファイル名のサフィックス（拡張子等で使用）
	 * @return BinaryFileインスタンス
	 */
	public static final BinaryFile ofTemp(String prefix, String suffix) {
		try {
			return new BinaryFile(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Getter
	protected final Path path;

	/**
	 * ファイル読み込み
	 * @return バイト配列
	 */
	public byte[] read() {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * ファイル書き込み
	 * @param bytes バイト配列
	 * @return ファイルパス
	 */
	public Path write(byte[] bytes) {
		try {
			return Files.write(path, bytes);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
