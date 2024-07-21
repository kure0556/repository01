package zircuf.core.business.validate;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public abstract class AbstractValidator<T> {

	protected final Class<T> clazz;
	protected final Field[] fields;

	protected int lv = 0;
	protected String parentFieldName = "";
	protected FieldType fieldType = FieldType.DTO;

	protected AbstractValidator(Class<T> clazz) {
		super();
		this.clazz = clazz;
		fields = this.clazz.getDeclaredFields();
		// フィールドのアクセス許可
		for (Field field : fields) {
			field.trySetAccessible();
		}
	}

	public AbstractValidator(Class<T> clazz, int lv, String parentFieldName, FieldType fieldType) {
		this(clazz);
		this.lv = lv;
		this.parentFieldName = parentFieldName;
		this.fieldType = fieldType;
	}

	protected String toString(Field field) {
		String name = field.getName();
		Type genericType = field.getGenericType();
		return (parentFieldName.isBlank() ? "" : parentFieldName + ".") + name + " (" + genericType + ")";
	}

	protected String toString(Field field, Object fieldVal) {
		String name = field.getName();
		Type genericType = field.getGenericType();
		return (parentFieldName.isBlank() ? "" : parentFieldName + ".") + name + " (" + genericType + ") = " + fieldVal;
	}

	// -----------------------
	// Dto、Collectionの走査
	// -----------------------
	protected <C> boolean deep(Field field, Object fieldVal) throws ClassNotFoundException {
		boolean result = true;
		@SuppressWarnings("unchecked")
		Class<C> class1 = (Class<C>) field.getType();
		if (fieldVal instanceof Map<?, ?> map) {
			// Map
			boolean ok = deepCollection(field, map.values());
			result = result && ok;
		} else if (fieldVal instanceof Collection<?> collection) {
			// Collection
			boolean ok = deepCollection(field, collection);
			result = result && ok;
		} else {
			// Dto
			boolean ok = deepChild(class1, field, fieldVal, FieldType.DTO, -1);
			result = result && ok;
		}
		return result;
	}

	private boolean deepCollection(Field field, Collection<?> collection) throws ClassNotFoundException {
		boolean result = true;
		if (!collection.isEmpty()) {
			ParameterizedType type = (ParameterizedType)field.getGenericType();
			Type childType = type.getActualTypeArguments()[0];
			Class<?> class2 = Class.forName(childType.getTypeName());
			int idx = 0;
			for (Object childObject : collection) {
				boolean ok = deepChild(class2, field, childObject, FieldType.COLLECTION, idx);
				result = result && ok;
				idx++;
			}
		}
		return result;
	}

	private <C> boolean deepChild(Class<C> class1, Field field, Object fieldVal, FieldType fieldType, int idx) {
		String nextFieldName = field.getName() + (idx >= 0 ? "[" + idx + "]" : "");
		if (!parentFieldName.isBlank())
			nextFieldName = parentFieldName + "." + nextFieldName;
		return deepInit(class1, fieldVal, fieldType, nextFieldName);
	}

	protected abstract <C> boolean deepInit(Class<C> class1, Object fieldVal, FieldType fieldType2, String nextFieldName);

	// -----------------------
	// Dto、Collectionの走査
	// -----------------------

	protected void putLog(Field field, Object fieldVal, Enum<?> operation, String result) {
		System.out.println(toString(field, fieldVal) + " : " + operation + " -> " + result);
	}

	protected void putLog(Field field, Object fieldVal, Class<?> operation, String result) {
		System.out.println(toString(field, fieldVal) + " : " + operation + " -> " + result);
	}

	protected enum FieldType {
		DTO, COLLECTION;
	}
}
