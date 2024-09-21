package zircuf_tools.window;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ResourceFileList {
    public static void main(String[] args) {
        JFrame frame = new JFrame("リソースファイル一覧");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // リソースのベースディレクトリ
        String resourceDir = "/resources";

        // リソースの列挙とファイル名リストの作成
        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(resourceDir);
            System.out.println("resouces");
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String path = resource.getPath();
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                listModel.addElement(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JListの作成と設定
        JList<String> filelist = new JList<>(listModel);
        filelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 単一選択モード

        // スクロールバーの追加
        JScrollPane scrollPane = new JScrollPane(filelist);

        // フレームにコンポーネントを追加
        frame.add(scrollPane);

        // フレームのサイズと表示
        frame.pack();
        frame.setVisible(true);
    }
}