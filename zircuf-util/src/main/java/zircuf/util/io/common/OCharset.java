package zircuf.util.io.common;

import java.nio.charset.Charset;
import java.util.Optional;

public interface OCharset {

	default public Optional<Charset> oCharset() {
		return Optional.empty();
	}

	default public Charset getCharset() {
		return oCharset().orElse(Charset.defaultCharset());
	}

}
