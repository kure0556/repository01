package zircuf.util.text.extractor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
			""",
			"""
			'aaa{}bbb{}ccc.{hoge}',\
			'aaaXXXbbbYYYccc.csv'\
			""" })
	void test(String template, String input) {
		TextExtractor.of(template)
				.peek(System.out::println) // テンプレートの正規表現を覗き見る
				.extract(input) // inputの文字列から変数を抽出する
				.peek(System.out::println) // 抽出した変数（map形式）を覗き見る
				.ifPresentOrElse(map -> {
					System.out.println("変数が抽出された"); // 変数の抽出に成功した場合
				}, () -> {
					System.out.println("マッチしていません"); // 変数の抽出に失敗した場合
				});
	}

	@Test
	@DisplayName("ex1: 固定文字 + 無名 + 固定長 + 無名 + 無名")
	void testExample1() {
		TextExtractor te = TextExtractor.of("aaa{}bbb{:2}{}_{}.csv");

		var r = te.extract("aaaxxxbbb12YYY_ZZZ.csv");

		assertTrue(r.isMatch());
		assertEquals("xxx", r.get("{0}"));
		assertEquals("12", r.get("{1}"));
		assertEquals("YYY", r.get("{2}"));
		assertEquals("ZZZ", r.get("{3}"));
	}

	@Test
	@DisplayName("ex2: 名前付きキー + 固定長 + 無名")
	void testExample2() {
		TextExtractor te = TextExtractor.of("aaa{hoge}bbb{fuga:2}{piyo}_{}.csv");

		var r = te.extract("aaaxxxbbb12YYY_ZZZ.csv");

		assertTrue(r.isMatch());
		assertEquals("xxx", r.get("hoge"));
		assertEquals("12", r.get("fuga"));
		assertEquals("YYY", r.get("piyo"));
		assertEquals("ZZZ", r.get("{3}"));
	}

	@Test
	@DisplayName("ex3: min,max 指定・max 指定のみ・無名・min 指定のみ")
    void testExample3() {
        TextExtractor te = TextExtractor.of("{hoge:2,3}{fuga:,3}{}{piyo:4,}");

        var r = te.extract("aaabbbxxxxxxx.csv");

        assertTrue(r.isMatch());
        assertEquals("aaa", r.get("hoge"));     // 2〜3文字
        assertEquals("bbb", r.get("fuga"));     // 最大3文字
        assertEquals("xxxxxxx", r.get("{2}"));  // 任意長
        assertEquals(".csv", r.get("piyo"));    // 最小4文字
    }

	@Test
	@DisplayName("無名 {} が複数ある場合の非貪欲動作")
	void testMultipleUnnamed() {
		TextExtractor te = TextExtractor.of("aaa{}bbb{}ccc");

		var r = te.extract("aaaxxxbbbYYYccc");

		assertTrue(r.isMatch());
		assertEquals("xxx", r.get("{0}"));
		assertEquals("YYY", r.get("{1}"));
	}

	@Test
	@DisplayName("マッチしない場合は isMatch=false")
	void testNotMatch() {
		TextExtractor te = TextExtractor.of("aaa{hoge}bbb");

		var r = te.extract("invalid");

		assertFalse(r.isMatch());
		assertTrue(r.isEmpty());
	}

	@Test
	@DisplayName("min 未指定 → 0 補完の挙動確認")
	void testMinOmitted() {
		TextExtractor te = TextExtractor.of("aaa{val:,3}bbb");

		var r = te.extract("aaaxxxbbb");

		assertTrue(r.isMatch());
		assertEquals("xxx", r.get("val"));
	}

	@Test
	@DisplayName("固定文字列のエスケープ確認（正規表現メタ文字を含む）")
	void testRegexEscape() {
		TextExtractor te = TextExtractor.of("a+b{val}c*d");

		var r = te.extract("a+bXYZc*d");

		assertTrue(r.isMatch());
		assertEquals("XYZ", r.get("val"));
	}

	@Test
	@DisplayName("toArray / toList / toMap の動作確認")
	void testResultMapHelpers() {
		TextExtractor te = TextExtractor.of("aaa{hoge:3}{fuga}");

		var r = te.extract("aaaxxxYYY");

		assertTrue(r.isMatch());
		assertArrayEquals(new String[] { "xxx", "YYY" }, r.toArray());
		assertEquals(java.util.List.of("xxx", "YYY"), r.toList());
		assertEquals(Map.of("hoge", "xxx", "fuga", "YYY"), r.toMap());
	}
}
