package zircuf.tools.swing.util;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SwingUtil {

	public static final void uiWindows() {

		// Windows用の見た目に変更する
		try {
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
		    ex.printStackTrace();
		}

	}
	
}
