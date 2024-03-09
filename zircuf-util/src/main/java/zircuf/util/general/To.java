package zircuf.util.general;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class To {

	// 各種配列・リスト -> Stream

	public static final <T> Stream<T> stream(Collection<T> collection) {
		return collection.stream();
	}

	public static final <T> Stream<T> stream(T[] array) {
		return Arrays.stream(array);
	}

	public static final DoubleStream stream(double[] array) {
		return Arrays.stream(array);
	}

	public static final IntStream stream(int[] array) {
		return Arrays.stream(array);
	}

	public static final LongStream stream(long[] array) {
		return Arrays.stream(array);
	}

	public static final <T> Stream<T> streamSingleton(T o) {
		return Stream.of(o);
	}

	// ファイルパス等 -> inputStream

	public static final InputStream inputStream(Path path) {
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw rException(e);
		}
	}

	// InputStream -> 汎用データ
//	@Deprecated
//	public static final String text(InputStream is) {
//		return Readers.text().toData(To.supplier(is));
//	}

	// Exception関連

	public static final RuntimeException rException(Exception e) {
		return new RuntimeException(e);
	}

	public static final <T> Supplier<T> supplier(T object) {
		return new Supplier<T>() {
			@Override
			public T get() {
				return object;
			}
		};
	}
}
