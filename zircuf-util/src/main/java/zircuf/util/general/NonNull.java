package zircuf.util.general;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * nullを許容しないフィールドに対するデフォルト値を返却する
 */
public class NonNull {

	public static final String of(String str) {
		return Objects.requireNonNullElse(str, "");
	}

	public static final <T> List<T> of(List<T> list) {
		return Objects.requireNonNullElse(list, Collections.emptyList());
	}

	public static final Boolean ofBoolean(String str) {
		return (str != null) ? Boolean.valueOf(str) : Boolean.FALSE;
	}

	@Deprecated
	public static final int ofInt(String str) {
		return (str != null) ? Integer.parseInt(str) : 0;
	}

}
