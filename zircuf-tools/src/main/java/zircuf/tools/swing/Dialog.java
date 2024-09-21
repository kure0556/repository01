package zircuf.tools.swing;

import javax.swing.JOptionPane;

public class Dialog {

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
