package zircuf.util.text.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JoinSplitRoundTripTest {

    @Test
    @DisplayName("csvDQ: Join → Split → Join が同一文字列になる")
    void testCsvDQRoundTrip() {
        String[] arr = { "A", "B,C", null, "D\"E" };

        String joined = Join.csvDQ(arr);
        String[] split = Split.csvDQ(joined);
        String rejoined = Join.csvDQ(split);

        assertEquals(joined, rejoined);
    }

    @Test
    @DisplayName("csv: Join → Split → Join が同一文字列になる")
    void testCsvRoundTrip() {
        String[] arr = { "A", "null", "C" };

        String joined = Join.csv(arr);
        String[] split = Split.csv(joined);
        String rejoined = Join.csv(split);

        assertEquals(joined, rejoined);
    }
}

