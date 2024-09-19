package zircuf.util.text.template;

import java.util.List;

interface TextTemplate<D, E extends TemplateItem<D>> {

	public List<E> itemList();

	public void addText(String text);

	public void addParts(String defaultValue, String key);

	default public String inject(D data) {
		StringBuilder sb = new StringBuilder();
		itemList().forEach(item -> item.inject(sb, data));
		return sb.toString();
	}

	default public String inject(StringBuilder sb, D data) {
		sb.setLength(0);
		itemList().forEach(item -> item.inject(sb, data));
		return sb.toString();
	}

	default public StringBuilder append(StringBuilder sb, D data) {
		itemList().forEach(item -> item.inject(sb, data));
		return sb;
	}

}

interface TemplateItem<T> {
	public StringBuilder inject(StringBuilder sb, T data);
}
