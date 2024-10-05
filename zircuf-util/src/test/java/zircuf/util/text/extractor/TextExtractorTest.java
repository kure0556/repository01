package zircuf.util.text.extractor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TextExtractorTest {

	@ParameterizedTest
	@CsvSource({
			"""
			aaa{hoge}bbb{fuga:2}{piyo}c.cc{yyyy:4}{mm:2}{dd:2}-{}.csv,\
			aaaxxxbbb123c.cc20241023-eoapijfwih.csv\
			""",
			"""
			aaa{hoge}bbb{fuga:1}{piyo:2}c.cc{yyyy:4}{mm:2}{dd:2}{}.csv,\
			aaaxxxbbb123c.cc20241023eoapijfwih.csv\
			""",
			"""
			'aaa{hoge}bbb{fuga:,1}{piyo}c.cc{yyyy:4}{mm:2}{dd:2}{}.csv',\
			'aaaxxxbbb123c.cc20241023eoapijfwih.csv'\
			""",
			"""
			'aaa{}bbb{:,1}{}c.cc{:4}{:2}{:2}{}.csv',\
			'aaaxxxbbb123c.cc20241023eoapijfwih.csv'\
			""",
			"""
			'aaa{}bbb{:,1}{}c.cc{:4}{:2}{:2}{}.csv',\
			'aaaxxxbbb123c.cc20241023eoapijfwih.csvX'\
			""" })
	void test(String template, String input) {
//		TextExtractor.of(template).extract(input).ifPresentOrElse(map -> {
		TextExtractor.of(template).peek(System.out::println).extract(input).peek(System.out::println).ifPresentOrElse(map -> {
			System.out.println("変数が抽出された");
		}, () -> {
			System.out.println("マッチしていません");
		});
	}

	void test2(String template, String input) {
		TextExtractor.of(template).extract(input).ifPresentOrElse(map -> {
			System.out.println("変数が抽出された");
		}, () -> {
			System.out.println("マッチしていません");
		});
	}

}
