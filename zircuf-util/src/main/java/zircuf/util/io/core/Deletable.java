package zircuf.util.io.core;

public interface Deletable {

	default public void delete() {
		throw new UnsupportedOperationException("未実装");
	}

	default public boolean deleteIfExists() {
		throw new UnsupportedOperationException("未実装");
	}

}
