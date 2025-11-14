package zircuf.util.general;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
			throw new RuntimeException(e);
		}
	}

	// リスト

	public static final <E> List<E> list(E[] array) {
		return Arrays.asList(array);
	}

	// InputStream -> 汎用データ
//	@Deprecated
//	public static final String text(InputStream is) {
//		return Readers.text().toData(To.supplier(is));
//	}

	// Refrect関連

	public static final <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
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
