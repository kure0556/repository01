package zircuf.env;

import zircuf.env.storage.LocalStorage;

public class Storage {

	public static LocalStorage local() {
		// ローカルストレージを使用する場合のデフォルトパス（/は任意）
		// 環境に応じて変更する
		return new LocalStorage("LOCAL_STORAGE/");
	}

}
