package zircuf.env.storage;

import java.nio.file.FileAlreadyExistsException;
import java.util.function.Consumer;

import zircuf.util.io.core.Deletable;
import zircuf.util.io.core.Existable;
import zircuf.util.io.core.Reader;
import zircuf.util.io.core.Writer;

public interface StorageCore {

	public StorageItem of(String objectKey);

	public static interface StorageItem extends Reader, Writer, Deletable, Existable {

		public String getObjectKey();

		@SuppressWarnings("unchecked")
		public default <T extends StorageItem> T peekPath(Consumer<String> action) {
			action.accept(getObjectKey());
			return (T) this;
		}

		/**
		 * すでにファイルが存在する場合に処理
		 */
		@SuppressWarnings("unchecked")
		public default <T extends StorageItem> void ifExist(Consumer<T> action) {
			if (isExists()) {
				action.accept((T) this);
			}
		}

		/**
		 * すでにファイルが存在するかに応じて処理を切り替え
		 */
		@SuppressWarnings("unchecked")
		public default <T extends StorageItem> void ifExistOrElse(Consumer<T> action, Runnable elseAction) {
			if (isExists()) {
				action.accept((T) this);
			} else {
				elseAction.run();
			}
		}

		/**
		 * すでにファイルが存在する場合に例外スローするオプション
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public default <T extends StorageItem> T blockOverWrite() throws FileAlreadyExistsException {
			if (this.isExists()) {
				throw new FileAlreadyExistsException(getObjectKey());
			}
			return (T) this;
		}

	}

}
