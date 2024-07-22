package zircuf.core.business.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Edit {

	EType[] value();

	public enum EType {
		// 汎用編集ロジック
		/** トリム */
		TRIM(EditLogic::trim),
		/** nullの項目を空のリストに変換 */
		NON_NULL_LIST(EditLogic::nonNullList),
		;

		private Function<Object, Object> function;

		@SuppressWarnings("unchecked")
		<T> EType(Function<T, T> function) {
			this.function = (Function<Object, Object>) function;
		}

		public Object edit(Object fieldObject) {
			return function.apply(fieldObject);
		}

	}

	// ----------------------
	// 汎用編集ロジック
	// ----------------------

	public static class EditLogic {

		public static String trim(String input) {
			if (input.startsWith(" ") || input.endsWith(" ")) {
				return input.trim();
			}
			return input;
		}

		public static List<?> nonNullList(List<?> input) {
			if (Objects.isNull(input)) {
				return Collections.emptyList();
			}
			return input;
		}

	}
}
