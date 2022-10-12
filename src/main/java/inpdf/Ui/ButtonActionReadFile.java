package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import inpdf.DirectoryManager;

public class ButtonActionReadFile implements ActionListener{
	private LabelManager labelManager;

	public ButtonActionReadFile(DirectoryManager directory, LabelManager labelManager) {
		this.labelManager = labelManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser("C:/");
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));
		
		int res = fileChooser.showSaveDialog(null);
		if(res == JFileChooser.APPROVE_OPTION) {
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			DirectoryManager.setInputDirectoryPath(file.toPath().toAbsolutePath());
			this.labelManager.addText(DirectoryManager.getInputDirectoryPath().toString());
		}
	}
}