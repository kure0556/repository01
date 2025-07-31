package zircuf.env;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import zircuf.tools.swing.Dialog;

class StorageTest {

	@Test
	void test() {
		// 書き込み
		String now = LocalDateTime.now().toString();
		Storage.local().of("data/test.txt").write(now);
		// 読み込み
		String text = Storage.local().of("data/test.txt").toText();
		// 書き込み・読み込みの内容が一致しているか
		assertEquals(now, text);

		// 動作確認用ダイアログ
		Dialog.message("ファイルが出力されました。\n出力したファイルを削除します。");

		// ファイルの削除
		Storage.local().of("data/test.txt").delete();
		// ファイルが削除されたか
		boolean existsFile = Storage.local().of("data/test.txt").isExists();
		assertFalse(existsFile);

		// ディレクトリの削除
		Storage.local().of("data").delete();
		// ディレクトリが削除できたか
		boolean existsDir = Storage.local().of("data").isExists();
		assertFalse(existsDir);
	}

}
