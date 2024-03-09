package zircuf.env;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResourceCoreTest {

	@Test
	void test() {
		ResourceCore.local().of("code_table.tsv").asTsv().converter(1);
		fail("まだ実装されていません");
	}

}
