package zircuf.util.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * DateTime関連ユーティリティの入口カタログ。
 *
 * 方針:
 * - DTは「探すための入口」に限定する
 * - 実処理は意味別クラスへ委譲する
 * - static集約はするが責務は持たない
 *
 * 構造:
 * - DT.Parse   : 文字列 → 時刻
 * - DT.Convert : 型変換（Date / Instant / Epoch）
 * - DT.Trim    : 精度調整（秒・ミリ秒）
 * - DT.Format  : 出力整形
 * - DT.Zone    : タイムゾーン関連
 */
public final class DT {

    private DT() {}

    // =========================
    // Parse（文字列 → 時刻）
    // =========================
    public static final class Parse {

    	/**
    	 * ISO形式でのparse {@link LocalDateTime#parse(text)} と同じ挙動
    	 * @param text
    	 * @return
    	 */
        public static LocalDateTime fromIso(String text) {
            return LocalDateTime.parse(text);
        }

    	/**
    	 * ISO形式でのparse {@link ZonedDateTime#parse(text)} と同じ挙動
    	 * @param text
    	 * @return
    	 */
        public static ZonedDateTime fromIsoZoned(String text) {
            return ZonedDateTime.parse(text);
        }

        /**
         * フラット形式の日時表記でのparse
         * @param yyyymmddhhmmss
         * @return
         */
        public static LocalDateTime fromFlatDateTime(String yyyymmddhhmmss) {
            return DTF.DATE_TIME.of(yyyymmddhhmmss);
        }
    }

    // =========================
    // Convert（型変換）
    // =========================
    public static final class Convert {

        public static LocalDateTime fromDate(Date date) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }

        public static ZonedDateTime fromDateJST(Date date) {
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Tokyo"));
        }

        public static LocalDateTime fromEpochMillis(long epochMillis) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault());
        }

        public static ZonedDateTime fromEpochMillisJST(long epochMillis) {
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.of("Asia/Tokyo"));
        }

        public static long toEpochSeconds(Instant instant) {
            return instant.getEpochSecond();
        }

        public static long toEpochMillis(Instant instant) {
            return instant.toEpochMilli();
        }

        public static long toEpochSeconds(LocalDateTime ldt) {
            return ldt.toInstant(ZoneOffset.ofHours(9)).getEpochSecond();
        }

        public static long toEpochSeconds(ZonedDateTime zdt) {
            return zdt.toInstant().getEpochSecond();
        }
    }

    // =========================
    // Trim（精度制御）
    // =========================
    public static final class Trim {

        /**
         * 秒までの精度に変換する（ミリ秒以下の切り捨て）
         * @param t 精度変更対象の日時
         * @return 秒精度日時
         */
        public static LocalDateTime seconds(LocalDateTime t) {
            return t.truncatedTo(ChronoUnit.SECONDS);
        }

        /**
         * 秒までの精度に変換する（ミリ秒以下の切り捨て）
         * @param t 精度変更対象の日時
         * @return 秒精度日時
         */
        public static ZonedDateTime seconds(ZonedDateTime t) {
            return t.truncatedTo(ChronoUnit.SECONDS);
        }

        /**
         * ミリ秒までの精度に変換する（ミリ秒未満の切り捨て）
         * @param t 精度変更対象の日時
         * @return ミリ秒精度日時
         */
        public static LocalDateTime millis(LocalDateTime t) {
            return t.truncatedTo(ChronoUnit.MILLIS);
        }

        /**
         * ミリ秒までの精度に変換する（ミリ秒未満の切り捨て）
         * @param t 精度変更対象の日時
         * @return ミリ秒精度日時
         */
        public static ZonedDateTime millis(ZonedDateTime t) {
            return t.truncatedTo(ChronoUnit.MILLIS);
        }
    }

    // =========================
    // Format（出力）
    // =========================
    public static final class Format {

        public static String iso(LocalDateTime ldt) {
            return DTF.ISO_DATE_TIME_MILLIS.format(ldt);
        }
        public static String iso(ZonedDateTime ldt) {
            return DTF.ISO_DATE_TIME_MILLIS_ZONED.format(ldt);
        }
        public static String isoUtc(ZonedDateTime zdt) {
            return DateTimeFormatter.ISO_INSTANT.format(zdt.toInstant());
        }
    }

    // =========================
    // Zone（タイムゾーン）
    // =========================
    public static final class Zone {

        public static final ZoneId JST = ZoneId.of("Asia/Tokyo");
        public static final ZoneId UTC = ZoneId.of("UTC");
        public static final ZoneOffset JST_OFFSET = ZoneOffset.ofHours(9);
    }
}

