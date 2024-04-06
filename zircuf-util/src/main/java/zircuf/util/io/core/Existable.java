package zircuf.util.io.core;

public interface Existable {

	default public boolean isExists() {
		throw new UnsupportedOperationException("未実装");
	}

}
