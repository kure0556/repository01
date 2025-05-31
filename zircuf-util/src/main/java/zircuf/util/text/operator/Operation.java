package zircuf.util.text.operator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import zircuf.util.text.extractor.TextExtractor;
import zircuf.util.text.extractor.TextExtractor.ResultMap;
import zircuf.util.text.operator.annotation.Operator;
import zircuf.util.text.template.MapTemplate;
import zircuf.util.text.template.Template;

public class Operation {

	public String opeline(Class<?> clazz, String line) {
		try {
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			Object newInstance = constructor.newInstance();
	        String extracted = extracted(clazz, line, newInstance);
	        return extracted;

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO コンストラクターエラー
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO インスタンス生成失敗
			e.printStackTrace();
		}
		return null;
	}

	private String extracted(Class<?> clazz, String line, Object newInstance)
			throws IllegalAccessException, InvocationTargetException {
		for (Method method : clazz.getDeclaredMethods()) {
			Operator[] operators = method.getAnnotationsByType(Operator.class);
			for (Operator operator : operators) {
				// 抽出
				String value1 = operator.value1();
				TextExtractor textExtractor = TextExtractor.of(value1);
				ResultMap resultMap = textExtractor.extract(line);
				System.out.println(resultMap);
				
				// 任意メソッドの実行
				int paramLength = method.getParameterTypes().length;
				if(paramLength <= 0) {
					method.invoke(newInstance);
				}
				
				// 埋め込み
				String value2 = operator.value2();
				MapTemplate mapTemplate = Template.ofMap(value2);
				String result = mapTemplate.inject(resultMap);
				return result;
			}
		}
		return null;
	}

}
