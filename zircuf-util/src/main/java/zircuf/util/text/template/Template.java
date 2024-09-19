package zircuf.util.text.template;

public class Template {

	public static ArrayTemplate ofArray(String template) {
		return (ArrayTemplate) TextTemplate.compile(new ArrayTemplate(), template);
	}

	public static MapTemplate ofMap(String template) {
		return (MapTemplate) TextTemplate.compile(new MapTemplate(), template);
	}

}
