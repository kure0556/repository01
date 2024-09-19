package zircuf.util.text.template;

import java.util.Map;

import org.junit.jupiter.api.Test;

import zircuf.util.performance.Performance;

class TemplateTest {

	@Test
	void test1() {

		String template = """
				{
				  aaa:"${0:}",
				  bbb:"${1:}",
				  ccc:"${0}${1}",
				  ddd:"${2:指定なし}"
				}
				""";
		System.out.println(Template.ofArray(template).injectArgs("あああ", null, "ううう"));

		ArrayTemplate arrayTemplate = Template.ofArray(template);

		Performance.of(() -> {
			arrayTemplate.injectArgs("あああ");
		});

		StringBuilder sb = new StringBuilder();
		Performance.of(() -> {
			sb.setLength(0);
			arrayTemplate.append(sb, new String[] { "あああ", "いいい", "ううう" });
		});
		System.out.println(sb.toString());

	}

	@Test
	void test2() {
		MapTemplate mapTemplate = Template.ofMap("""
				{
				  aaa:"${key0:}",
				  bbb:"${key1:}",
				  ccc:"${key0}${key1}",
				  ddd:"${key2:指定なし}"
				}
				""");

		StringBuilder sb = new StringBuilder();
		Performance.of(() -> {
			sb.setLength(0);
			mapTemplate.append(sb, Map.of("key1", "あああ", "key2", "いいい"));
		});
		System.out.println(sb.toString());
	}

	@Test
	void test3() {
		Performance.of(() -> {
			@SuppressWarnings("unused")
			String template = """
					{
					  aaa:"%s",
					  bbb:"%s",
					  ccc:"%s",
					  ddd:"%s"
					}
					""".formatted("あああ", "いいい", "ううう", "えええ");
		});
	}
}
