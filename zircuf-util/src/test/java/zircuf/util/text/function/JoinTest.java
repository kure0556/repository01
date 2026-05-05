package zircuf.util.text.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JoinTest {

    @Test
    @DisplayName("csv: カンマ区切りで連結される")
    void testCsv() {
        String[] arr = { "A", "B", "C" };
        assertEquals("A,B,C", Join.csv(arr));
    }

    @Test
    @DisplayName("csv: null は \"null\" として扱われる（String.join の仕様）")
    void testCsvNullElement() {
        String[] arr = { "A", null, "C" };
        assertEquals("A,null,C", Join.csv(arr));
    }

    @Test
    @DisplayName("tsv: タブ区切りで連結される")
    void testTsv() {
        String[] arr = { "A", "B", "C" };
        assertEquals("A\tB\tC", Join.tsv(arr));
    }

    @Test
    @DisplayName("pipe: パイプ区切りで連結される")
    void testPipe() {
        String[] arr = { "A", "B", "C" };
        assertEquals("A|B|C", Join.pipe(arr));
    }

    @Test
    @DisplayName("csvDQ: 通常の値はダブルクォートで囲まれ、内部の \" は \"\" にエスケープされる")
    void testCsvDQ() {
        String[] arr = { "A", "B,C", "D\"E" };
        String expected = "\"A\",\"B,C\",\"D\"\"E\"";
        assertEquals(expected, Join.csvDQ(arr));
    }

    @Test
    @DisplayName("csvDQ: null は未記入セルとして扱われる（,,）")
    void testCsvDQNullElement() {
        String[] arr = { "A", "B,C", null, "D\"E" };
        String expected = "\"A\",\"B,C\",,\"D\"\"E\"";
        assertEquals(expected, Join.csvDQ(arr));
    }

    @Test
    @DisplayName("csvDQ: 空配列は空文字を返す")
    void testCsvDQEmpty() {
        String[] arr = {};
        assertEquals("", Join.csvDQ(arr));
    }
}