package zircuf.core.business.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BizLogLevel {

	/** 情報 */
	INFORMATION("INF", "INFO "),
	/** 警告 */
	WARNING("WRN", "WARN "),
	/** 異常 */
	ERROR("ERR", "ERROR"),
	;

	@Getter
	private final String shortName;
	@Getter
	private final String shortName5;

}
