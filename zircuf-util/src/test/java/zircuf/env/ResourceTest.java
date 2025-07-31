package zircuf.env;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import zircuf.tools.swing.Dialog;
import zircuf.util.data.table.TableConverter.Converter;
import zircuf.util.text.Texts;

class ResourceTest {

	@Test
	void test() {
		// テキスト形式での取得
		String text = Resource.of("code_table.tsv").toText();
		System.out.println(text);

		// 複数行テキスト形式での取得
		List<String> lines = Resource.of("code_table.tsv").toLines();
		System.out.println(lines);

		// テーブルデータでの取得
		List<String[]> table = Resource.of("code_table.tsv").asTsv().getTable();
		System.out.println(Texts.summry(table));

		// タイトル行を除外したテーブルデータの取得
		List<String[]> tableData = Resource.of("code_table.tsv").asTsv().titled().getTable();
		System.out.println(Texts.summry(tableData));

		// 0列目から1列目への変換
		Converter nameConverter = Resource.of("code_table.tsv").asTsv().titled().converter();
		String name1 = nameConverter.find("01").get()[1];
		assertEquals(name1, "a");
		String name2 = nameConverter.find("02").get()[1];
		assertEquals(name2, "b");

		// 1列目から2列目への変換
		Converter codeConverter = Resource.of("code_table.tsv").asTsv().titled().converter(1);
		String code1 = codeConverter.find("a").get()[0];
		assertEquals(code1, "01");
		String code2 = codeConverter.find("b").get()[0];
		assertEquals(code2, "02");

		// 指定されたリソースのパスを確認する（ログ表示）
		Resource.of("code_table.tsv").peekPath(System.out::println);

		// 指定されたリソースのパスを確認する（ダイアログ表示）
		Resource.of("code_table.tsv").peekPath(Dialog::message);
	}

}
