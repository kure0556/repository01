package zircuf.env;

import zircuf.env.storage.LocalStorage;

/**
 * アプリケーションが利用するストレージへの入口（エンドポイント）を提供するユーティリティ。
 * <p>
 * 現在は「このプログラムが動作しているマシンのローカルストレージ」を返す実装のみを提供する。
 * デプロイ環境ごとに基準ディレクトリを切り替えたい場合や、別種のストレージ実装（S3 / GCS など）へ
 * 差し替える際の拡張ポイントとなる。
 */
public class Storage {

	/**
	 * 実行中のマシンのローカルストレージを表す {@link LocalStorage} を返す。
	 * <p>
	 * アプリケーションが使用する基準ディレクトリを一元管理するためのメソッドであり、
	 * 呼び出し側はパスや環境差異を意識せずにローカルストレージへアクセスできる。
	 */
	public static LocalStorage local() {
		// ローカルストレージを使用する場合のデフォルトパス（/は任意）
		// 環境に応じて変更する
		// return new LocalStorage("/var/app/");
		return new LocalStorage("LOCAL_STORAGE/");
	}

}
