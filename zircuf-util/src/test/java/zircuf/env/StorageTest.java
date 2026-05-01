package zircuf.env;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import zircuf.tools.swing.Dialog;
import zircuf.util.io.common.Encoding;

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

	@Test
	void test2() {
		var temp = Storage.local().ofTemp("hoge", ".txt").withEncoding(Encoding.SJIS);
		temp.write("""
				エンコード確認①②③
				エンコード確認
				""");
		if (temp.isExists()) {
			System.out.println("ファイルあり" + temp.getPath());
		} else {
			System.out.println("ファイルなし" + temp.getPath());
		}

		String text = temp.toText();
		System.out.println(text);
	}

	@Test
	void test3() {
		Storage.local().of("hoge").peekPath(System.out::println).touch();
		Storage.local().of("huga/huga.txt").peekPath(System.out::println).touch();

		// 動作確認用ダイアログ
		Dialog.message("ファイルが出力されました。\n出力したファイルを削除します。");

		Storage.local().of("hoge").delete();
		Storage.local().of("huga/huga.txt").delete();
		Storage.local().of("huga").delete();
		Storage.local().of("").delete();
	}
}
