package zircuf.util.performance;

public abstract class Performance {

	public static void of(Runnable target) {
		double millis = new Performance() {
			@Override
			protected void proc() {
				target.run();
			}
		}.millis();
		System.out.println(millis);
	}

	int count = 1000000;

	double millis() {
		long nanoTime = System.nanoTime();
		for (int i = 0; i < count; i++) {
			proc();
		}
		return (double) (System.nanoTime() - nanoTime) / 1000000d;
	}

	protected abstract void proc();

}
