package zircuf.util.text.template;

public interface TextTemplate<D> {

	public String inject(D data);

	public String inject(StringBuilder sb, D data);

	public StringBuilder append(StringBuilder sb, D data);

	default public String inject2(String... data) {
		return null;
	};

	default public StringBuilder append2(StringBuilder sb, String... data) {
		return null;
	};
}
