package zircuf.util.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

import zircuf.util.performance.Bench;

class DTTest {

	@Test
	void test() {
		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(DT.Zone.UTC);

		System.out.println(DT.Format.iso(ldt));
		System.out.println(DT.Format.iso(zdt));
		System.out.println(DT.Format.isoUtc(udt));

		ldt = DT.Trim.millis(ldt);
		zdt = DT.Trim.millis(zdt);
		udt = DT.Trim.millis(udt);

		System.out.println(DT.Format.iso(ldt));
		System.out.println(DT.Format.iso(zdt));
		System.out.println(DT.Format.isoUtc(udt));

		ldt = DT.Trim.seconds(ldt);
		zdt = DT.Trim.seconds(zdt);
		udt = DT.Trim.seconds(udt);

		System.out.println(DT.Format.iso(ldt));
		System.out.println(DT.Format.iso(zdt));
		System.out.println(DT.Format.isoUtc(udt));

	}

	@Test
	void test2() {
		// Dateは内部的にマシンのタイムゾーンで作られる。
		// DateTimeに変換すると同じ日時になることを確認。
		Date date = new Date();
		System.out.println(DT.Convert.fromDate(date));			// LocalDateTime
		System.out.println(DT.Convert.fromDateJST(date));		// ZonedDateTime
		System.out.println(DT.Convert.fromEpochMillis(date.getTime()));		// LocalDateTime
		System.out.println(DT.Convert.fromEpochMillisJST(date.getTime()));	// ZonedDateTime
		System.out.println();

		// epochは内部的にUTCの絶対値に変換される
		// それぞれDateTime→Epoch→DateTimeで元の時刻に戻ることを確認。
		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime udt = ZonedDateTime.now(DT.Zone.UTC);
		System.out.println(DT.Convert.fromEpochMillis(DT.Convert.toEpochSeconds(ldt)));
		System.out.println(DT.Convert.fromEpochMillis(DT.Convert.toEpochSeconds(zdt)));
		System.out.println(DT.Convert.fromEpochMillis(DT.Convert.toEpochSeconds(udt)));
		System.out.println(DT.Convert.fromEpochMillisJST(DT.Convert.toEpochSeconds(ldt)));
		System.out.println(DT.Convert.fromEpochMillisJST(DT.Convert.toEpochSeconds(zdt)));
		System.out.println(DT.Convert.fromEpochMillisJST(DT.Convert.toEpochSeconds(udt)));
		System.out.println();

		// 性能差の確認
		Bench.measure(() -> {
			DT.Trim.millis(LocalDateTime.now());
		}, 10_000, 100_000).result(System.out::println);
		Bench.measure(() -> {
			LocalDateTime.now().withNano(0);
		}, 10_000, 100_000).result(System.out::println);
		System.out.println();

		// 
		LocalDateTime asLocalDateTime = DTF.DATE_TIME.of("19870605235911");
		System.out.println(asLocalDateTime);
		ZonedDateTime asZonedDateTime = DTF.ISO_DATE_TIME_ZONED.ofZoned("1987-06-05T23:59:11Z");
		System.out.println(asZonedDateTime);
		System.out.println(DT.Parse.fromIso("1987-06-05T23:59:11"));
		System.out.println(DT.Parse.fromIsoZoned("1987-06-05T23:59:11Z"));
		System.out.println(DT.Parse.fromIsoZoned("1987-06-05T23:59:11+09:00"));
		System.out.println();

		System.out.println(DT.Parse.fromFlatDateTime("19870605235911"));
	}

//	@Test
//	void test2() {
//		Date date = new Date();
//		System.out.println(DT.of(date));		// LocalDateTime
//		System.out.println(DT.ofJST(date));		// ZonedDateTime
//		System.out.println(DT.of(date.getTime()));		// LocalDateTime
//		System.out.println(DT.ofJST(date.getTime()));	// ZonedDateTime
//		System.out.println();
//
//		LocalDateTime ldt = LocalDateTime.now();
//		ZonedDateTime zdt = ZonedDateTime.now();
//		ZonedDateTime udt = ZonedDateTime.now(DT.UTC);
//
//		System.out.println(DT.of(DT.asEpochTimeMillis(ldt)));
//		System.out.println(DT.of(DT.asEpochTimeMillis(zdt)));
//		System.out.println(DT.of(DT.asEpochTimeMillis(udt)));
//		System.out.println(DT.ofJST(DT.asEpochTimeMillis(ldt)));
//		System.out.println(DT.ofJST(DT.asEpochTimeMillis(zdt)));
//		System.out.println(DT.ofJST(DT.asEpochTimeMillis(udt)));
//		System.out.println();
//
//		Performance.of(() -> {
//			DT.trimMillis(LocalDateTime.now());
//		});
//
//		Performance.of(() -> {
//			LocalDateTime.now().withNano(0);
//		});
//
//		LocalDateTime asLocalDateTime = DTF.DATE_TIME.of("19870312235911");
//		System.out.println(asLocalDateTime);
//
//		ZonedDateTime asZonedDateTime = DTF.ISO_DATE_TIME_ZONED.ofZoned("1987-03-12T23:59:11Z");
//		System.out.println(asZonedDateTime);
//		System.out.println(DT.of("1987-03-12T23:59:11"));
//		System.out.println(DT.ofZoned("1987-03-12T23:59:11Z"));
//		System.out.println(DT.ofZoned("1987-03-12T23:59:11+09:00"));
//		System.out.println();
//
//		System.out.println(DT.Text.asIsoFromFlat("19870312235911"));
//	}

}
