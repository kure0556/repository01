package zircuf.core.business.validate;

import java.lang.reflect.Field;
import java.util.Objects;

import zircuf.core.business.validate.annotation.Edit;
import zircuf.core.business.validate.annotation.Edit.EType;

public class Editor<T> extends AbstractValidator<T> {

	public static <U> Editor<U> of(Class<U> clazz) {
		return new Editor<U>(clazz);
	}

	public Editor(Class<T> clazz) {
		super(clazz);
	}

	public void edit(final T object) {
		for (Field field : fields) {
			editFields(field, object);
		}
	}

	private void editFields(Field field, final T object) {
		try {
			// フィールド値の取得
			Object fieldVal = field.get(object);

			// 汎用編集ロジック
			Edit editAnno = field.getAnnotation(Edit.class);
			if (Objects.nonNull(editAnno)) {
				for (EType eType : editAnno.value()) {
					try {
						Object preVal = fieldVal;
						fieldVal = eType.edit(fieldVal);
						System.out.println(toString(field, preVal) + " : " + eType + " -> " + fieldVal);
					} catch (Exception e) {
						throw new RuntimeException("編集処理例外発生 : " + eType + " : " + toString(field, fieldVal), e);
					}
				}
				field.set(object, fieldVal);
			}

			// 拡張編集ロジック
			// TODO:実装予定

		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("編集処理例外発生 : " + toString(field), e);
		}
	}

}
