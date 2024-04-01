package zircuf.env;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import zircuf.util.datetime.DT;

public class SystemClock {

	private Clock innerClock;

	public SystemClock() {
		this.innerClock = Clock.system(DT.JST);
	}

	public void setOrigin(String fixedDateTimeText) {
		setOrigin(LocalDateTime.parse(fixedDateTimeText));
	}

	public void setOrigin(int year, int month, int day, int hour, int minute) {
		setOrigin(LocalDateTime.of(year, month, day, hour, minute));
	}

	public void setOrigin(int year, int month, int day, int hour, int minute, int second) {
		setOrigin(LocalDateTime.of(year, month, day, hour, minute, second));
	}

	public void setOrigin(LocalDateTime fromLdt) {
		Duration between = Duration.between(fromLdt.toInstant(DT.offsetJST), Clock.system(DT.JST).instant());
		setOffset(between);
	}

	public void setOffsetDays(long days) {
		setOffset(Duration.ofDays(days));
	}

	public void setOffset(String durationText) {
		setOffset(Duration.parse(durationText));
	}

	public void setOffset(Duration duration) {
		this.innerClock = Clock.offset(this.innerClock, duration);
	}

	public void setFixed(String fixedDateTimeText) {
		setFixed(LocalDateTime.parse(fixedDateTimeText));
	}

	public void setFixed(int year, int month, int day, int hour, int minute) {
		setFixed(LocalDateTime.of(year, month, day, hour, minute));
	}

	public void setFixed(int year, int month, int day, int hour, int minute, int second) {
		setFixed(LocalDateTime.of(year, month, day, hour, minute, second));
	}

	public void setFixed(LocalDateTime ldt) {
		this.innerClock = Clock.fixed(ldt.toInstant(ZoneOffset.ofHours(9)), DT.JST);
	}

	public LocalDateTime now() {
		return nowNanos().truncatedTo(ChronoUnit.SECONDS);
	}

	public ZonedDateTime nowJST() {
		return nowNanosJST().truncatedTo(ChronoUnit.SECONDS);
	}

	public LocalDateTime nowNanos() {
		return LocalDateTime.ofInstant(innerClock.instant(), DT.JST);
	}

	public ZonedDateTime nowNanosJST() {
		return ZonedDateTime.ofInstant(innerClock.instant(), DT.JST);
	}

}
