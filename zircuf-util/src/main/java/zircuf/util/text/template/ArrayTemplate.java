package zircuf.util.text.template;

import java.util.ArrayList;
import java.util.List;

public class ArrayTemplate implements TemplateCore<String[], ArrayTemplateItem> {

	@Override
	public void addText(String text) {
		itemList.add(new ArrayTemplateItem(text, null, -1));
	}

	@Override
	public void addParts(String defaultText, String key) {
		itemList.add(new ArrayTemplateItem(defaultText, key, Integer.parseInt(key)));
	}

	List<ArrayTemplateItem> itemList = new ArrayList<>();

	@Override
	public List<ArrayTemplateItem> itemList() {
		return itemList;
	}

}

record ArrayTemplateItem(String text, String key, int keyInt) implements TemplateItem<String[]> {
	@Override
	public StringBuilder inject(StringBuilder sb, String[] data) {
		if (keyInt == -1) {
			sb.append(text());
		} else {
			if (keyInt < data.length && data[keyInt] != null) {
				sb.append(data[keyInt]);
			} else {
				sb.append(text());
			}
		}
		return sb;
	}
};
