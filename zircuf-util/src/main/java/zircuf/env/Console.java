package zircuf.env;

import java.util.Scanner;

public class Console {

	private static Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {
		String input = read("入力>");
		System.out.println("%sと入力されました".formatted(input));
	}

	public static String read() {
		return read(">");
	}

	public static String read(String message) {
		System.out.print(message);
		return SCANNER.nextLine();
	}
}