package zircuf.util.data.table;

import java.util.List;
import java.util.function.Function;

import zircuf.util.text.function.Join;
import zircuf.util.text.function.Text;

public interface Table {

	public List<String[]> getTable();

	public static <T> T accept(List<String[]> data, Function<List<String[]>, T> initter) { 
		return initter.apply(data);
	}

	public default String toSummry() {
		return Text.summry(getTable(), Join::pipe);
	}
}
