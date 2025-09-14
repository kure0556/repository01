package zircuf.tools.swing.parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * メニュービルダー
 * <pre>
 * 		MenuBuilder.of(this)
 *				.add("ファイル")                                   // 親メニューの追加
 *				.addItem("開く", () -> {                          // 子メニューのイベント追加
 *					statusLabel.setText("開くが選択されました");
 *				})
 *				.addSeparator()                                  // セパレーターの追加
 *				.addItem("終了", () -> {
 *					System.exit(0);
 *				})
 *				.add("編集")
 *				.addItem("元に戻す", () -> {
 *					statusLabel.setText("未サポートの機能です");
 *				});
 * </pre>
 */
public class MenuBuilder {

	public static MenuBuilder of(JFrame frame) {
		return new MenuBuilder(frame);
	}

	private JMenuBar menuBar = new JMenuBar();
	private JMenu currentMenu = null;

	private MenuBuilder(JFrame frame) {
		frame.setJMenuBar(menuBar);
	}

	/**
	 * メニューの第1階層を追加する
	 * @param name
	 * @return
	 */
	public MenuBuilder add(String name) {
		currentMenu = new JMenu(name);
		menuBar.add(currentMenu);
		return this;
	}

	/**
	 * メニューの第2階層を追加する
	 * @param name
	 * @param perform
	 * @return
	 */
	public MenuBuilder addItem(String name, Runnable perform) {
		return addItem(name, event -> {
			perform.run();
		});
	}

	/**
	 * メニューの第2階層を追加する
	 * @param name
	 * @param perform
	 * @return
	 */
	public MenuBuilder addItem(String name, Consumer<ActionEvent> perform) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				perform.accept(e);
			}
		});
		currentMenu().add(menuItem);
		return this;
	}

	/**
	 * メニューの第2階層にセパレータを追加する
	 * @param name
	 * @param perform
	 * @return
	 */
	public MenuBuilder addSeparator() {
		currentMenu().addSeparator();
		return this;
	}

	public void build() {
	}

	private JMenu currentMenu() {
		if (Objects.isNull(currentMenu)) {
			add("メニュー");
		}
		return currentMenu;
	}

}
