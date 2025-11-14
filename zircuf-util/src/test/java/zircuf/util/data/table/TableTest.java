package zircuf.util.data.table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import zircuf.util.data.table.TableConverter.Converter;
import zircuf.util.performance.Performance;
import zircuf.util.text.Texts;
import zircuf.util.text.function.Split;

class TableTest {

	@Test
	void test1() {
		List<String[]> list = IntStream.range(1, 10000).mapToObj(i -> {
			return i + "," + i;
		}).map(Split::csv).toList();
		System.out.println(Texts.summry(list));

		Converter converter = Table.of(list).converter();
		Performance.of(() -> {
			converter.get("5000", 1);
		});
	}

	@Test
	void test2() {
		var data = List.of(
				List.of("123", "ひふみ").toArray(String[]::new),
				List.of("456", "しごろ").toArray(String[]::new),
				List.of("3", "しごろ").toArray(String[]::new),
				List.of("4", "しごろ").toArray(String[]::new),
				List.of("5", "しごろ").toArray(String[]::new),
				List.of("6", "しごろ").toArray(String[]::new),
				List.of("7", "しごろ").toArray(String[]::new),
				List.of("8", "しごろ").toArray(String[]::new),
				List.of("9", "しごろ").toArray(String[]::new),
				List.of("10", "しごろ").toArray(String[]::new));
		System.out.println(Texts.summry(data));

		String[] convert = Table.of(data).get("123", 0);
		System.out.println(Arrays.toString(convert));

	}

	@ParameterizedTest
	@CsvSource({"0,2", "2,0"})
	void testError(int from, int to) {
		try {
			var data = List.of(
					List.of("123", "ひふみ").toArray(String[]::new),
					List.of("456", "しごろ").toArray(String[]::new),
					List.of("3", "しごろ").toArray(String[]::new),
					List.of("4", "しごろ").toArray(String[]::new),
					List.of("5", "しごろ").toArray(String[]::new),
					List.of("6", "しごろ").toArray(String[]::new),
					List.of("7", "しごろ").toArray(String[]::new),
					List.of("8", "しごろ").toArray(String[]::new),
					List.of("9", "しごろ").toArray(String[]::new),
					List.of("10", "しごろ").toArray(String[]::new));
			String result = Table.of(data).converter(from).get("123", to);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
