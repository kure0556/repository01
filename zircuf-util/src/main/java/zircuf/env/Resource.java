package zircuf.env;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.function.Consumer;

import lombok.Getter;
import zircuf.util.io.core.path.PathReader;

public class Resource {

	public static ResourceItem of(String pathStr) {
		return new ResourceItem(pathStr);
	}

	public static class ResourceItem implements PathReader {

		@Getter
		private final Path path;

		private ResourceItem(String pathStr) {
			path = resourcePath(pathStr);
		}

		public final ResourceItem peekPath(Consumer<String> action) {
			action.accept(path.toString());
			return this;
		}

		private final Path resourcePath(String pathStr) {
			try {
				return Path.of(Resource.class.getClassLoader().getResource(pathStr).toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException("クラスパスで指定されたファイルが見つからない pathStr=" + pathStr, e);
			}
		}

	}
}
