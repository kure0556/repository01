package zircuf.util.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import zircuf.util.io.common.Encode;
import zircuf.util.io.core.path.PathReader;
import zircuf.util.io.core.path.PathWriter;

@RequiredArgsConstructor
public class TextFile implements BaseFile, PathReader, PathWriter {

	public static final TextFile of(String pathStr) {
		return new TextFile(Path.of(pathStr));
	}

	public static final TextFile of(Path path) {
		return new TextFile(path);
	}

	public static final TextFile of(File file) {
		return new TextFile(file.toPath());
	}

	public static final TextFile ofTemp(String prefix, String suffix) {
		try {
			return new TextFile(Files.createTempFile(prefix, suffix));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected final Path path;
	protected Optional<Charset> oCharSet = Optional.empty();

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public Optional<Charset> oCharset() {
		return oCharSet;
	}

	public TextFile encode(Encode encode) {
		this.oCharSet = Optional.of(encode.getCharset());
		return this;
	}

}
