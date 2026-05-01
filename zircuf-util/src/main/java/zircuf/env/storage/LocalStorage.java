package zircuf.env.storage;

import java.io.IOException;
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
public class LocalStorage implements StorageCore<Path, Path> {

	private final String root;

	public LocalStorage() {
		super();
		this.root = "";
	}

	public LocalStorageItem of(String objectKey) {
		return new LocalStorageItem(Path.of(this.root, objectKey));
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
		 * @param encoding エンコード
		 * @return 自インスタンス
		 */
		public LocalStorageItem withEncoding(Encoding encoding) {
			this.charset = Optional.of(encoding.getCharset());
			return this;
		}

	}

}
