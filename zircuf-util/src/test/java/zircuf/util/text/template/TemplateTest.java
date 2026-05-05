package zircuf.util.text.template;

import java.util.Map;

import org.junit.jupiter.api.Test;

import zircuf.util.performance.Bench;

class TemplateTest {

	@Test
	void test1() {
		String result = Template.ofArray("""
				{
				  aaa:"${0:}",
				  bbb:"${1:}",
				  ccc:"${0}${1}",
				  ddd:"${2:指定なし}"
				}
				""").injectArgs("あああ", null, "ううう");
		System.out.println(result);
	}

	@Test
	void test2() {
		ArrayTemplate arrayTemplate = Template.ofArray("""
				{
				  aaa:"${0:}",
				  bbb:"${1:}",
				  ccc:"${0}${1}",
				  ddd:"${2:指定なし}"
				}
				""");

		Bench.measure(() -> {
			arrayTemplate.injectArgs("あああ");
		}, 10_000, 100_000).result(System.out::println);

		StringBuilder sb = new StringBuilder();
		Bench.measure(() -> {
			sb.setLength(0);
			arrayTemplate.append(sb, new String[] { "あああ", "いいい", "ううう" });
		}, 10_000, 100_000).result(System.out::println);
		System.out.println(sb.toString());

	}

	@Test
	void test3() {
		MapTemplate mapTemplate = Template.ofMap("""
				{
				  aaa:"${key0:}",
				  bbb:"${key1:}",
				  ccc:"${key0}${key1}",
				  ddd:"${key2:指定なし}"
				}
				""");

		StringBuilder sb = new StringBuilder();
		Bench.measure(() -> {
			sb.setLength(0);
			mapTemplate.append(sb, Map.of("key1", "あああ", "key2", "いいい"));
		}, 10_000, 100_000).result(System.out::println);
		System.out.println(sb.toString());
	}

	@Test
	void test4() {
		Bench.measure(() -> {
			@SuppressWarnings("unused")
			String template = """
					{
					  aaa:"%s",
					  bbb:"%s",
					  ccc:"%s",
					  ddd:"%s"
					}
					""".formatted("あああ", "いいい", "ううう", "えええ");
		}, 10_000, 100_000).result(System.out::println);
	}
}
