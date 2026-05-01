package zircuf.util.datetime;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

class DTFTest {

	@Test
	void test() {

		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(DT.UTC);

		System.out.println(DTF.ISO_DATE_TIME.format(ldt));
		System.out.println(DTF.ISO_DATE_TIME.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.ISO_DATE_TIME_MILLIS_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_MILLIS_ZONED.format(udt));
		System.out.println(DTF.ISO_DATE_TIME_NANOS_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_NANOS_ZONED.format(udt));
		System.out.println();

		ldt = DT.trimMillis(ldt);
		zdt = DT.trimMillis(zdt);
		udt = DT.trimMillis(udt);

		System.out.println(DTF.ISO_DATE_TIME.format(ldt));
		System.out.println(DTF.ISO_DATE_TIME.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.ISO_DATE_TIME_MILLIS_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_MILLIS_ZONED.format(udt));
		System.out.println(DTF.ISO_DATE_TIME_NANOS_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_NANOS_ZONED.format(udt));
		System.out.println();

//		fail("まだ実装されていません");
	}

    private static final LocalDateTime FIXED_LDT =
            LocalDateTime.of(2024, 4, 1, 12, 34, 56, 789_123_456);

    private static final ZonedDateTime FIXED_ZDT =
            ZonedDateTime.of(FIXED_LDT, ZoneId.of("Asia/Tokyo"));

    // ---------------------------------------------------------
    // format()
    // ---------------------------------------------------------
    @Test
    void testFormat_DATE() {
        String actual = DTF.DATE.format(FIXED_LDT);
        assertEquals("20240401", actual);
    }

    @Test
    void testFormat_TIME() {
        String actual = DTF.TIME.format(FIXED_LDT);
        assertEquals("123456", actual);
    }

    @Test
    void testFormat_DATE_TIME() {
        String actual = DTF.DATE_TIME.format(FIXED_LDT);
        assertEquals("20240401123456", actual);
    }

    @Test
    void testFormat_DATE_TIME_MILLIS() {
        String actual = DTF.DATE_TIME_MILLIS.format(FIXED_LDT);
        assertEquals("20240401123456789", actual);
    }

    @Test
    void testFormat_READABLE_DATE_TIME() {
        String actual = DTF.READABLE_DATE_TIME.format(FIXED_LDT);
        assertEquals("2024/04/01 12:34:56", actual);
    }

    @Test
    void testFormat_READABLE_DATE_TIME_MILLIS() {
        String actual = DTF.READABLE_DATE_TIME_MILLIS.format(FIXED_LDT);
        assertEquals("2024/04/01 12:34:56.789", actual);
    }

    @Test
    void testFormat_ISO_DATE_TIME() {
        String actual = DTF.ISO_DATE_TIME.format(FIXED_LDT);
        assertEquals("2024-04-01T12:34:56", actual);
    }

    @Test
    void testFormat_ISO_DATE_TIME_MILLIS() {
        String actual = DTF.ISO_DATE_TIME_MILLIS.format(FIXED_LDT);
        assertEquals("2024-04-01T12:34:56.789", actual);
    }

    @Test
    void testFormat_ISO_DATE_TIME_NANOS() {
        String actual = DTF.ISO_DATE_TIME_NANOS.format(FIXED_LDT);
        assertEquals("2024-04-01T12:34:56.789123456", actual); // ISO_LOCAL_DATE_TIME はナノ秒も出す
    }

    // ---------------------------------------------------------
    // of() LocalDateTime.parse
    // ---------------------------------------------------------
    @Test
    void testOf_DATE() {
        LocalDate actual = DTF.DATE.ofDate("20240401");
        assertEquals(LocalDate.of(2024, 4, 1).atStartOfDay(), actual);
    }

    @Test
    void testOf_TIME() {
        LocalTime actual = DTF.TIME.ofTime("123456");
        assertEquals(LocalTime.of(12, 34, 56), actual);
    }

    @Test
    void testOf_DATE_TIME() {
        LocalDateTime actual = DTF.DATE_TIME.of("20240401123456");
        assertEquals(LocalDateTime.of(2024, 4, 1, 12, 34, 56), actual);
    }

    @Test
    void testOf_DATE_TIME_MILLIS() {
        LocalDateTime actual = DTF.DATE_TIME_MILLIS.of("20240401123456789");
        assertEquals(FIXED_LDT, actual);
    }

    // ---------------------------------------------------------
    // ofZoned() ZonedDateTime.parse
    // ---------------------------------------------------------
    @Test
    void testOfZoned_ISO_DATE_TIME_ZONED() {
        ZonedDateTime actual = DTF.ISO_DATE_TIME_ZONED.ofZoned("2024-04-01T12:34:56+09:00");
        assertEquals(FIXED_ZDT, actual);
    }

    @Test
    void testOfZoned_ISO_DATE_TIME_MILLIS_ZONED() {
        ZonedDateTime actual = DTF.ISO_DATE_TIME_MILLIS_ZONED.ofZoned("2024-04-01T12:34:56.789+09:00");
        assertEquals(FIXED_ZDT, actual);
    }

    // ---------------------------------------------------------
    // ofText() 形式変換
    // ---------------------------------------------------------
    @Test
    void testOfText_convert_DATE_TIME_to_READABLE() {
        String actual = DTF.DATE_TIME.ofText("20240401123456", DTF.READABLE_DATE_TIME);
        assertEquals("2024/04/01 12:34:56", actual);
    }

    @Test
    void testOfText_convert_READABLE_to_DATE_TIME() {
        String actual = DTF.READABLE_DATE_TIME.ofText("2024/04/01 12:34:56", DTF.DATE_TIME);
        assertEquals("20240401123456", actual);
    }

}
