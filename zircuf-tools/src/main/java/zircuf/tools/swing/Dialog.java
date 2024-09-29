package zircuf.tools.swing;

import java.util.Optional;

import javax.swing.JOptionPane;

public class Dialog {

	public static void message(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public static Optional<String> input(String message) {
		return Optional.ofNullable(JOptionPane.showInputDialog(message));
	}

	public static boolean confirm(String message) {
		int result = JOptionPane.showConfirmDialog(
				null,
				message,
				"確認",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		return result == JOptionPane.YES_OPTION;
	}

}
