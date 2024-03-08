package zircuf.uti.performance;

public abstract class Performance {

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
