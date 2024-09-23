package zircuf.tools.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import lombok.RequiredArgsConstructor;

public class FileDropDialog extends JFrame {

	public static void main(String[] args) {
		new FileDropDialog("ファイル存在確認", (window, files) -> {
			for (File file : files) {
				boolean result = window.confirm("""
						ファイルが存在します。上書き保存しますか？
						%s\
						""".formatted(file.getAbsolutePath()));
				window.log("ダイアログ結果" + result);
			}
		});
	}

	private JTextArea logArea;
	private JLabel label;
	private JPanel dropArea;
	private Window window;

	public FileDropDialog(String windowTitle, BiConsumer<Window, List<File>> proc) {
		setTitle(windowTitle);
		setSize(440, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ドロップエリア
		dropArea = new JPanel();
		dropArea.setLayout(new FlowLayout(FlowLayout.LEFT)); // 左寄せのFlowLayout
		dropArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // JPanelの内側の余白
		dropArea.setPreferredSize(new Dimension(440, 50));
		dropArea.setBackground(Color.LIGHT_GRAY);
		dropArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

		// ファイル選択ボタン
		FileChooserButton fcButton = new FileChooserButton("ファイル選択").init(this, proc);
		dropArea.add(fcButton);

		// ドロップエリアにテキストを追加
		label = new JLabel("ここにファイルをドラッグアンドドロップ");
		label.setBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, (float) 2, (float) 2, (float) 2, false));
		label.setPreferredSize(new Dimension(280, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER); // 水平方向中央
		label.setForeground(Color.DARK_GRAY); // 文字色

		GridBagConstraints gbc = new GridBagConstraints(); // 垂直方向中央
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		dropArea.add(label);

		// ログ出力エリア
		logArea = new JTextArea();
		logArea.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		logArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// window
		window = new Window() {
			public void log(String str) {
				logArea.append(str + "\n");
			}
		};

		// イベント追加
		FileDropTargetListener listener = new FileDropTargetListener(proc);
		new DropTarget(this, DnDConstants.ACTION_COPY, listener, true);
		new DropTarget(logArea, DnDConstants.ACTION_COPY, listener, true);

		// レイアウト
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(dropArea, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	public void log(String str) {
		logArea.append(str + "\n");
	}

	@RequiredArgsConstructor
	private class FileDropTargetListener implements DropTargetListener {

		public final BiConsumer<Window, List<File>> cons;

		// ドラッグがドロップエリアに入ったとき
		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrag(DnDConstants.ACTION_COPY);
				dropArea.setBackground(Color.CYAN); // ドラッグ中は背景色を黄色に変更
			} else {
				dtde.rejectDrag();
			}
		}

		// ドラッグがドロップエリアから出たとき
		@Override
		public void dragExit(DropTargetEvent dte) {
			dropArea.setBackground(Color.LIGHT_GRAY); // 元の背景色に戻す
		}

		// ドロップされたとき
		@Override
		public void drop(DropTargetDropEvent dtde) {
			try {
				if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> files = (List<File>) dtde.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : files) {
						log("droped > " + file.getAbsolutePath());
					}
					cons.accept(window, files);
				} else {
					dtde.rejectDrop();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dropArea.setBackground(Color.LIGHT_GRAY); // 必ず元の背景色に戻す
			}
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}

	}

	public class FileChooserButton extends JButton {

		public FileChooserButton(String text) {
			super(text);
		}

		public FileChooserButton init(JFrame frame, BiConsumer<Window, List<File>> proc) {
			addActionListener(e -> {
				JFileChooser chooser = new JFileChooser();
				// 初期ディレクトリの設定（例：デスクトップ）
				chooser.setCurrentDirectory(new File("src/main/resources/"));

				// ファイルフィルタの設定（.tsvファイルのみ）
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TSVファイル (*.tsv)", "tsv");
				chooser.setFileFilter(filter);

				// 複数ファイル選択
				chooser.setMultiSelectionEnabled(true);
				int returnVal = chooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File[] selectedFiles = chooser.getSelectedFiles();
					Arrays.stream(selectedFiles).forEach(file -> {
						log("selected > " + file.getAbsolutePath());
					});
					proc.accept(window, Arrays.asList(selectedFiles));
				}
			});
			return this;
		}

	}

	public class Window {

		void log(String str) {
			logArea.append(str + "\n");
		}

		boolean confirm(String message) {
			return Dialog.confirm(message);
		}

	}
}