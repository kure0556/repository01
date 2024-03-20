package zircuf.util.io.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import zircuf.util.general.To;

public interface PathDeletable extends Deletable {

	public Path getPath();

	@Override
	default public void delete() {
		try {
			Files.delete(getPath());
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

	@Override
	default public boolean deleteIfExists() {
		try {
			return Files.deleteIfExists(getPath());
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

}
