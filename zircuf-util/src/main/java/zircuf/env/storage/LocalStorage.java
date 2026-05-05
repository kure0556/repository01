package zircuf.env.storage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zircuf.util.io.common.Encoding;
import zircuf.util.io.core.path.PathDeletable;
import zircuf.util.io.core.path.PathExistable;
import zircuf.util.io.core.path.PathReader;
import zircuf.util.io.core.path.PathWriter;

@RequiredArgsConstructor
/**
 * ローカルファイルシステム上のストレージアクセスを提供する実装。
 * <p>
 * 指定された root を基準ディレクトリとして、ファイルの読み書きを行う
 * {@link LocalStorageItem} を生成する。アプリケーションが動作している
 * マシンのローカルストレージを扱うための実装であり、Storage.local() から
 * 取得されるデフォルトのストレージとして利用される。
 * <p>
 */
public class LocalStorage implements StorageCore<Path, Path> {

	private final String root;

	public LocalStorage() {
		super();
		this.root = "";
	}
	/**
	 * 基準ディレクトリ（root）からの相対パスとして objectKey を解決し、
	 * 対応するローカルファイルを表す {@link LocalStorageItem} を生成する。
	 *
	 * @param objectKey 基準ディレクトリからの相対パス
	 * @return 対応する LocalStorageItem（読み書き可能）
	 */
	public LocalStorageItem of(String objectKey) {
		return new LocalStorageItem(Path.of(this.root, objectKey));
	}
	/**
	 * 一時ファイルを作成し、そのファイルを表す {@link LocalStorageItem} を返す。
	 * <p>
	 * {@link Files#createTempFile(String, String)} に基づいて OS のテンポラリ領域に
	 * ファイルを作成するため、ストレージの root 設定には依存しない。
	 *
	 * @param prefix 一時ファイル名のプレフィックス（prefixとsuffixの間に衝突回避のためのユニークIDが入る）
	 * @param suffix 一時ファイル名のサフィックス（拡張子等で使用）
	 * @return 作成された一時ファイルを表す LocalStorageItem
	 */
	public LocalStorageItem ofTemp(String prefix, String suffix) {
		try {
			return new LocalStorageItem(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@RequiredArgsConstructor
	public static class LocalStorageItem
			implements StorageItem<Path, Path>, PathReader, PathWriter, PathDeletable, PathExistable {

		@Getter
		private final Path path;

		@Getter
		protected Optional<Charset> charset = Optional.empty();

		/**
		 * エンコードの指定
		 * @param encoding エンコード
		 * @return 自インスタンス
		 */
		public LocalStorageItem withEncoding(Encoding encoding) {
			this.charset = Optional.of(encoding.getCharset());
			return this;
		}

	}

}
