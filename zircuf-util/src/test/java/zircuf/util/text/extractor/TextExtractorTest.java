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
			""",
			"""
			'{hoge:2,3}{fuga:,3}{}{piyo:4,}',\
			'aaabbbxxxxxxx.csv'\
			""",
			"""
			'{hoge:2,3}{fuga:,3}{}{piyo:4,}',\
			'aabbbxxxxxxx.csv'\
			""" })
	void test(String template, String input) {
		TextExtractor.of(template)
				.peek(System.out::println)	// テンプレートの正規表現を覗き見る
				.extract(input)				// inputの文字列から変数を抽出する
				.peek(System.out::println)	// 抽出した変数（map形式）を覗き見る
				.ifPresentOrElse(map -> {
					System.out.println("変数が抽出された");		// 変数の抽出に成功した場合
				}, () -> {
					System.out.println("マッチしていません");	// 変数の抽出に失敗した場合
				});
	}

}
