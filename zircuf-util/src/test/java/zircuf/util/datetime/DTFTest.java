package zircuf.util.datetime;

import java.time.LocalDateTime;
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
		System.out.println(DTF.MILLIS_ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.MILLIS_ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.NANOS_ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.NANOS_ISO_DATE_TIME_ZONED.format(udt));
		System.out.println();

		ldt = DT.trimMillis(ldt);
		zdt = DT.trimMillis(zdt);
		udt = DT.trimMillis(udt);

		System.out.println(DTF.ISO_DATE_TIME.format(ldt));
		System.out.println(DTF.ISO_DATE_TIME.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.MILLIS_ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.MILLIS_ISO_DATE_TIME_ZONED.format(udt));
		System.out.println(DTF.NANOS_ISO_DATE_TIME_ZONED.format(zdt));
		System.out.println(DTF.NANOS_ISO_DATE_TIME_ZONED.format(udt));
		System.out.println();

//		fail("まだ実装されていません");
	}

}
