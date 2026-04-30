package zircuf.env.storage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zircuf.util.io.common.Encode;
import zircuf.util.io.core.path.PathDeletable;
import zircuf.util.io.core.path.PathExistable;
import zircuf.util.io.core.path.PathReader;
import zircuf.util.io.core.path.PathWriter;

public class LocalStorage implements StorageCore<Path, Path> {

	public LocalStorageItem of(String objectKey) {
		return new LocalStorageItem(Path.of(objectKey));
	}

	public LocalStorageItem ofTemp(String prefix, String suffix) {
		try {
			return new LocalStorageItem(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw new RuntimeException(e);
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
		 * @param encode エンコード
		 * @return 自インスタンス
		 */
		public LocalStorageItem withEncode(Encode encode) {
			this.charset = Optional.of(encode.getCharset());
			return this;
		}

	}

}
