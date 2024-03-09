package zircuf.uti.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import zircuf.util.datetime.DTF;
import zircuf.util.datetime.DateTimes;

class DTFTest {

	@Test
	void test() {

		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(DateTimes.UTC);

		System.out.println(DTF.ISO_DATE_TIME.format(ldt));
		System.out.println(DTF.ISO_DATE_TIME.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.formatIsoAsUTC(zdt));
		System.out.println(DTF.formatIsoAsUTC(udt));
		System.out.println(DTF.NANOS_ISO_INSTANT.format(zdt));
		System.out.println(DTF.NANOS_ISO_INSTANT.format(udt));
		System.out.println();

		ldt = DateTimes.trim(ldt);
		zdt = DateTimes.trim(zdt);
		udt = DateTimes.trim(udt);

		System.out.println(DTF.ISO_DATE_TIME.format(ldt));
		System.out.println(DTF.ISO_DATE_TIME.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.formatIsoAsUTC(zdt));
		System.out.println(DTF.formatIsoAsUTC(udt));
		System.out.println(DTF.NANOS_ISO_INSTANT.format(zdt));
		System.out.println(DTF.NANOS_ISO_INSTANT.format(udt));
		System.out.println();

//		fail("まだ実装されていません");
	}

}
