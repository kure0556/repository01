package zircuf.util.datetime;

import java.time.LocalDateTime;
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
	 * yyyy-mm-ddThh:mm:ss.SSS
	 */
	MILLIS_ISO_DATE_TIME("yyyy-MM-dd'T'HH:mm:ss.SSS"),
	/**
	 * yyyy-mm-ddThh:mm:ss.SSSXXX
	 */
	MILLIS_ISO_DATE_TIME_ZONED("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
	/**
	 * yyyy-mm-ddThh:mm:ss.n
	 */
	NANOS_ISO_DATE_TIME(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
	/**
	 * yyyy-mm-ddThh:mm:ss.nXXX[V]
	 */
	NANOS_ISO_DATE_TIME_ZONED(DateTimeFormatter.ISO_OFFSET_DATE_TIME),

	;

	private final DateTimeFormatter dateTimeFormatter;

	DTF(String pattern) {
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
	}

	DTF(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	public final LocalDateTime of(final String text) {
		return LocalDateTime.parse(text, dateTimeFormatter);
	}

	public final ZonedDateTime ofZoned(final String text) {
		return ZonedDateTime.parse(text, dateTimeFormatter);
	}

	public final String ofText(final String text, DTF other) {
		return other.format(dateTimeFormatter.parse(text));
	}

	public final String format(final TemporalAccessor temporal) {
		return dateTimeFormatter.format(temporal);
	}

}