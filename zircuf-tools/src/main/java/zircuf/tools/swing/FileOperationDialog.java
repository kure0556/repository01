package zircuf.tools.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import lombok.RequiredArgsConstructor;

public class FileOperationDialog extends JFrame {

	private final String windowTitle = "ファイルパスログ";
	private JTextArea logArea;
	private JLabel label;
	private JPanel dropArea;

	public FileOperationDialog(Consumer<List<File>> proc) {
		setTitle(windowTitle);
		setSize(500, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ドロップエリア
		dropArea = new JPanel();
		dropArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // JPanelの内側の余白
		dropArea.setPreferredSize(new Dimension(500, 60));
		dropArea.setBackground(Color.LIGHT_GRAY);
		dropArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		new DropTarget(dropArea, DnDConstants.ACTION_COPY, new FileDropTargetListener(proc), true);

		// ドロップエリアにテキストを追加
		label = new JLabel("こちらにファイルをドラッグアンドドロップ");
		label.setBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, (float) 2, (float) 2, (float) 2, false));
		label.setPreferredSize(new Dimension(460, 40));
		label.setHorizontalAlignment(SwingConstants.CENTER); // 水平方向中央
		label.setForeground(Color.DARK_GRAY); // 文字色
		GridBagConstraints gbc = new GridBagConstraints(); // 垂直方向中央
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		dropArea.add(label, gbc);

		// ログ出力エリア
		logArea = new JTextArea();
		logArea.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
		logArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		new DropTarget(logArea, DnDConstants.ACTION_COPY, new FileDropTargetListener(proc), true);

		// レイアウト
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(dropArea, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		// ウィンドウクローズ時の処理
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// 必要であれば、ログデータを保存するなどの処理を行う
			}
		});

		setVisible(true);
	}

	@RequiredArgsConstructor
	private class FileDropTargetListener implements DropTargetListener {

		public final Consumer<List<File>> cons;

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
					List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : files) {
						logArea.append("droped > " + file.getAbsolutePath() + "\n");
					}
					cons.accept(files);
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

	public static void main(String[] args) {
		new FileOperationDialog(files -> {
			boolean confirm = Window.Dialog.confirm("ファイルが存在します。上和書き保存しますか？");
			System.out.println(confirm);
		});
	}
}