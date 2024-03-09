package zircuf.env;

import java.net.URISyntaxException;
import java.nio.file.Path;

import lombok.Getter;
import zircuf.util.io.core.PathReader;

public interface ResourceCore {

	public static ResourceCore local() {
		return new ResourceCore() {
		};
	}

	default public ResourceItem of(String pathStr) {
		return new ResourceItem(pathStr);
	}

	public static final class ResourceItem implements PathReader {

		@Getter
		private final Path path;

		private ResourceItem(String pathStr) {
			path = resourcePath(pathStr);
		}

		private final Path resourcePath(String pathStr) {
			try {
				return Path.of(ResourceCore.class.getClassLoader().getResource(pathStr).toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException("クラスパスで指定されたファイルが見つからない pathStr=" + pathStr, e);
			}
		}

	}
}
