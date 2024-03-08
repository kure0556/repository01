package zircuf.uti.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import zircuf.uti.performance.Performance;

public class DateTimes {
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(of(date));
		System.out.println(of(date.getTime()));
		System.out.println(ofJST(date));
		System.out.println(ofUTC(date.getTime()));
		System.out.println();

		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(UTC);

		System.out.println(of(asEpochTimeMillisJST(ldt)));
		System.out.println(of(asEpochTimeMillis(zdt)));
		System.out.println();

		ldt = trim(ldt);
		zdt = trim(zdt);
		udt = trim(udt);

		System.out.println(DTF.ISO_DATE_TIME.format(ldt));
		System.out.println(DTF.ISO_DATE_TIME.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.formatIsoAsUTC(zdt));
		System.out.println(DTF.formatIsoAsUTC(udt));
		System.out.println(DTF.NANOS_ISO_INSTANT.format(zdt));
		System.out.println(DTF.NANOS_ISO_INSTANT.format(udt));
		System.out.println();

		new Performance() {
			@Override
			protected void proc() {
				trim(LocalDateTime.now());
			}
		}.keisoku();

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

	public static final ZonedDateTime ofUTC(final Date date) {
		return ZonedDateTime.ofInstant(date.toInstant(), UTC);
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

	public static final ZonedDateTime ofUTC(final long epochTimeMillis) {
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochTimeMillis), UTC);
	}

	// -----------------------------
	// EpochTimeへの変換
	// -----------------------------

	public static final long asEpochTimeMillisJST(final LocalDateTime localDateTime) {
		return asEpochTimeJST(localDateTime) * 1000l;
	}

	public static final long asEpochTimeMillis(final ZonedDateTime zonedDateTime) {
		return asEpochTime(zonedDateTime) * 1000l;
	}

	public static final long asEpochTimeJST(final LocalDateTime localDateTime) {
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

}
