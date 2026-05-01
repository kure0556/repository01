package zircuf.env;

import org.junit.jupiter.api.Test;

import zircuf.util.datetime.DTF;

class SystemClockTest {

	@Test
	void test() throws InterruptedException {

		SystemClock systemJst = SystemClock.systemJst();
		System.out.println(systemJst.now());
		System.out.println(systemJst.nowMillis());
		System.out.println(systemJst.nowRaw());
		System.out.println(systemJst.nowZoned());
		System.out.println(systemJst.nowZonedMillis());
		System.out.println(systemJst.nowZonedRaw());

		System.out.println(DTF.READABLE_DATE_TIME_MILLIS.format(systemJst.now()));
		System.out.println(DTF.READABLE_DATE_TIME_MILLIS.format(systemJst.nowMillis()));
		System.out.println(DTF.READABLE_DATE_TIME_MILLIS.format(systemJst.nowRaw()));
		System.out.println(DTF.READABLE_DATE_TIME_MILLIS.format(systemJst.nowZoned()));
		System.out.println(DTF.READABLE_DATE_TIME_MILLIS.format(systemJst.nowZonedMillis()));
		System.out.println(DTF.READABLE_DATE_TIME_MILLIS.format(systemJst.nowZonedRaw()));


//		SystemClock systemClock = new SystemClock();
//		System.out.println(systemClock.now());
//
//		System.out.println();
//
//		systemClock.setOffset("-P5D");
//
//		System.out.println(systemClock.now());
//		Thread.sleep(1000);
//		System.out.println(systemClock.now());
//
//		System.out.println();
//
//		systemClock.setFixed("2024-01-01T16:00:00");
//
//		System.out.println(systemClock.now());
//		Thread.sleep(1000);
//		System.out.println(systemClock.now());
//
//		System.out.println();
//
//		systemClock = new SystemClock();
//		systemClock.setOrigin(2024, 1, 1, 16, 0);
//
//		System.out.println(systemClock.now());
//		Thread.sleep(60000);
//		System.out.println(systemClock.now());
//		Thread.sleep(60000);
//		System.out.println(systemClock.now());

	}

}
