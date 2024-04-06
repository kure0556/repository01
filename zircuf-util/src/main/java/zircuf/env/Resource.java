package zircuf.env;

import zircuf.env.resource.LocalResource;
import zircuf.env.resource.LocalResource.LocalResourceItem;

public class Resource {

	public static LocalResource local() {
		return new LocalResource();
	}

	public static LocalResourceItem of(String pathStr) {
		return new LocalResource().of(pathStr);
	}

}
