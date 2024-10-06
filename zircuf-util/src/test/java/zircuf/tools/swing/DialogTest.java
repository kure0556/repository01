package zircuf.tools.swing;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import zircuf.tools.swing.util.SwingUtil;

class DialogTest {

	@ParameterizedTest
	@ValueSource(booleans = { false, true })
	void test(boolean flg) {
		if (flg) {
			SwingUtil.uiWindows();
		}
		Dialog.message("メッセージ");
		Dialog.messageWarn("メッセージ");
		Dialog.input("メッセージ");
		Dialog.confirm("メッセージ");
		Optional<File> selectFile = Dialog.selectFile();
		List<File> selectFiles = Dialog.selectFiles();
	}

}
