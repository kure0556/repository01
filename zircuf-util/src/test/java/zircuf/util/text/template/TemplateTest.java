package zircuf.util.text.template;

import java.util.Map;

import org.junit.jupiter.api.Test;

import zircuf.util.performance.Performance;

class TemplateTest {

	@Test
	void test() {

		String template = """
				{
				  aaa:"${0:}",
				  bbb:"${1:}",
				  ccc:"${0}${1}",
				  ddd:"${2:指定なし}"
				}
				""";
		System.out.println(Template.ofArray(template).inject2("あああ", null, "ううう"));

		TextTemplate<String[]> compile = Template.ofArray(template);

		Performance.of(s -> {
			compile.inject2("あああ");
		});

		StringBuilder sb = new StringBuilder();
		Performance.of(s -> {
			sb.setLength(0);
			compile.append(sb, new String[] { "あああ", "いいい", "ううう" });
		});

		System.out.println(sb.toString());

		TextTemplate<Map<String, String>> compile2 = Template.ofMap(template);

		Performance.of(s -> {
			sb.setLength(0);
			compile2.append(sb, Map.of("1", "あああ", "2", "いいい"));
		});

		System.out.println(sb.toString());

	}

	@Test
	void test2() {

		Performance.of(s -> {
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
