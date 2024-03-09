package zircuf.util.general;

import java.util.Collections;
import java.util.List;

/**
 * nullを許容しないフィールドに対するデフォルト値を返却する
 */
public class NonNull {

	public static final String of(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static final <T> List<T> of(List<T> list) {
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public static final Boolean ofBoolean(String str) {
		if (str == null) {
			return Boolean.FALSE;
		}
		return Boolean.valueOf(str);
	}

}
