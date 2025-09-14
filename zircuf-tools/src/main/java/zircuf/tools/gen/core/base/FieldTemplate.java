package zircuf.tools.gen.core.base;

public interface FieldTemplate {

	public boolean hasChild();

	/**
	 * @param name フィールド物理名
	 * @param option 文字列の場合：デフォルト値、オブジェクト・リスト・マップの場合：クラス名
	 * @return
	 */
	public String fieldCode(String name, String option);

}
