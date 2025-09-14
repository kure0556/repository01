package zircuf.tools.gen;

import java.io.File;

import org.junit.jupiter.api.Test;

import zircuf.env.Storage;
import zircuf.tools.swing.FileDropDialog;

class CodeGeneratorToolSample {

	private static final String FILE_NAME = "./object_define.tsv";

	@Test
	void test() {

		Storage.local().of(FILE_NAME).write("""
			my_field1									文字列	文字列の項目
			my_field2									整数	整数の項目
			my_field3									真偽値	真偽値の項目
			my_field4									文字列リスト	文字列リストの項目
			my_object1									オブジェクト	オブジェクトの項目
				my_field7								文字列	文字列の項目
				my_field8								整数	整数の項目
				my_field9								真偽値	真偽値の項目
				my_object2								オブジェクト	オブジェクトの項目
					my_field21							文字列	文字列の項目
					my_field22							整数	整数の項目
			my_field5_list									リスト	オブジェクトリストの項目
				my_field51								文字列	文字列の項目
			my_field6_map									マップ	オブジェクトマップの項目
				my_field61								文字列	文字列の項目
			""");
		
		new FileDropDialog("ファイル存在確認", (window, files) -> {
			for (File file : files) {
				boolean result = window.confirm("""
						ファイルが存在します。上書き保存しますか？
						%s\
						""".formatted(file.getAbsolutePath()));
				window.log("ダイアログ結果" + result);
				Storage.local().of(FILE_NAME).deleteIfExists();
			}
		});
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
