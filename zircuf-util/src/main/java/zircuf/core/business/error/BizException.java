package zircuf.core.business.error;

public class BizException extends RuntimeException {

	public static BizException of(String message) {
		return (BizException) new RuntimeException(message);
	}

	public static BizException of(String message, Throwable cause) {
		return (BizException) new RuntimeException(message, cause);
	}

}
