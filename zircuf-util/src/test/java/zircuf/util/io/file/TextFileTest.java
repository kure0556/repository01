package zircuf.util.io.file;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import zircuf.env.Storage;
import zircuf.util.io.common.Encode;

class TextFileTest {

	@Test
	void test() {
		TextFile temp = TextFile.ofTemp("hoge", ".txt");
		if (temp.isExists()) {
			System.out.println("ファイルあり" + temp.getPath());
		} else {
			System.out.println("ファイルなし" + temp.getPath());
		}

	}

	@Test
	void test2() {
		TextFile temp = TextFile.ofTemp("hoge", ".txt").withEncode(Encode.SJIS);
		temp.write("""
				エンコード確認①②③
				エンコード確認
				""");
		if (temp.isExists()) {
			System.out.println("ファイルあり" + temp.getPath());
		} else {
			System.out.println("ファイルなし" + temp.getPath());
		}

		String text = temp.toText();
		System.out.println(text);
	}

	@Test
	void test3() {
		var temp = Storage.local().ofTemp("hoge", ".txt").withEncode(Encode.SJIS);
		temp.write("""
				エンコード確認①②③
				エンコード確認
				""");
		if (temp.isExists()) {
			System.out.println("ファイルあり" + temp.getPath());
		} else {
			System.out.println("ファイルなし" + temp.getPath());
		}

		String text = temp.toText();
		System.out.println(text);
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
