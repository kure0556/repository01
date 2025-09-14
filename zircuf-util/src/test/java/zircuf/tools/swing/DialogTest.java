package zircuf.tools.swing;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import zircuf.tools.swing.util.SwingUtil;

class DialogTest {

	@ParameterizedTest
	@ValueSource(booleans = { false, true })	// デフォルトの見た目, Windowsの見た目
	void test(boolean windowsLikeFlag) {
		if (windowsLikeFlag) {
			// Windowsのウィンドウの見た目に切り替える
			SwingUtil.uiWindows();
		}

		// 情報メッセージ
		Dialog.message("メッセージ");
		// 警告メッセージ
		Dialog.messageWarn("メッセージ");

		// 入力ダイアログ
		Optional<String> input = Dialog.input("メッセージ");
		System.out.println("入力文字列　-> " + input);

		// 確認ダイアログ
		boolean confirm = Dialog.confirm("メッセージ");
		System.out.println("確認結果　-> " + confirm);

		// ファイル選択ダイアログ（単一）
		Optional<File> selectFile = Dialog.selectFile();
		System.out.println(selectFile);

		// ファイル選択ダイアログ（複数）
		List<File> selectFiles = Dialog.selectFiles();
		System.out.println(selectFiles);
	}

}
