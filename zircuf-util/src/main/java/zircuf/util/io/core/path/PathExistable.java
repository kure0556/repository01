package zircuf.util.io.core.path;

import java.nio.file.Files;
import java.nio.file.Path;

import zircuf.util.io.core.Existable;

/**
 * Pathが与えられた場合の存在判定インターフェース
 */
public interface PathExistable extends Existable {

	public Path getPath();

	@Override
	default public boolean isExists() {
		return Files.exists(getPath());
	}

}
