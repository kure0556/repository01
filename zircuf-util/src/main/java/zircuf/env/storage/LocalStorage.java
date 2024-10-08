package zircuf.env.storage;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zircuf.util.io.core.path.PathDeletable;
import zircuf.util.io.core.path.PathExistable;
import zircuf.util.io.core.path.PathReader;
import zircuf.util.io.core.path.PathWriter;

public class LocalStorage implements StorageCore {

	public LocalStorageItem of(String objectKey) {
		return new LocalStorageItem(objectKey);
	}

	@RequiredArgsConstructor
	public static class LocalStorageItem
			implements StorageItem, PathReader, PathWriter, PathDeletable, PathExistable {

		@Getter
		private final String objectKey;

		@Override
		public final Path getPath() {
			return Path.of(objectKey);
		}

		public final LocalStorageItem peekPath(Consumer<String> action) {
			action.accept(objectKey);
			return this;
		}

		/**
		 * すでにファイルが存在する場合に例外スローするオプション
		 * @throws Exception
		 */
		public LocalStorageItem blockOverWrite() throws FileAlreadyExistsException {
			if (this.isExists()) {
				throw new FileAlreadyExistsException(objectKey);
			}
			return this;
		}
	}

}
