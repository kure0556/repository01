package zircuf.env;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

import lombok.Getter;
import zircuf.util.io.common.CharsetOpt;
import zircuf.util.io.common.Encoding;
import zircuf.util.io.core.path.PathReader;

/**
 * アプリケーションが利用するリソース（読み取り専用ファイル）への入口（エンドポイント）を提供するユーティリティ。
 * <p>
 * デフォルトではクラスパス上のリソース（通常は src/main/resources 配下）を参照する。
 * {@link ClassLoader#getResource(String)} に基づいてパスを解決するため、
 * ビルド後の jar 内リソースも同じ方法で取得できる。
 * <p>
 * 読み取り専用のアクセスのみを提供し、書き込みは許可しない。
 * デプロイ環境ごとに基準ディレクトリを切り替えたい場合や、別種のストレージ実装（S3 / GCS など）へ
 * 差し替える際の拡張ポイントとなる。
 */
public class Resource {

	public static ResourceItem of(String pathStr) {
		return new ResourceItem(pathStr);
	}

	/**
	 * クラスパス上のリソースを表す読み取り専用の項目。
	 * <p>
	 * 指定されたパスは {@link ClassLoader#getResource(String)} を用いて解決され、
	 * 通常は src/main/resources 配下のファイルが対象となる。
	 * <p>
	 * リソースは読み取り専用であり、書き込み操作は提供しない。
	 * 必要に応じて文字コードを {@link #withEncode(Encoding)} で指定できる。
	 */
	public static class ResourceItem implements PathReader, CharsetOpt {

		@Getter
		private final Path path;

		@Getter
		protected Optional<Charset> charset = Optional.empty();

		private ResourceItem(String pathStr) {
			path = resourcePath(pathStr);
		}

		/**
		 * エンコードの指定
		 * @param encoding エンコード
		 * @return 自インスタンス
		 */
		public ResourceItem withEncode(Encoding encoding) {
			this.charset = Optional.of(encoding.getCharset());
			return this;
		}

		/**
		 * 解決済みのリソースパスを外部から参照するためのフック。
		 * <p>
		 * ResourceItem が内部で解決した {@link Path} を文字列として受け取り、
		 * 指定されたアクションに渡す。デバッグ用途で「最終的にどのパスが参照されているか」
		 * を確認したい場合に便利。
		 *
		 * <h2>使用例</h2>
		 * <pre>{@code
		 * Resource.of("config/app.json")
		 *         .peekPath(System.out::println);  // 実際に解決されたパスを出力
		 * }</pre>
		 *
		 * @param action 解決されたパス文字列を受け取る処理
		 * @return 自インスタンス（メソッドチェーン用）
		 */
		public final ResourceItem peekPath(Consumer<String> action) {
			action.accept(path.toString());
			return this;
		}

		private final Path resourcePath(String pathStr) {
			try {
				return Path.of(Resource.class.getClassLoader().getResource(pathStr).toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException("クラスパスで指定されたファイルが見つからない pathStr=" + pathStr, e);
			}
		}

	}
}
