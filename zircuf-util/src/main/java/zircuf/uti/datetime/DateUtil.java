package zircuf.uti.datetime;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtil {

	/**
	 *  @deprecated より簡潔な代替メソッドがあります {@link DateTimes#of(Date)}
	 */
	@Deprecated()
	public static final LocalDateTime asLocalDateTime(final Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 *  @deprecated より簡潔な代替メソッドがあります {@link DateTimes#ofJST(Date)}
	 */
	@Deprecated()
	public static final ZonedDateTime asZonedDateTime(final Date date) {
		return asZonedDateTime(date, ZoneId.systemDefault());
	}

	public static final ZonedDateTime asZonedDateTime(final Date date, final ZoneId zoneId) {
		return ZonedDateTime.ofInstant(date.toInstant(), zoneId);
	}

	// -----------------------------
	// Dateへの変換
	// -----------------------------

	public static final Date of(final LocalDateTime localDateTime) {
		OffsetDateTime odt = OffsetDateTime.now(ZoneId.systemDefault());
		ZoneOffset zoneOffset = odt.getOffset();
		return Date.from(localDateTime.toInstant(zoneOffset));
	}

	public static final Date of(final ZonedDateTime zonedDateTime) {
		return Date.from(zonedDateTime.toInstant());
	}
}
