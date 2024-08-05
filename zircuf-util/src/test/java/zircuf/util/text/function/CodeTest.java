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
		extracted(Code.lowerSnakeToLowerCamel("my_name_is_Snake"), "myNameIsSnake");
		extracted(Code.lowerSnakeToUpperCamel("my_name_is_Snake"), "MyNameIsSnake");
	}

	@Test
	void test2() {
		extracted(Code.lowerSnakeToLowerCamel("foo"), "foo");
		extracted(Code.lowerSnakeToUpperCamel("foo"), "Foo");
	}

	@Test
	void test3() {
		extracted(Code.lowerSnakeToLowerCamel("x"), "x");
		extracted(Code.lowerSnakeToUpperCamel("x"), "X");
	}

	@Test
	void test4() {
		extracted(Code.lowerSnakeToLowerCamel(""), "");
		extracted(Code.lowerSnakeToUpperCamel(""), "");
	}

}
