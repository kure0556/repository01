package zircuf.env.storage;

import zircuf.util.io.core.Deletable;
import zircuf.util.io.core.Existable;
import zircuf.util.io.core.Reader;
import zircuf.util.io.core.Writer;

public interface StorageCore {

	public StorageItem of(String objectKey);

	public static interface StorageItem extends Reader, Writer, Deletable, Existable {
	}

}
