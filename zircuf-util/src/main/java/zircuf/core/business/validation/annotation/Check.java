package zircuf.core.business.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {

	CType[] value();

	/**
	 * チェックタイプ
	 */
	public enum CType implements Predicate<Object> {
		// 汎用チェックロジック
		/** 必須チェック（非nullチェック） */
		REQUIERD(Objects::nonNull),
		/** 非空文字チェック */
		NON_BLANK(CheckLogic::nonBlank),
		/** 非空リストチェック（1件以上リストのチェック） */
		NOT_EMPTY_COLLECTION(CheckLogic::notEmptyCollection),
		;

		private Predicate<Object> predicate;

		private CType(Predicate<Object> predicate) {
			this.predicate = predicate;
		}

		@Override
		public boolean test(Object fieldObject) {
			return predicate.test(fieldObject);
		}

	}

	// ----------------------
	// 汎用チェックロジック
	// ----------------------

	public static class CheckLogic {

		public static boolean nonBlank(Object input) {
			if (Objects.isNull(input)) {
				return false;
			}
			if (input instanceof String str) {
				return !str.isBlank();
			}
			throw new UnsupportedOperationException("nonBlankはString型のみサポートされます");
		}

		public static boolean notEmptyCollection(Object input) {
			if (Objects.isNull(input)) {
				return false;
			}
			if (input instanceof Collection collection) {
				return !collection.isEmpty();
			}
			throw new UnsupportedOperationException("notEmptyCollectionはCollection型のみサポートされます");
		}

	}
}
