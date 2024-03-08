package zircuf.uti.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;

public enum DTF {

	/** yyyymmdd */
	DATE("yyyyMMdd"),
	/** hhmmss */
	TIME("HHmmss"),
	/** yyyymmddhhmmss */
	DATE_TIME("yyyyMMddHHmmss"),

	/** yyyy/mm/dd hh:mm:ss */
	DATE_TIME_LOG("yyyy/MM/dd HH:mm:ss"),

	/**
	 * yyyy-mm-ddThh:mm:ss
	 */
	ISO_DATE_TIME("yyyy-MM-dd'T'HH:mm:ss"),
	/**
	 * yyyy-mm-ddThh:mm:ssXXX
	 */
	ISO_DATE_TIME_ZONED("yyyy-MM-dd'T'HH:mm:ssXXX"),
	/**
	 * yyyy-mm-ddThh:mm:ss.n
	 */
	NANOS_ISO_DATE_TIME(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
	/**
	 * yyyy-mm-ddThh:mm:ss.nXXX[V]
	 */
	NANOS_ISO_DATE_TIME_ZONED(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
	/**
	 * yyyy-mm-ddThh:mm:ss.nZ<br/>
	 * Instantからフォーマットするので必ずUTC（Z帯）になります。<br/>
	 * ただしナノ秒が表示されるため、表示させたくない場合は{@link DTF#formatAsUTC(ZonedDateTime)}を使用します。
	 */
	NANOS_ISO_INSTANT(DateTimeFormatter.ISO_INSTANT),
	;

	private final DateTimeFormatter dateTimeFormatter;

	DTF(String pattern) {
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
	}

	DTF(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	public final String format(final TemporalAccessor temporal) {
		return dateTimeFormatter.format(temporal);
	}

	/**
	 * 入力がISO形式であれば {@link LocalDateTime#parse(text)} や<br/>
	 * {@link ZonedDateTime#parse(text)} が推奨される
	 * @param text
	 * @return
	 */
	public final TemporalAccessor parse(final String text) {
		return dateTimeFormatter.parse(text);
	}

	public final LocalDateTime asLocalDateTime(final String text) {
		return LocalDateTime.parse(text, dateTimeFormatter);
	}

	public final ZonedDateTime asZonedDateTime(final String text) {
		return ZonedDateTime.parse(text, dateTimeFormatter);
	}

	public static final String formatIsoAsUTC(final ZonedDateTime zdt) {
		// 標準フォーマッタISO_INSTANTはInstantを参照しているため、UTCでの表記にできる
		// ナノ秒を消してフォーマット
		return DateTimeFormatter.ISO_INSTANT.format(zdt.truncatedTo(ChronoUnit.SECONDS));
	}

	/**
	 * ISO形式でのparse {@link LocalDateTime#parse(text)} と同じ挙動
	 * @param text
	 * @return
	 */
	public static final LocalDateTime ofLocalDateTime(final String text) {
		return LocalDateTime.parse(text);
	}

	/**
	 * ISO形式でのparse {@link ZonedDateTime#parse(text)} と同じ挙動
	 * @param text
	 * @return
	 */
	public static final ZonedDateTime ofZonedDateTime(final String text) {
		return ZonedDateTime.parse(text);
	}

}