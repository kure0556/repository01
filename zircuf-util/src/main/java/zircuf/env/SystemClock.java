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

	/**
	 * 起点日時を設定
	 */
	public void setOrigin(String fixedDateTimeText) {
		setOrigin(LocalDateTime.parse(fixedDateTimeText));
	}

	/**
	 * 起点日時を設定
	 */
	public void setOrigin(int year, int month, int day, int hour, int minute) {
		setOrigin(LocalDateTime.of(year, month, day, hour, minute));
	}

	/**
	 * 起点日時を設定
	 */
	public void setOrigin(int year, int month, int day, int hour, int minute, int second) {
		setOrigin(LocalDateTime.of(year, month, day, hour, minute, second));
	}

	/**
	 * 起点日時を設定
	 */
	public void setOrigin(LocalDateTime fromLdt) {
		Duration between = Duration.between(this.innerClock.instant(), fromLdt.toInstant(DT.offsetJST));
		setOffset(between);
	}

	/**
	 * 日付のオフセットを設定
	 */
	public void setOffsetDays(long days) {
		setOffset(Duration.ofDays(days));
	}

	/**
	 * 日時のオフセットを設定
	 */
	public void setOffset(String durationText) {
		setOffset(Duration.parse(durationText));
	}

	/**
	 * 日時のオフセットを設定
	 */
	public void setOffset(Duration duration) {
		this.innerClock = Clock.offset(this.innerClock, duration);
	}

	/**
	 * 固定日時を設定
	 */
	public void setFixed(String fixedDateTimeText) {
		setFixed(LocalDateTime.parse(fixedDateTimeText));
	}

	/**
	 * 固定日時を設定
	 */
	public void setFixed(int year, int month, int day, int hour, int minute) {
		setFixed(LocalDateTime.of(year, month, day, hour, minute));
	}

	/**
	 * 固定日時を設定
	 */
	public void setFixed(int year, int month, int day, int hour, int minute, int second) {
		setFixed(LocalDateTime.of(year, month, day, hour, minute, second));
	}

	/**
	 * 固定日時を設定
	 */
	public void setFixed(LocalDateTime ldt) {
		this.innerClock = Clock.fixed(ldt.toInstant(ZoneOffset.ofHours(9)), DT.JST);
	}

	/**
	 * @return LocalDateTime（秒未満をトリミング）
	 */
	public LocalDateTime now() {
		return nowNanos().truncatedTo(ChronoUnit.SECONDS);
	}

	/**
	 * @return ZonedDateTime（秒未満をトリミング）
	 */
	public ZonedDateTime nowJST() {
		return nowNanosJST().truncatedTo(ChronoUnit.SECONDS);
	}

	/**
	 * @return LocalDateTime
	 */
	public LocalDateTime nowNanos() {
		return LocalDateTime.ofInstant(innerClock.instant(), DT.JST);
	}

	/**
	 * @return ZonedDateTime
	 */
	public ZonedDateTime nowNanosJST() {
		return ZonedDateTime.ofInstant(innerClock.instant(), DT.JST);
	}

}
