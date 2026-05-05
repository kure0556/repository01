package zircuf.util.text.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SplitTest {

    @Test
    @DisplayName("csv: カンマ区切りで分割される（空セル保持）")
    void testCsv() {
        String text = "A,B,,C";
        assertArrayEquals(new String[] { "A", "B", "", "C" }, Split.csv(text));
    }

    @Test
    @DisplayName("csv: \"null\" は文字列として扱われる")
    void testCsvNullLiteral() {
        String text = "A,null,C";
        assertArrayEquals(new String[] { "A", "null", "C" }, Split.csv(text));
    }

    @Test
    @DisplayName("tsv: タブ区切りで分割される")
    void testTsv() {
        String text = "A\tB\tC";
        assertArrayEquals(new String[] { "A", "B", "C" }, Split.tsv(text));
    }

    @Test
    @DisplayName("pipe: パイプ区切りで分割される")
    void testPipe() {
        String text = "A|B|C";
        assertArrayEquals(new String[] { "A", "B", "C" }, Split.pipe(text));
    }

    @Test
    @DisplayName("csvDQ: 通常の値は正しく復元される")
    void testCsvDQBasic() {
        String text = "\"A\",\"B,C\",\"D\"\"E\"";
        assertArrayEquals(
            new String[] { "A", "B,C", "D\"E" },
            Split.csvDQ(text)
        );
    }

    @Test
    @DisplayName("csvDQ: null（未記入セル）は null として復元される")
    void testCsvDQNullCell() {
        String text = "\"A\",\"B\",,\"C\"";
        assertArrayEquals(
            new String[] { "A", "B", null, "C" },
            Split.csvDQ(text)
        );
    }

    @Test
    @DisplayName("csvDQ: 空文字 \"\" は空文字として復元される")
    void testCsvDQEmptyString() {
        String text = "\"A\",\"\",\"C\"";
        assertArrayEquals(
            new String[] { "A", "", "C" },
            Split.csvDQ(text)
        );
    }
}

