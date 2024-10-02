package zircuf.util.text.picker;

import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TextExtractorTest {

	@ParameterizedTest
	@CsvSource({
//			"""
//			aaa{hoge}bbb{fuga:2}{piyo}c.cc{yyyy:4}{mm:2}{dd:2}-{}.csv,\
//			aaaxxxbbb123c.cc20241023-eoapijfwih.csv\
//			""",
//			"""
//			aaa{hoge}bbb{fuga:1}{piyo:2}c.cc{yyyy:4}{mm:2}{dd:2}{}.csv,\
//			aaaxxxbbb123c.cc20241023eoapijfwih.csv\
//			""",
			"""
			'aaa{hoge}bbb{fuga:,1}{piyo}c.cc{yyyy:4}{mm:2}{dd:2}{}.csv',\
			'aaaxxxbbb123c.cc20241023eoapijfwih.csv'\
			"""})
	void test(String template, String input) {
//			System.out.println(new TextExtractor(template));
			try {
				TextExtractor extractor = TextExtractor.of(template);
				System.out.println(extractor);
				Map<String, String> map = extractor.extract(input);
				System.out.println(map); // 出力: {hoge=xxx, fuga=123}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
