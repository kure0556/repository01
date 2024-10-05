package zircuf.util.io.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Consumer;
import java.util.function.Function;

import zircuf.util.general.To;
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
			throw To.rException(e);
		}
	}

	@SuppressWarnings("unchecked")
	default public <T extends BaseFile> void ifExist(Consumer<? super T> action) {
		if (isExists()) {
			action.accept((T) this);
		}
	}

	@SuppressWarnings("unchecked")
	public default <T extends BaseFile> void ifExistOrElse(Consumer<? super T> action, Runnable emptyAction) {
		if (isExists()) {
			action.accept((T) this);
		} else {
			emptyAction.run();
		}
	}

	default public <T> T work(Function<InputStream, T> func) {
		try (InputStream is = Files.newInputStream(getPath())) {
			return func.apply(is);
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

}
