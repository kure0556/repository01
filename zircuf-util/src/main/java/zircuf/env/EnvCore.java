package zircuf.env;

public interface EnvCore {

	public static boolean isWinOS() {
		return System.getProperty("file.separator").equals("\\");
	}

}
