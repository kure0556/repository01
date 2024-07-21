package zircuf.core.business.validate;

import java.lang.reflect.Field;
import java.util.Objects;

import zircuf.core.business.validate.annotation.Deep;
import zircuf.core.business.validate.annotation.Edit;
import zircuf.core.business.validate.annotation.Edit.EType;

public class Editor<T> extends AbstractValidator<T> {

	public static <S> Editor<S> of(Class<S> clazz) {
		return new Editor<S>(clazz);
	}

	public Editor(Class<T> clazz) {
		super(clazz);
	}

	private Editor(Class<T> clazz, int lv, String parentFieldName, FieldType fieldType) {
		super(clazz, lv, parentFieldName, fieldType);
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
				boolean isEdited = false;
				for (EType eType : editAnno.value()) {
					try {
						Object editedVal = eType.edit(fieldVal);
						if (!Objects.equals(fieldVal, editedVal)) {
							putLog(field, fieldVal, eType, editedVal.toString());
							fieldVal = editedVal;
							isEdited = true;
						}
					} catch (Exception e) {
						throw new RuntimeException("編集処理例外発生 : " + eType + " : " + toString(field, fieldVal), e);
					}
				}
				if (isEdited) {
					field.set(object, fieldVal);
				}
			}

			// 拡張編集ロジック
			// TODO:実装予定

			// Dto、Collectionの走査
			Deep deepAnno = field.getAnnotation(Deep.class);
			if (Objects.nonNull(deepAnno) && Objects.nonNull(fieldVal)) {
				deep(field, fieldVal);
			}

		} catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException e) {
			throw new RuntimeException("編集処理例外発生 : " + toString(field), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <C> boolean deepInit(Class<C> class1, Object fieldVal, FieldType fieldType2, String nextFieldName) {
		new Editor<C>(class1, lv + 1, nextFieldName, fieldType).edit((C) fieldVal);
		return true;
	}

}
