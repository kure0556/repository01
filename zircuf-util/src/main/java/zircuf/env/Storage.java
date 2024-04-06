package zircuf.env;

import zircuf.env.storage.LocalStorage;

public class Storage {

	public static LocalStorage local() {
		return new LocalStorage();
	}

}
