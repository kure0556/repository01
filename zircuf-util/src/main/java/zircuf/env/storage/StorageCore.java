package zircuf.env.storage;

import java.nio.file.FileAlreadyExistsException;
import java.util.function.Consumer;

import zircuf.util.io.core.Deletable;
import zircuf.util.io.core.Existable;
import zircuf.util.io.core.Reader;
import zircuf.util.io.core.Writer;

/**
 * @param <P> パスの型
 * @param <R> 書き込み時の戻り値型
 */
public interface StorageCore<P, R> {

	public StorageItem<P, R> of(String objectKey);

	public static interface StorageItem<P, R> extends Reader, Writer<R>, Deletable, Existable {

		public P getPath();

		@SuppressWarnings("unchecked")
		public default <T extends StorageItem<P, R>> T peekPath(Consumer<P> action) {
			action.accept(getPath());
			return (T) this;
		}

		/**
		 * すでにファイルが存在する場合に処理
		 */
		@SuppressWarnings("unchecked")
		public default <T extends StorageItem<P, R>> void ifExist(Consumer<T> action) {
			if (isExists()) {
				action.accept((T) this);
			}
		}

		/**
		 * すでにファイルが存在するかに応じて処理を切り替え
		 */
		@SuppressWarnings("unchecked")
		public default <T extends StorageItem<P, R>> void ifExistOrElse(Consumer<T> action, Runnable elseAction) {
			if (isExists()) {
				action.accept((T) this);
			} else {
				elseAction.run();
			}
		}

		/**
		 * すでにファイルが存在する場合に例外スローするオプション
		 * @throws FileAlreadyExistsException
		 */
		@SuppressWarnings("unchecked")
		public default <T extends StorageItem<P, R>> T blockOverWrite() throws FileAlreadyExistsException {
			if (this.isExists()) {
				throw new FileAlreadyExistsException(getPath().toString());
			}
			return (T) this;
		}

	}

}
