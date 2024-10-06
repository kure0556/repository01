package zircuf.tools.swing.parts;

import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSelector {

	private JFileChooser chooser = new JFileChooser();
	private Component frame;

	public FileSelector(String currentDir) {
		currentDir = Objects.requireNonNullElse(currentDir, "");
		chooser.setCurrentDirectory(new File(currentDir));
	}

	public FileSelector(String currentDir, Component frame) {
		this(currentDir);
		this.frame = frame;
	}

	public final FileSelector tsv() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TSVファイル (*.tsv)", "tsv");
		chooser.addChoosableFileFilter(filter);
		return this;
	}

	public final Optional<File> selectedFile() {
		chooser.setMultiSelectionEnabled(false);
		int returnVal = chooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return Optional.ofNullable(chooser.getSelectedFile());
		}
		return Optional.empty();
	}

	public final List<File> selectedFiles() {
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = chooser.getSelectedFiles();
			return Arrays.stream(selectedFiles).toList();
		}
		return Collections.emptyList();
	}

}
