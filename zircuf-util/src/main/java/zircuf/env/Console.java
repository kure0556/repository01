package zircuf.env;

import java.util.Scanner;

public class Console {
	public static void main(String[] args) {
		String input = read("入力>");
		System.out.println("%sと入力されました".formatted(input));
	}

	public static String read() {
		return read(">");
	}

	public static String read(String message) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print(message);
			return scanner.next(); //文字列の入力の受け取り
		}
	}
}
