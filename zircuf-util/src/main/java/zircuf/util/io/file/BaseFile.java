package zircuf.util.io.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;

import zircuf.util.io.core.path.PathDeletable;
import zircuf.util.io.core.path.PathExistable;

/**
 * ファイルシステムのファイルを操作するインターフェース
 */
public interface BaseFile extends PathDeletable, PathExistable {

	public Path getPath();

	default public LocalDateTime getLastModified() {
		try {
			FileTime lastModifiedTime = Files.getLastModifiedTime(getPath());
			return LocalDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	default public <T> T work(Function<InputStream, T> func) {
		try (InputStream is = Files.newInputStream(getPath())) {
			return func.apply(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
