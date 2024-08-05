package zircuf_tools.gen;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.Data;
import zircuf.util.text.function.Code;
import zircuf_tools.gen.core.java.JavaCodeTemplate;
import zircuf_tools.gen.core.java.JavaFieldType;

class CodeGenTest {

	@Test
	void test() {
		JavaCodeTemplate javaCodeTemplate = new JavaCodeTemplate();
		StringBuilder sb = new StringBuilder();

		// クラス開始
		sb.append(javaCodeTemplate.header("MyClass", "マイクラス"));

		sb.append(javaCodeTemplate.addField(JavaFieldType.STRING, Code.lowerSnakeToLowerCamel("my_field_01"), "変数01"));
		sb.append(javaCodeTemplate.addField(JavaFieldType.OBJECT, Code.lowerSnakeToLowerCamel("my_field_02"), "変数02", "Child1"));

		// 子クラス開始
		sb.append(javaCodeTemplate.headerChild("Child1", "子クラス"));
		sb.append(javaCodeTemplate.addField(JavaFieldType.STRING, Code.lowerSnakeToLowerCamel("child_field_01"), "子変数01"));
		sb.append(javaCodeTemplate.addField(JavaFieldType.INTEGER, Code.lowerSnakeToLowerCamel("child_field_02"), "子変数03"));
		sb.append(javaCodeTemplate.addField(JavaFieldType.BOOLEAN, Code.lowerSnakeToLowerCamel("child_field_03"), "子変数03"));
		sb.append(javaCodeTemplate.footerChild());
		// 子クラス終了

		sb.append(javaCodeTemplate.addField(JavaFieldType.LIST, Code.lowerSnakeToLowerCamel("my_field_03"), "変数03", "Child1"));

		// クラス終了
		sb.append(javaCodeTemplate.footer());

		System.out.println(sb.toString());
	}

	/**
	 * マイクラス
	 */
	@Data
	public static class MyClass {
		/**
		 * 変数01
		 */
		private String my_field_01;
		/**
		 * 変数02
		 */
		private Child1 my_field_02 = new Child1();

		/**
		 * 子クラス
		 */
		@Data
		public static class Child1 {
			/**
			 * 子変数01
			 */
			private String child_field_01;
			/**
			 * 子変数03
			 */
			private Integer child_field_02;
			/**
			 * 子変数03
			 */
			private Boolean child_field_03;
		}

		/**
		 * 変数03
		 */
		private List<Child1> my_field_03 = new ArrayList<Child1>();
	}

}
