package zircuf.tools.swing;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import zircuf.tools.swing.parts.MenuBuilder;

public class StatusDialog extends JFrame {
	private JLabel statusLabel;

	public StatusDialog() {
		super("ステータスバーとメニューバーの例");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);

		Font font = new Font("ＭＳ ゴシック", Font.PLAIN, 14);
        UIManager.put("Font", font);
        SwingUtilities.updateComponentTreeUI(this);

		// ステータスバーの作成
		statusLabel = new JLabel("ステータスバー");
		add(statusLabel, BorderLayout.SOUTH);

		// メニューの追加
		MenuBuilder.of(this)
				.add("ファイル")
				.addItem("開く", () -> {
					statusLabel.setText("開くが選択されました");
				})
				.addSeparator()
				.addItem("終了", () -> {
					System.exit(0);
				})
				.add("編集")
				.addItem("元に戻す", () -> {
					statusLabel.setText("未サポートの機能です");
				});

		setVisible(true);
	}

	public static void main(String[] args) {
		new StatusDialog();
	}
}