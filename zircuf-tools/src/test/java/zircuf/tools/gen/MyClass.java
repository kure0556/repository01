package zircuf.tools.gen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * __FIX_ME__
 */
@Data
@Builder
public class MyClass {

	/** 文字列の項目 */
	private String myField1;

	/** 整数の項目 */
	private Integer myField2;

	/** 真偽値の項目 */
	private Boolean myField3;

	/** 文字列リストの項目 */
	@Builder.Default
	private List<String> myField4 = new ArrayList<String>();

	/** オブジェクトの項目 */
	private MyObject1 myObject1;

	/** オブジェクトリストの項目 */
	@Builder.Default
	private List<MyField5> myField5List = new ArrayList<MyField5>();

	/** オブジェクトマップの項目 */
	@Builder.Default
	private Map<String, MyField6> myField6Map = new LinkedHashMap<String, MyField6>();

	/**
	 * オブジェクトの項目Dto
	 */
	@Data
	@Builder
	public static class MyObject1 {

		/** 文字列の項目 */
		private String myField7;

		/** 整数の項目 */
		private Integer myField8;

		/** 真偽値の項目 */
		private Boolean myField9;

		/** オブジェクトの項目 */
		private MyObject2 myObject2;
	}

	/**
	 * オブジェクトの項目Dto
	 */
	@Data
	@Builder
	public static class MyObject2 {

		/** 文字列の項目 */
		private String myField21;

		/** 整数の項目 */
		private Integer myField22;
	}

	/**
	 * オブジェクトリストの項目Dto
	 */
	@Data
	@Builder
	public static class MyField5 {

		/** 文字列の項目 */
		private String myField51;
	}

	/**
	 * オブジェクトマップの項目Dto
	 */
	@Data
	@Builder
	public static class MyField6 {

		/** 文字列の項目 */
		private String myField61;
	}
}