package zircuf.env;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import zircuf.util.data.table.TableConverter.Converter;

class ResourceCoreTest {

	@Test
	void test() {
		Converter converter = ResourceCore.local().of("code_table.tsv").asTsv().titled().converter(1);
		fail("まだ実装されていません");
	}

}
