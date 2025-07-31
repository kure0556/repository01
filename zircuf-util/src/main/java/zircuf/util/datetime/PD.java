package zircuf.util.datetime;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

/**
 * TemporalAmount（Period・Duration）ユーティリティ
 */
public class PD {

	/**
	 * 期間・時間を表すTemporalAmountへの変換
	 * <pre>
	 * P1Y  : 1年間
	 * P1M  : 1か月間
	 * P1W  : 1週間
	 * P1D  : 1に置換
	 * P1Y2M3D : 1年2か月と3日間
	 * PT1H : 1時間
	 * PT1M : 1分間
	 * PT1S : 1秒間
	 * PT1.345S : 1秒と345ミリ秒
	 * P2DT3H4M : 2日と3時間4分
	 * </pre>
	 * @param text
	 * @return
	 */
	public static final TemporalAmount of(String text) {
		if (text.startsWith("PT")) {
			return Duration.parse(text);
		} else {
			return Period.parse(text);
		}
	}
}
