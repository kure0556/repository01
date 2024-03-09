package zircuf.util.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zircuf.util.general.To;

@RequiredArgsConstructor
public class ByteFile implements BaseFile {

	public static final ByteFile of(String pathStr) {
		return new ByteFile(Path.of(pathStr));
	}

	public static final ByteFile of(Path path) {
		return new ByteFile(path);
	}

	public static final ByteFile of(File file) {
		return new ByteFile(file.toPath());
	}

	public static final ByteFile ofTemp(String prefix, String suffix) {
		try {
			return new ByteFile(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

	@Getter
	protected final Path path;

	public byte[] read() {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

	public Path write(byte[] bytes) {
		try {
			return Files.write(path, bytes);
		} catch (IOException e) {
			throw To.rException(e);
		}
	}

}
