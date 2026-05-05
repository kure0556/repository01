package zircuf.util.text.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CodeTest {

	private void extracted(String input, String output) {
		System.out.println(input);
		assertEquals(input, output);
	}

	@Test
	void test() {
		extracted(Code.toLowerCamel("my_name_is_Snake"), "myNameIsSnake");
		extracted(Code.toUpperCamel("my_name_is_Snake"), "MyNameIsSnake");
	}

	@Test
	void test2() {
		extracted(Code.toLowerCamel("foo"), "foo");
		extracted(Code.toUpperCamel("foo"), "Foo");
	}

	@Test
	void test3() {
		extracted(Code.toLowerCamel("x"), "x");
		extracted(Code.toUpperCamel("x"), "X");
	}

	@Test
	void test4() {
		extracted(Code.toLowerCamel(""), "");
		extracted(Code.toUpperCamel(""), "");
	}

	@Test
	void test5() {
		extracted(Code.toLowerCamel("MY_NAME_IS__SNAKE"), "myNameIsSnake");
		extracted(Code.toUpperCamel("MY_NAME_IS__SNAKE"), "MyNameIsSnake");
	}

	@ParameterizedTest
	@CsvSource({
		"fooList, foo",
		"fooMap, foo",
		"fooSet, foo",
        "entries, entry",
        "boxes, boxe",
        "resources, resource",
        "items, item",
        "ies, ie",
        "es, e",
        "s, sItem",
        "foo, fooItem",
	})
	void test6(String src, String dst) {
		extracted(Code.convertSingletonName(src), dst);
	}

}
