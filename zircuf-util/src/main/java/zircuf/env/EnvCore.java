package zircuf.env;

public interface EnvCore {

	public static boolean isWinOS() {
		return System.getProperty("file.separator").equals("\\");
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
