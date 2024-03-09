package zircuf.util.text.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapTemplate implements TemplateCore<Map<String, String>, MapTemplateItem> {

	@Override
	public void addText(String text) {
		itemList.add(new MapTemplateItem(text, null));
	}

	@Override
	public void addParts(String defaultText, String key) {
		itemList.add(new MapTemplateItem(defaultText, key));
	}

	List<MapTemplateItem> itemList = new ArrayList<>();

	@Override
	public List<MapTemplateItem> itemList() {
		return itemList;
	}

};

record MapTemplateItem(String text, String key) implements TemplateItem<Map<String, String>> {
	@Override
	public StringBuilder inject(StringBuilder sb, Map<String, String> data) {
		if (key == null) {
			sb.append(text());
		} else {
			String value = data.get(key);
			if (value != null) {
				sb.append(value);
			} else {
				sb.append(text());
			}
		}
		return sb;
	}
};
