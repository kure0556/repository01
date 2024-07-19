package zircuf.core.business.validate;

import java.util.function.Predicate;

public class MyCheckLogic implements Predicate<String> {

	public MyCheckLogic() {
		super();
	}

	@Override
	public boolean test(String str) {
		return !str.trim().isBlank();
	}

}
