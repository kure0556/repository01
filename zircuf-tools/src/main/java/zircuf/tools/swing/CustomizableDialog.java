package zircuf.tools.swing;

import java.awt.GridLayout;
import java.io.File;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomizableDialog {
    public static void main(String[] args) throws Exception {
        // JSONファイルを読み込む
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("ui.json"));

        // JPanelを作成
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // JSONノードを処理してSwingコンポーネントを作成
        Iterator<JsonNode> elements = root.get("components").elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            System.out.println(element);
            String type = element.get("type").asText();
            String label = element.get("label").asText();
            String name = element.get("name").asText();

            JLabel labelComponent = new JLabel(label);
            panel.add(labelComponent);

            JComponent component = null;
            switch (type) {
                case "JTextField":
                    // テキストボックスを作成
                    component = new JTextField(20); // 20文字分

                    break;
                case "JRadioButton":
                	JPanel jPanel = new JPanel();
                    ButtonGroup group = new ButtonGroup();
                    JsonNode options = element.get("options");
                    for (JsonNode option : options) {
                        JRadioButton radioButton = new JRadioButton(option.get("text").asText());
                        radioButton.setActionCommand(option.get("value").asText());
                        group.add(radioButton);
                        jPanel.add(radioButton);
                    }
                    component = jPanel;
                    break;
                case "JCheckBox":
                    component = new JCheckBox();
                    break;
            }

            // ラベルの追加
            panel.add(component);
            
        }

        // JFrameの作成
        JFrame frame = new JFrame("JSONから生成された画面");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}