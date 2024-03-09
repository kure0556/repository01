package zircuf.util.io.common;

import java.util.Optional;

public interface OLineSeparator {

	default public Optional<String> oLineSeparator() {
		return Optional.of(System.lineSeparator());
	}

}
