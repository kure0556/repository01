package zircuf.util.text.operator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OperationTest {

	@ParameterizedTest
	@CsvSource({
		"Logger.info(\"ログメッセージ\")",
		"'\tLogger.info(\"ログメッセージ\");'",})
	void test1(String val) {
		Operation operation = new Operation();
		System.out.println(val);
		String opeline = operation.opeline(LogFix.class, val);
		System.out.println(opeline);
	}
}
