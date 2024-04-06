package zircuf.env.resource;

import zircuf.util.io.core.Reader;

public interface ResourceCore {

	public ResourceItem of(String pathStr);

	public static interface ResourceItem extends Reader {
	}

}
