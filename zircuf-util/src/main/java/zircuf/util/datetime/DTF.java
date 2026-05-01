package zircuf.util.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public enum DTF {

	/** yyyymmdd */
	DATE("yyyyMMdd"),
	/** hhmmss */
	TIME("HHmmss"),
	/** yyyymmddhhmmss */
	DATE_TIME("yyyyMMddHHmmss"),
	/** yyyymmddhhmmssSSS */
	DATE_TIME_MILLIS("yyyyMMddHHmmssSSS"),

	/** yyyy/MM/dd */
	READABLE_DATE("yyyy/MM/dd"),
	/** HH:mm:ss */
	READABLE_TIME("HH:mm:ss"),
	/** yyyy/MM/dd HH:mm:ss */
	READABLE_DATE_TIME("yyyy/MM/dd HH:mm:ss"),
	/** yyyy/MM/dd HH:mm:ss.SSS */
	READABLE_DATE_TIME_MILLIS("yyyy/MM/dd HH:mm:ss.SSS"),

	/** yyyy-MM-ddThh:mm:ss */
	ISO_DATE_TIME("yyyy-MM-dd'T'HH:mm:ss"),
	/** yyyy-MM-ddThh:mm:ssXXX */
	ISO_DATE_TIME_ZONED("yyyy-MM-dd'T'HH:mm:ssXXX"),
	/** yyyy-MM-ddThh:mm:ss.SSS */
	ISO_DATE_TIME_MILLIS("yyyy-MM-dd'T'HH:mm:ss.SSS"),
	/** yyyy-MM-ddThh:mm:ss.SSSXXX */
	ISO_DATE_TIME_MILLIS_ZONED("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
	/** yyyy-MM-ddThh:mm:ss.n */
	ISO_DATE_TIME_NANOS(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
	/** yyyy-MM-ddThh:mm:ss.nXXX[V] */
	ISO_DATE_TIME_NANOS_ZONED(DateTimeFormatter.ISO_OFFSET_DATE_TIME),

	;

	private final DateTimeFormatter dateTimeFormatter;

	DTF(String pattern) {
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
	}

	DTF(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	/**
	 * 文字列を LocalDateTime として解釈する。
	 * @param text 変換元の日時文字列
	 * @return LocalDateTime
	 */
	public final LocalDateTime of(final String text) {
		return LocalDateTime.parse(text, dateTimeFormatter);
	}

	/**
	 * 文字列を LocalDate として解釈する。
	 * @param text 変換元の日時文字列
	 * @return LocalDate
	 */
	public final LocalDate ofDate(final String text) {
		return LocalDate.parse(text, dateTimeFormatter);
	}

	/**
	 * 文字列を LocalTime として解釈する。
	 * @param text 変換元の日時文字列
	 * @return LocalTime
	 */
	public final LocalTime ofTime(final String text) {
		return LocalTime.parse(text, dateTimeFormatter);
	}

	/**
	 * 文字列を ZonedDateTime として解釈する。
	 * @param text 変換元の日時文字列
	 * @return ZonedDateTime
	 */
	public final ZonedDateTime ofZoned(final String text) {
		return ZonedDateTime.parse(text, dateTimeFormatter);
	}

	/**
	 * このフォーマットで解釈した文字列を、別のフォーマットで出力する。
	 * @param text  変換元の日時文字列
	 * @param other 変換先のフォーマット
	 * @return 変換後の日時文字列
	 */
	public final String ofText(final String text, DTF other) {
		return other.format(dateTimeFormatter.parse(text));
	}

	/**
	 * TemporalAccessor をこのフォーマットで文字列化する。
	 * @param temporal LocalDate/LocalTime/LocalDateTime/ZonedDateTime等の日時情報
	 * @return 変換後の日時文字列
	 */
	public final String format(final TemporalAccessor temporal) {
		return dateTimeFormatter.format(temporal);
	}
}