package zircuf.core.business.validate;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Predicate;

import zircuf.core.business.validate.annotation.Check;
import zircuf.core.business.validate.annotation.Check.CType;
import zircuf.core.business.validate.annotation.CheckLogic;

public class Checker<T> extends AbstractValidator<T> {

	public static <U> Checker<U> of(Class<U> clazz) {
		return new Checker<U>(clazz);
	}

	public Checker(Class<T> clazz) {
		super(clazz);
	}

	public boolean check(final T object) {
		boolean result = true;
		for (Field field : fields) {
			boolean ok = checkFields(field, object);
			result = result && ok;
		}
		return result;
	}

	private boolean checkFields(Field field, final T object) {
		boolean result = true;
		try {
			// フィールド値の取得
			Object fieldVal = field.get(object);

			// 汎用チェックロジック
			Check checkAnno = field.getAnnotation(Check.class);
			if (Objects.nonNull(checkAnno)) {
				for (CType cType : checkAnno.value()) {
					try {
						boolean ok = cType.isOk(fieldVal);
						result = result && ok;
						System.out.println(toString(field, fieldVal) + " : " + cType + "=" + ok);
					} catch (Exception e) {
						throw new RuntimeException("チェック処理例外発生 : " + cType + " : " + toString(field, fieldVal), e);
					}
				}
			}

			// 拡張チェックロジック
			CheckLogic checkLogicAnno = field.getAnnotation(CheckLogic.class);
			if (Objects.nonNull(checkLogicAnno)) {
				for (Class<? extends Predicate<?>> class1 : checkLogicAnno.value()) {
					try {
						@SuppressWarnings("unchecked")
						Predicate<Object> check = (Predicate<Object>) class1.getDeclaredConstructor().newInstance();
						boolean ok = check.test(fieldVal);
						result = result && ok;
						System.out.println(toString(field, fieldVal) + " : " + class1 + "=" + ok);
					} catch (Exception e) {
						throw new RuntimeException("チェック処理例外発生 : " + class1 + " : " + toString(field, fieldVal), e);
					}
				}
			}

		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("チェック処理例外発生 : " + toString(field), e);
		}
		return result;
	}

}
