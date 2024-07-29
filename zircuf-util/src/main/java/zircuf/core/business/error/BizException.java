package zircuf.core.business.error;

/**
 * 業務処理例外クラス<br/>
 * 業務処理にて能動的なチェック・例外キャッチによりthrowされる例外
 */
public class BizException extends RuntimeException {

	public static BizException of(String message) {
		return (BizException) new RuntimeException(message);
	}

	public static BizException of(String message, Throwable cause) {
		return (BizException) new RuntimeException(message, cause);
	}

}
