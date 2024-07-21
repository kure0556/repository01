package zircuf.env;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import zircuf.util.data.table.TableConverter.Converter;
import zircuf.util.text.function.Text;

class ResourceCoreTest {

	@Test
	void test() {
		Converter converter = Resource.of("code_table.tsv").asTsv().titled().converter(1);
		String code = converter.find("a").get()[0];
		assertEquals(code, "01");
		System.out.println(Text.summry(Resource.local().of("code_table.tsv").asTsv().titled().getTable()));
	}

}
