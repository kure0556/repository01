package zircuf_tools.gen;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.Data;
import zircuf.env.Resource;
import zircuf_tools.gen.core.base.CodeTemplate;
import zircuf_tools.gen.core.java.JavaCodeTemplate;
import zircuf_tools.gen.core.ts.TypeScriptCodeTemplate;

class CodeGenTest {

	@Test
	void test1() {
		List<String[]> table = Resource.local().of("codegen.tsv").asTsv().getTable();

		CodeTemplate codeTemplate = new JavaCodeTemplate();
//		CodeTemplate codeTemplate = new TypeScriptCodeTemplate();

		CodeGenerator codeGenerator = CodeGenerator.builder()
				.table(table)
				.codeTemplate(codeTemplate)
				.pysicalNameIdx(0)
				.logicalNameIdx(10)
				.typeTxetIdx(9)
				.classPysicalName(null)
				.classLogicalName(null)
				.extendsOrImplementsText(null)
				.packageText(null)
				.build();
		String string = codeGenerator.generateCode();

		System.out.println(string);

	}

	@Test
	void test2() {
		List<String[]> table = Resource.local().of("codegen.tsv").asTsv().getTable();

		CodeTemplate codeTemplate = new TypeScriptCodeTemplate();

		CodeGenerator codeGenerator = CodeGenerator.builder()
				.table(table)
				.codeTemplate(codeTemplate)
				.pysicalNameIdx(0)
				.logicalNameIdx(10)
				.typeTxetIdx(9)
				.classPysicalName(null)
				.classLogicalName(null)
				.extendsOrImplementsText(null)
				.packageText(null)
				.build();
		String string = codeGenerator.generateCode();

		System.out.println(string);

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
