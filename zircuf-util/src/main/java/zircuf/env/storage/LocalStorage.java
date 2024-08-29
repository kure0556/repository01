package zircuf.env.storage;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zircuf.env.storage.StorageCore.StorageItem;
import zircuf.util.io.core.path.PathDeletable;
import zircuf.util.io.core.path.PathExistable;
import zircuf.util.io.core.path.PathReader;
import zircuf.util.io.core.path.PathWriter;

public class LocalStorage {

	public LocalStorageItem of(String objectKey) {
		return new LocalStorageItem(objectKey);
	}

	@RequiredArgsConstructor
	public static class LocalStorageItem
			implements StorageItem, PathReader, PathWriter, PathDeletable, PathExistable {

		@Getter
		private final String objectKey;

		@Override
		public Path getPath() {
			return Path.of(objectKey);
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
