package zircuf.core.business.validate;

public class Validator {

	@SuppressWarnings("unchecked")
	static <T> void checkAndEdit(T object) {
		boolean check1 = Checker.of((Class<T>) object.getClass()).check(object);
		System.out.println("check1=" + check1);

		Editor.of((Class<T>) object.getClass()).edit(object);
	}
}
