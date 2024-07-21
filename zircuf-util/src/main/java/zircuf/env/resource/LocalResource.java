package zircuf.env.resource;

import java.net.URISyntaxException;
import java.nio.file.Path;

import lombok.Getter;
import zircuf.env.resource.ResourceCore.ResourceItem;
import zircuf.util.io.core.path.PathReader;

public class LocalResource {

	public LocalResourceItem of(String pathStr) {
		return new LocalResourceItem(pathStr);
	}

	public static class LocalResourceItem implements ResourceItem, PathReader {

		@Getter
		private final Path path;

		private LocalResourceItem(String pathStr) {
			path = resourcePath(pathStr);
		}

		private final Path resourcePath(String pathStr) {
			try {
				return Path.of(LocalResource.class.getClassLoader().getResource(pathStr).toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException("クラスパスで指定されたファイルが見つからない pathStr=" + pathStr, e);
			}
		}

	}
}
