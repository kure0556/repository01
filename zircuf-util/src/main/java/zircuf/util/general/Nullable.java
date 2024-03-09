package zircuf.util.general;

/**
 * 入力の値がnullであった場合に、nullを返却する
 */
public class Nullable {

	public static final String substring(String str, int beginIndex) {
		if (str == null)
			return null;
		return str.substring(beginIndex);
	}

	public static final int intValue(Integer integer) {
		return intValue(integer, 0);
	}

	public static final int intValue(Integer integer, int defaultValue) {
		if (integer == null)
			return defaultValue;
		return integer.intValue();
	}
}
