package zircuf.env;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import zircuf.tools.swing.Dialog;
import zircuf.util.data.table.TableConverter.Converter;
import zircuf.util.text.Texts;

class ResourceCoreTest {

	@Test
	void test() {
		Converter converter = Resource.of("code_table.tsv").peekPath(Dialog::message).asTsv().titled().converter(1);
		String code = converter.find("a").get()[0];
		assertEquals(code, "01");

		Converter converter2 = Resource.of("code_table.tsv").asTsv().titled().converter();
		String name = converter2.find("02").get()[1];
		assertEquals(name, "b");

		System.out.println(Texts.summry(Resource.local().of("code_table.tsv").asTsv().titled().getTable()));
	}

}
