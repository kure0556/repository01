package zircuf.util.text.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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

	@Test
	void test6() {
		extracted(Code.convertSingletonName("entries"), "entry");
		extracted(Code.convertSingletonName("boxes"), "box");
		extracted(Code.convertSingletonName("items"), "item");
		extracted(Code.convertSingletonName("fooList"), "foo");
		extracted(Code.convertSingletonName("fooMap"), "foo");
		extracted(Code.convertSingletonName("fooSet"), "foo");
		extracted(Code.convertSingletonName("ies"), "i");
		extracted(Code.convertSingletonName("es"), "e");
		extracted(Code.convertSingletonName("s"), "s");
		extracted(Code.convertSingletonName("foo"), "foo");
	}

}
