package zircuf.core.business.validation;

public class Validator {

	@SuppressWarnings("unchecked")
	public static <T> boolean checkAndEdit(T object) {
		boolean checkOk = Checker.of((Class<T>) object.getClass()).check(object);
		if (checkOk) {
			Editor.of((Class<T>) object.getClass()).edit(object);
			return true;
		} else {
			return false;
		}
	}

}
