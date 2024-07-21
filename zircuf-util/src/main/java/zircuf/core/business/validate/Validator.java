package zircuf.core.business.validate;

public class Validator {

	@SuppressWarnings("unchecked")
	static <T> boolean checkAndEdit(T object) {
		boolean check1 = Checker.of((Class<T>) object.getClass()).check(object);
		if (check1 == false)
			return false;
		Editor.of((Class<T>) object.getClass()).edit(object);
		return true;
	}

}
