package zircuf.util.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DT {

	public static final ZoneId JST = ZoneId.of("Asia/Tokyo");
	public static final ZoneId UTC = ZoneId.of("UTC");
	public static final ZoneOffset offsetJST = ZoneOffset.ofHours(9);

	// -----------------------------
	// 文字列からの変換
	// -----------------------------

	/**
	 * ISO形式でのparse {@link LocalDateTime#parse(text)} と同じ挙動
	 * @param text
	 * @return
	 */
	public static final LocalDateTime of(final String text) {
		return LocalDateTime.parse(text);
	}

	/**
	 * ISO形式でのparse {@link ZonedDateTime#parse(text)} と同じ挙動
	 * @param text
	 * @return
	 */
	public static final ZonedDateTime ofZoned(final String text) {
		return ZonedDateTime.parse(text);
	}

	// -----------------------------
	// Dateからの変換
	// -----------------------------

	public static final LocalDateTime of(final Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static final ZonedDateTime ofJST(final Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), JST);
	}

	// -----------------------------
	// EpochTime(ミリ秒)からの変換
	// -----------------------------

	public static final LocalDateTime of(final long epochTimeMillis) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTimeMillis), ZoneId.systemDefault());
	}

	public static final ZonedDateTime ofJST(final long epochTimeMillis) {
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochTimeMillis), JST);
	}

	// -----------------------------
	// EpochTimeへの変換
	// -----------------------------

	public static final long asEpochTimeMillis(final LocalDateTime localDateTime) {
		return asEpochTime(localDateTime) * 1000l;
	}

	public static final long asEpochTimeMillis(final ZonedDateTime zonedDateTime) {
		return asEpochTime(zonedDateTime) * 1000l;
	}

	public static final long asEpochTime(final LocalDateTime localDateTime) {
		return localDateTime.toInstant(ZoneOffset.ofHours(9)).getEpochSecond();
	}

	public static final long asEpochTime(final ZonedDateTime zonedDateTime) {
		return zonedDateTime.toInstant().getEpochSecond();
	}

	// -----------------------------
	// 秒以下の除去
	// -----------------------------

	public static final LocalDateTime trimMillis(final LocalDateTime localDateTime) {
		return localDateTime.truncatedTo(ChronoUnit.SECONDS);
	}

	public static final ZonedDateTime trimMillis(final ZonedDateTime zonedDateTime) {
		return zonedDateTime.truncatedTo(ChronoUnit.SECONDS);
	}

	public static final LocalDateTime trimNanos(final LocalDateTime localDateTime) {
		return localDateTime.truncatedTo(ChronoUnit.MILLIS);
	}

	public static final ZonedDateTime trimNanos(final ZonedDateTime zonedDateTime) {
		return zonedDateTime.truncatedTo(ChronoUnit.MILLIS);
	}

	// -----------------------------
	// その他
	// -----------------------------

	public static final String formatIsoAsUTC(final ZonedDateTime zdt) {
		// 標準フォーマッタISO_INSTANTはInstantを参照しているため、UTCでの表記にできる
		// ナノ秒を消してフォーマット
		return DateTimeFormatter.ISO_INSTANT.format(zdt.truncatedTo(ChronoUnit.SECONDS));
	}

	public static class Text {
		public static String asIsoFromFlat(final String yyyymmddhhmmss) {
			return DTF.ISO_DATE_TIME.format(DTF.DATE_TIME.of(yyyymmddhhmmss));
		}
	}

}
