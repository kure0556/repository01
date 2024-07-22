package zircuf.core.business.validation;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Predicate;

import zircuf.core.business.validation.annotation.Check;
import zircuf.core.business.validation.annotation.CheckLogic;
import zircuf.core.business.validation.annotation.Deep;
import zircuf.core.business.validation.annotation.Check.CType;

public class Checker<T> extends AbstractValidator<T> {

	public static <S> Checker<S> of(Class<S> clazz) {
		return new Checker<S>(clazz);
	}

	public Checker(Class<T> clazz) {
		super(clazz);
	}

	private Checker(Class<T> clazz, int lv, String parentFieldName, FieldType fieldType) {
		super(clazz, lv, parentFieldName, fieldType);
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
						boolean ok = cType.test(fieldVal);
						result = result && ok;
						if (!ok) {
							putLog(field, fieldVal, cType, String.valueOf(ok));
							break;
						}
					} catch (Exception e) {
						throw new RuntimeException("チェック処理例外発生 : " + cType + " : " + toString(field, fieldVal), e);
					}
				}
			}

			// 拡張チェックロジック
			CheckLogic checkLogicAnno = field.getAnnotation(CheckLogic.class);
			if (Objects.nonNull(checkLogicAnno)) {
				for (Class<? extends Predicate<?>> checkLogicClass : checkLogicAnno.value()) {
					try {
						@SuppressWarnings("unchecked")
						Predicate<Object> check = (Predicate<Object>) checkLogicClass.getDeclaredConstructor()
								.newInstance();
						boolean ok = check.test(fieldVal);
						result = result && ok;
						if (!ok) {
							putLog(field, fieldVal, checkLogicClass, String.valueOf(ok));
							break;
						}
					} catch (Exception e) {
						throw new RuntimeException(
								"チェック処理例外発生 : " + checkLogicClass + " : " + toString(field, fieldVal), e);
					}
				}
			}

			// Dto、Collectionの走査
			Deep deepAnno = field.getAnnotation(Deep.class);
			if (Objects.nonNull(deepAnno) && Objects.nonNull(fieldVal)) {
				boolean ok = deep(field, fieldVal);
				result = result && ok;
			}

		} catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException("チェック処理例外発生 : " + toString(field), e);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <C> boolean deepInit(Class<C> class1, Object fieldVal, FieldType fieldType, String nextFieldName) {
		return new Checker<C>(class1, lv + 1, nextFieldName, fieldType).check((C) fieldVal);
	}

}
