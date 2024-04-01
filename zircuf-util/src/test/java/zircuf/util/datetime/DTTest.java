package zircuf.util.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

import zircuf.util.performance.Performance;

class DTTest {

	@Test
	void test() {
		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(DT.UTC);

		System.out.println(DT.formatIsoAsUTC(zdt));
		System.out.println(DT.formatIsoAsUTC(udt));

		ldt = DT.trimMillis(ldt);
		zdt = DT.trimMillis(zdt);
		udt = DT.trimMillis(udt);

		System.out.println(DT.formatIsoAsUTC(zdt));
		System.out.println(DT.formatIsoAsUTC(udt));

	}

	@Test
	void test2() {
		Date date = new Date();
		System.out.println(DT.of(date));		// LocalDateTime
		System.out.println(DT.ofJST(date));		// ZonedDateTime
		System.out.println(DT.of(date.getTime()));		// LocalDateTime
		System.out.println(DT.ofJST(date.getTime()));	// ZonedDateTime
		System.out.println();

		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(DT.UTC);

		System.out.println(DT.of(DT.asEpochTimeMillis(ldt)));
		System.out.println(DT.of(DT.asEpochTimeMillis(zdt)));
		System.out.println(DT.of(DT.asEpochTimeMillis(udt)));
		System.out.println(DT.ofJST(DT.asEpochTimeMillis(ldt)));
		System.out.println(DT.ofJST(DT.asEpochTimeMillis(zdt)));
		System.out.println(DT.ofJST(DT.asEpochTimeMillis(udt)));
		System.out.println();

		Performance.of(s -> {
			DT.trimMillis(LocalDateTime.now());
		});

		Performance.of(s -> {
			LocalDateTime.now().withNano(0);
		});

		LocalDateTime asLocalDateTime = DTF.DATE_TIME.of("19870312235911");
		System.out.println(asLocalDateTime);

		ZonedDateTime asZonedDateTime = DTF.ISO_DATE_TIME_ZONED.ofZoned("1987-03-12T23:59:11Z");
		System.out.println(asZonedDateTime);
		System.out.println(DT.of("1987-03-12T23:59:11"));
		System.out.println(DT.ofZoned("1987-03-12T23:59:11Z"));
		System.out.println(DT.ofZoned("1987-03-12T23:59:11+09:00"));
		System.out.println();

		System.out.println(DT.Text.asIsoFromFlat("19870312235911"));
	}

}
