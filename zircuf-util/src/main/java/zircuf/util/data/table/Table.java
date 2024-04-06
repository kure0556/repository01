package zircuf.util.data.table;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import zircuf.util.text.function.Split;

@RequiredArgsConstructor(staticName = "of")
public class Table implements TableBase, TableConverter, TableMapper {

	public final Table ofTsv(String tsvText) {
		return Table.of(tsvText.lines().map(Split::tsv).toList());
	}

	public final Table ofCsv(String csvText) {
		return Table.of(csvText.lines().map(Split::csv).toList());
	}

	@NonNull
	@Getter
	private final List<String[]> table;

}
