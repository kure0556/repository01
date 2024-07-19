package zircuf.env;

import org.junit.jupiter.api.Test;

class SystemClockTest {

	@Test
	void test() throws InterruptedException {
		SystemClock systemClock = new SystemClock();
		System.out.println(systemClock.now());

		System.out.println();

		systemClock.setOffset("-P5D");

		System.out.println(systemClock.now());
		Thread.sleep(1000);
		System.out.println(systemClock.now());

		System.out.println();

		systemClock.setFixed("2024-01-01T16:00:00");

		System.out.println(systemClock.now());
		Thread.sleep(1000);
		System.out.println(systemClock.now());

		System.out.println();

		systemClock = new SystemClock();
		systemClock.setOrigin(2024, 1, 1, 16, 0);

		System.out.println(systemClock.now());
		Thread.sleep(60000);
		System.out.println(systemClock.now());
		Thread.sleep(60000);
		System.out.println(systemClock.now());

	}

}
