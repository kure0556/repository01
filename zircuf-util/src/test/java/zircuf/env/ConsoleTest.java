package zircuf.env;

import org.junit.jupiter.api.Test;

class ConsoleTest {

    @Test
    void test() {
        while (true) {
			String result = Console.read(">");
			if ("exit".equals(result)) {
				System.out.println("「%s」が入力されたため、終了します。".formatted(result));
				break;
			}
			System.out.println("「%s」が入力されました。".formatted(result));
		}
    }

}