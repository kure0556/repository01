package zircuf.util.io.file;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TextFileTest {

	@Test
	void test() {
		TextFile temp = TextFile.ofTemp("hoge", "fuga");
		if (temp.isExists()) {
			System.out.println("fairuaru");
		} else {
			System.out.println("fairuneeeeeeeee");
		}

	}

	@ParameterizedTest
	@CsvSource({
			"src/main/resources/code_table.tsv, true",
			"src/main/resources/code_table.csv, false"
	})
	void test2(String path, boolean exists) {
		TextFile temp = TextFile.of(path);
		assertEquals(temp.isExists(), exists);
	}

}
