package zircuf.util.io.core;

/**
 * 存在判定可能
 */
public interface Existable {

	default public boolean isExists() {
		throw new UnsupportedOperationException("サポート外操作：isExists()");
	}

}
