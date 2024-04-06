package zircuf.env;

import org.junit.jupiter.api.Test;

import zircuf.util.data.table.TableConverter.Converter;

class ResourceCoreTest {

	@Test
	void test() {
		Converter converter = Resource.of("code_table.tsv").asTsv().titled().converter(1);
		Resource.local().of("code_table.tsv").toString();
	}

}
