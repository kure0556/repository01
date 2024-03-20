package zircuf.util.performance;

import java.util.function.Consumer;

public abstract class Performance {

	public static void of(Consumer<String> target) {
		new Performance() {
			@Override
			protected void proc() {
				target.accept(null);
			}
		}.keisoku();
	}
	

	int count = 1000000;

	public void keisoku() {
		long nanoTime = System.nanoTime();
		for (int i = 0; i < count; i++) {
			proc();
		}
		long time = System.nanoTime() - nanoTime;
		double millis = (double) time / 1000000d;
		System.out.println(millis);
	}

	protected abstract void proc();

}
