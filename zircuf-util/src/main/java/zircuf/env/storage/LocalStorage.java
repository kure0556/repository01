package zircuf.env.storage;

import java.nio.file.Path;

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

	}

}
