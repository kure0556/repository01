package zircuf.util.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

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

}
