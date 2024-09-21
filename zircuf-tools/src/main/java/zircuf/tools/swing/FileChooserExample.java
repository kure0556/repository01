package zircuf.tools.swing;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ファイル選択");
        JButton button = new JButton("ファイルを開く");

        button.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            // 初期ディレクトリの設定（例：デスクトップ）
            chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));

            // ファイルフィルタの設定（.tsvファイルのみ）
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TSVファイル (*.tsv)", "tsv");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                System.out.println("選択されたファイル: " + selectedFile.getAbsolutePath());
                // ここで、選択されたファイルに対する処理を行う
            }
        });

        frame.add(button);
        frame.pack();
        frame.setVisible(true);
    }
}