package zircuf.tools.swing;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import zircuf.tools.swing.parts.FileSelector;


public class Dialog {

	/**
	 * メッセージダイアログ
	 * @param message
	 */
	public static void message(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * 警告メッセージダイアログ
	 * @param message
	 */
	public static void messageWarn(String message) {
		JOptionPane.showMessageDialog(null, message, "警告", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * 入力ダイアログ
	 * @param message
	 * @return 入力値（取り消しの場合はOptional.empty()）
	 */
	public static Optional<String> input(String message) {
		return Optional.ofNullable(JOptionPane.showInputDialog(message));
	}

	/**
	 * 確認ダイアログ
	 * @param message
	 * @return true：はい, false：いいえ
	 */
	public static boolean confirm(String message) {
//		int result = JOptionPane.showConfirmDialog(null, message);
		int result = JOptionPane.showConfirmDialog(
				null,
				message,
				"確認",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		return result == JOptionPane.YES_OPTION;
	}

	public static Optional<File> selectFile() {
		return new FileSelector("").selectedFile();
	}

	public static Optional<File> selectFile(String currentDir) {
		return new FileSelector(currentDir).selectedFile();
	}

	public static List<File> selectFiles() {
		return new FileSelector("").selectedFiles();
	}

	public static List<File> selectFiles(String currentDir) {
		return new FileSelector(currentDir).selectedFiles();
	}

}
