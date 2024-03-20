package zircuf.util.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import zircuf.util.performance.Performance;

/**
 * 
 */
public class DT {
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(DT.of(date));		// LocalDateTime
		System.out.println(DT.ofJST(date));		// ZonedDateTime
		System.out.println(DT.of(date.getTime()));		// LocalDateTime
		System.out.println(DT.ofJST(date.getTime()));	// ZonedDateTime
		System.out.println();

		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(DT.UTC);

		System.out.println(DT.of(asEpochTimeMillis(ldt)));
		System.out.println(DT.of(asEpochTimeMillis(zdt)));
		System.out.println(DT.of(asEpochTimeMillis(udt)));
		System.out.println(DT.ofJST(asEpochTimeMillis(ldt)));
		System.out.println(DT.ofJST(asEpochTimeMillis(zdt)));
		System.out.println(DT.ofJST(asEpochTimeMillis(udt)));
		System.out.println();

		Performance.of(s -> {
			trim(LocalDateTime.now());
		});

		new Performance() {
			@Override
			protected void proc() {
				LocalDateTime.now().withNano(0);
			}
		}.keisoku();

		LocalDateTime asLocalDateTime = DTF.DATE_TIME.asLocalDateTime("19870312235911");
		System.out.println(asLocalDateTime);

		ZonedDateTime asZonedDateTime = DTF.ISO_DATE_TIME_ZONED.asZonedDateTime("1987-03-12T23:59:11Z");
		System.out.println(asZonedDateTime);
		System.out.println(DTF.ofLocalDateTime("1987-03-12T23:59:11"));
		System.out.println(DTF.ofZonedDateTime("1987-03-12T23:59:11Z"));
		System.out.println(DTF.ofZonedDateTime("1987-03-12T23:59:11+09:00"));
		System.out.println();

		System.out.println(DT.Text.asIsoFromFlat("19870312235911"));
	}

	public static final ZoneId JST = ZoneId.of("Asia/Tokyo");
	public static final ZoneId UTC = ZoneId.of("UTC");

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
	// 秒の除去
	// -----------------------------

	public static final LocalDateTime trim(final LocalDateTime localDateTime) {
		return localDateTime.truncatedTo(ChronoUnit.SECONDS);
	}

	public static final ZonedDateTime trim(final ZonedDateTime zonedDateTime) {
		return zonedDateTime.truncatedTo(ChronoUnit.SECONDS);
	}

	public static final LocalDateTime trimNanos(final LocalDateTime localDateTime) {
		return localDateTime.truncatedTo(ChronoUnit.MILLIS);
	}

	public static final ZonedDateTime trimNanos(final ZonedDateTime zonedDateTime) {
		return zonedDateTime.truncatedTo(ChronoUnit.MILLIS);
	}

	public static class Text {
		public static String asIsoFromFlat(final String yyyymmddhhmmss) {
			return DTF.ISO_DATE_TIME.format(DTF.DATE_TIME.parse(yyyymmddhhmmss));
		}
	}
}
