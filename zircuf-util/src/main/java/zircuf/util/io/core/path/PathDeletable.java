package zircuf.util.io.core.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import zircuf.util.io.core.Deletable;

/**
 * Pathが与えられた場合の削除処理インターフェース
 */
public interface PathDeletable extends Deletable {

	public Path getPath();

	@Override
	default public void delete() {
		try {
			Files.delete(getPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	default public boolean deleteIfExists() {
		try {
			return Files.deleteIfExists(getPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
