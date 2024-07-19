package zircuf.core.business.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public abstract class AbstractValidator<T> {

	protected final Class<T> clazz;
	protected final Field[] fields;

	protected AbstractValidator(Class<T> clazz) {
		super();
		this.clazz = clazz;
		fields = this.clazz.getDeclaredFields();
		// フィールドのアクセス許可
		for (Field field : fields) {
			field.trySetAccessible();
		}
	}

	protected String toString(Field field) {
		String name = field.getName();
		Class<?> type = field.getType();
		Type genericType = field.getGenericType();
		return name + "(" + type + ")";
	}

	protected String toString(Field field, Object fieldVal) {
		String name = field.getName();
		Class<?> type = field.getType();
		Type genericType = field.getGenericType();
		return name + "(" + type + ") : " + fieldVal;
	}
}
