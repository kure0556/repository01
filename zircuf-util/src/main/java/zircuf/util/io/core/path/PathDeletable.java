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

	/**
	 * ファイルの削除（ファイルなしの場合に例外スロー）
	 */
	@Override
	default public void delete() {
		try {
			Files.delete(getPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ファイルの削除（ファイルなしの場合はfalse）
	 */
	@Override
	default public boolean deleteIfExists() {
		try {
			return Files.deleteIfExists(getPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
