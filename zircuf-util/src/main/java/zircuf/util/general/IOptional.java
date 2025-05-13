package zircuf.util.general;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Optional化インターフェース（提供されるデータが有効か無効かをisPresentで表現）
 * @param <T>
 */
public interface IOptional<T> extends Supplier<T> {

	public boolean isPresent();

	@Override
	public T get();

	/**
	 * If a value is present, returns the value, otherwise returns
	 * {@code other}.
	 *
	 * @param other the value to be returned, if no value is present.
	 *        May be {@code null}.
	 * @return the value, if present, otherwise {@code other}
	 */
	public default T orElse(T other) {
		return isPresent() ? get() : other;
	}

	/**
	 * If a value is present, returns the value, otherwise returns the result
	 * produced by the supplying function.
	 *
	 * @param supplier the supplying function that produces a value to be returned
	 * @return the value, if present, otherwise the result produced by the
	 *         supplying function
	 * @throws NullPointerException if no value is present and the supplying
	 *         function is {@code null}
	 */
	public default T orElseGet(Supplier<? extends T> supplier) {
		return isPresent() ? get() : supplier.get();
	}

	/**
	 * If a value is present, performs the given action with the value,
	 * otherwise does nothing.
	 *
	 * @param action the action to be performed, if a value is present
	 * @throws NullPointerException if value is present and the given action is
	 *         {@code null}
	 */
	public default void ifPresent(Consumer<? super T> action) {
		if (isPresent()) {
			action.accept(get());
		}
	}

	/**
	 * If a value is present, performs the given action with the value,
	 * otherwise performs the given empty-based action.
	 *
	 * @param action the action to be performed, if a value is present
	 * @param emptyAction the empty-based action to be performed, if no value is
	 *        present
	 * @throws NullPointerException if a value is present and the given action
	 *         is {@code null}, or no value is present and the given empty-based
	 *         action is {@code null}.
	 */
	public default void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
		if (isPresent()) {
			action.accept(get());
		} else {
			emptyAction.run();
		}
	}

}