//public class DT {
//
//	public static final ZoneId JST = ZoneId.of("Asia/Tokyo");
//	public static final ZoneId UTC = ZoneId.of("UTC");
//	public static final ZoneOffset offsetJST = ZoneOffset.ofHours(9);
//
//	// -----------------------------
//	// 文字列からの変換
//	// -----------------------------
//
//	/**
//	 * ISO形式でのparse {@link LocalDateTime#parse(text)} と同じ挙動
//	 * @param text
//	 * @return
//	 */
//	public static final LocalDateTime of(final String text) {
//		return LocalDateTime.parse(text);
//	}
//
//	/**
//	 * ISO形式でのparse {@link ZonedDateTime#parse(text)} と同じ挙動
//	 * @param text
//	 * @return
//	 */
//	public static final ZonedDateTime ofZoned(final String text) {
//		return ZonedDateTime.parse(text);
//	}
//
//	// -----------------------------
//	// Dateからの変換
//	// -----------------------------
//
//	public static final LocalDateTime of(final Date date) {
//		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//	}
//
//	public static final ZonedDateTime ofJST(final Date date) {
//		return ZonedDateTime.ofInstant(date.toInstant(), JST);
//	}
//
//	// -----------------------------
//	// EpochTime(ミリ秒)からの変換
//	// -----------------------------
//
//	public static final LocalDateTime of(final long epochTimeMillis) {
//		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTimeMillis), ZoneId.systemDefault());
//	}
//
//	public static final ZonedDateTime ofJST(final long epochTimeMillis) {
//		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochTimeMillis), JST);
//	}
//
//	// -----------------------------
//	// EpochTimeへの変換
//	// -----------------------------
//
//	public static final long asEpochTimeMillis(final LocalDateTime localDateTime) {
//		return asEpochTime(localDateTime) * 1000l;
//	}
//
//	public static final long asEpochTimeMillis(final ZonedDateTime zonedDateTime) {
//		return asEpochTime(zonedDateTime) * 1000l;
//	}
//
//	public static final long asEpochTime(final LocalDateTime localDateTime) {
//		return localDateTime.toInstant(ZoneOffset.ofHours(9)).getEpochSecond();
//	}
//
//	public static final long asEpochTime(final ZonedDateTime zonedDateTime) {
//		return zonedDateTime.toInstant().getEpochSecond();
//	}
//
//	// -----------------------------
//	// 秒以下の除去
//	// -----------------------------
//
//	public static final LocalDateTime trimMillis(final LocalDateTime localDateTime) {
//		return localDateTime.truncatedTo(ChronoUnit.SECONDS);
//	}
//
//	public static final ZonedDateTime trimMillis(final ZonedDateTime zonedDateTime) {
//		return zonedDateTime.truncatedTo(ChronoUnit.SECONDS);
//	}
//
//	public static final LocalDateTime trimNanos(final LocalDateTime localDateTime) {
//		return localDateTime.truncatedTo(ChronoUnit.MILLIS);
//	}
//
//	public static final ZonedDateTime trimNanos(final ZonedDateTime zonedDateTime) {
//		return zonedDateTime.truncatedTo(ChronoUnit.MILLIS);
//	}
//
//	// -----------------------------
//	// その他
//	// -----------------------------
//
//	public static final String formatIsoAsUTC(final ZonedDateTime zdt) {
//		// 標準フォーマッタISO_INSTANTはInstantを参照しているため、UTCでの表記にできる
//		// ナノ秒を消してフォーマット
//		return DateTimeFormatter.ISO_INSTANT.format(zdt.truncatedTo(ChronoUnit.SECONDS));
//	}
//
//	public static class Text {
//		public static String asIsoFromFlat(final String yyyymmddhhmmss) {
//			return DTF.ISO_DATE_TIME.format(DTF.DATE_TIME.of(yyyymmddhhmmss));
//		}
//	}
//
//}
