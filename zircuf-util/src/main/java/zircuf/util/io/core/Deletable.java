package zircuf.util.io.core;

/**
 * 削除可能
 */
public interface Deletable {

	default public void delete() {
		throw new UnsupportedOperationException("サポート外操作：delete()");
	}

	default public boolean deleteIfExists() {
		throw new UnsupportedOperationException("サポート外操作：deleteIfExists()");
	}

}
