package zircuf.tools.gen;

import java.util.List;

import org.junit.jupiter.api.Test;

import zircuf.tools.gen.core.base.CodeTemplate;
import zircuf.tools.gen.core.java.JavaCodeTemplate;
import zircuf.tools.gen.core.ts.TypeScriptCodeTemplate;
import zircuf.util.data.table.Table;

class CodeGeneratorTest {

	List<String[]> table = Table.ofTsv("""
			my_field1									文字列	文字列の項目
			my_field2									整数	整数の項目
			my_field3									真偽値	真偽値の項目
			my_field4									文字列リスト	文字列リストの項目
			my_object1									オブジェクト	オブジェクトの項目
				my_field7								文字列	文字列の項目
				my_field8								整数	整数の項目
				my_field9								真偽値	真偽値の項目
				my_object2								オブジェクト	オブジェクトの項目
					my_field21							文字列	文字列の項目
					my_field22							整数	整数の項目
			my_field5_list									リスト	オブジェクトリストの項目
				my_field51								文字列	文字列の項目
			my_field6_map									マップ	オブジェクトマップの項目
				my_field61								文字列	文字列の項目
			""").getTable();

	/**
	 * Java自動生成
	 */
	@Test
	void test1() {

		CodeTemplate codeTemplate = new JavaCodeTemplate();
		//		CodeTemplate codeTemplate = new TypeScriptCodeTemplate();

		CodeGenerator codeGenerator = CodeGenerator.builder()
				.table(table)					// オブジェクト定義の指定
				.codeTemplate(codeTemplate)		// テンプレートの指定
				.pysicalNameIdx(0)				// 物理名の列番号（+1ずらして階層を表現）
				.logicalNameIdx(10)				// 論理名の列番号
				.typeTxetIdx(9)					// 型の列番号
				.classPhysicalName(null)		// 生成するクラスの物理名（省略可）
				.classLogicalName(null)			// 生成するクラスの論理名（省略可）
				.extendsOrImplementsText(null)	// 生成するクラスの付与文字列（継承用）
				.packageText(null)				// パッケージ名（省略可）
				.build();

		// ソース生成（Java）
		String string = codeGenerator.generateCode();

		System.out.println(string);

	}

	/**
	 *  TypeScript自動生成
	 */
	@Test
	void test2() {
		CodeTemplate codeTemplate = new TypeScriptCodeTemplate();

		CodeGenerator codeGenerator = CodeGenerator.builder()
				.table(table)
				.codeTemplate(codeTemplate)
				.pysicalNameIdx(0)
				.logicalNameIdx(10)
				.typeTxetIdx(9)
				.classPhysicalName(null)
				.classLogicalName(null)
				.extendsOrImplementsText(null)
				.packageText(null)
				.build();

		// ソース生成（TypeScript）
		String string = codeGenerator.generateCode();

		System.out.println(string);

	}

}
