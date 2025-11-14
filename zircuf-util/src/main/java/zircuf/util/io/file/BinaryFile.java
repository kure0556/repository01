package zircuf.util.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BinaryFile implements BaseFile {

	public static final BinaryFile of(String pathStr) {
		return new BinaryFile(Path.of(pathStr));
	}

	public static final BinaryFile of(Path path) {
		return new BinaryFile(path);
	}

	public static final BinaryFile of(File file) {
		return new BinaryFile(file.toPath());
	}

	public static final BinaryFile ofTemp(String prefix, String suffix) {
		try {
			return new BinaryFile(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Getter
	protected final Path path;

	public byte[] read() {
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Path write(byte[] bytes) {
		try {
			return Files.write(path, bytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
