package zircuf.util.data.table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import zircuf.util.performance.Performance;
import zircuf.util.text.function.Split;
import zircuf.util.text.function.Text;

@RequiredArgsConstructor(staticName = "of")
public class Table implements TableCore, TableConverter, TableMapper {

	public static void main(String[] args) {
		List<String[]> list = IntStream.range(1, 10000).mapToObj(i -> {
			return i + "," + i;
		}).map(Split::csv).toList();
		System.out.println(Text.summry(list));

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
		System.out.println(Text.summry(data));
		@SuppressWarnings("deprecation")
		String[] convert = Table.of(data).get("123", 0);
		System.out.println(Arrays.toString(convert));

		//		new Performance() {
		//			@Override
		//			protected void proc() {
		//				CodeTable.of(list).get("5000", 0);
		//			}
		//		}.keisoku();

		//		Converter converter = CodeTable.of(list).complie();
		//		String string = converter.get("5000", 1);
		//		System.out.println(string);

		Converter converter = Table.of(list).converter();
		Performance.of(s -> {
			converter.get("5000", 1);
		});

	}

	@NonNull
	@Getter
	private final List<String[]> table;

}
