package zircuf.util.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 現在時刻を提供するインターフェース。
 *
 * 設計方針:
 * - コアは Instant（UTCの絶対時間）
 * - LocalDateTime / ZonedDateTime は派生表現
 * - 精度（秒・ミリ秒・ナノ秒）は用途ごとに明示的に使い分ける
 *
 * 精度の使い分け:
 * - now()        : 秒精度（業務標準・比較やDB保存向け）
 * - nowMillis()  : ミリ秒精度（外部IF・ログ用途）
 * - nowRaw()     : ナノ秒精度（内部処理・厳密比較）
 *
 * 注意:
 * - タイムゾーンは JST 固定（DT.JST）
 * - 必要に応じて実装側で変更すること
 */
public interface TimeProvider {

	/**
	 * 現在時刻（UTCの絶対時間）。
	 *
	 * 責務:
	 * - 実装クラスはこのメソッドのみ提供すればよい
	 * - テストではここを差し替えることで全体を制御可能
	 */
	Instant nowInstant();

    /**
     * 現在時刻を「yyyyMMddHHmmss」形式で返す。
     *
     * 用途:
     * - 外部IFの連番生成
     * - ログIDやトランザクションIDの付番
     */
    default String nowYYYYMMDDHHMMSS() {
        return DTF.DATE_TIME.format(now());
    }

    /**
     * 現在日付を「yyyyMMdd」形式で返す。
     *
     * 用途:
     * - 日付キー
     * - バッチ処理の基準日
     */
    default String nowYYYYMMDD() {
        return DTF.DATE.format(now());
    }

    /**
     * 現在時刻を「HHmmss」形式で返す。
     *
     * 用途:
     * - ログ出力
     * - 秒単位の時刻比較
     */
    default String nowHHMMSS() {
        return DTF.TIME.format(now());
    }

    /**
	 * 現在時刻（秒精度 / LocalDateTime）。
	 *
	 * 用途:
	 * - 業務ロジック
	 * - DB保存
	 * - 時刻比較
	 */
	default LocalDateTime now() {
		return LocalDateTime.ofInstant(nowInstant(), DT.JST).truncatedTo(ChronoUnit.SECONDS);
	}

	/**
	 * 現在時刻（ミリ秒精度 / LocalDateTime）。
	 *
	 * 用途:
	 * - 外部インターフェース
	 * - ログ出力
	 */
	default LocalDateTime nowMillis() {
		return LocalDateTime.ofInstant(nowInstant(), DT.JST).truncatedTo(ChronoUnit.MILLIS);
	}

	/**
	 * 現在時刻（ナノ秒精度 / LocalDateTime）。
	 *
	 * 用途:
	 * - 内部処理
	 * - 高精度な比較
	 */
	default LocalDateTime nowRaw() {
		return LocalDateTime.ofInstant(nowInstant(), DT.JST);
	}

	/**
	 * 現在時刻（秒精度 / ZonedDateTime）。
	 */
	default ZonedDateTime nowZoned() {
		return ZonedDateTime.ofInstant(nowInstant(), DT.JST).truncatedTo(ChronoUnit.SECONDS);
	}

	/**
	 * 現在時刻（ミリ秒精度 / ZonedDateTime）。
	 */
	default ZonedDateTime nowZonedMillis() {
		return ZonedDateTime.ofInstant(nowInstant(), DT.JST).truncatedTo(ChronoUnit.MILLIS);
	}

	/**
	 * 現在時刻（ナノ秒精度 / ZonedDateTime）。
	 */
	default ZonedDateTime nowZonedRaw() {
		return ZonedDateTime.ofInstant(nowInstant(), DT.JST);
	}
}