package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import inpdf.DirectoryManager;

public class ButtonActionReadFile implements ActionListener{
	private DirectoryManager directory;
	private LabelManager labelManager;

	public ButtonActionReadFile(DirectoryManager directory, LabelManager labelManager) {
		this.directory = directory;
		this.labelManager = labelManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser("C:/");
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));
		
		int res = fileChooser.showSaveDialog(null);
		if(res == JFileChooser.APPROVE_OPTION) {
			System.out.println("TESTE");
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			this.directory.setInputDirectoryPath(file.toString());
			this.labelManager.addText(this.directory.getInputDirectoryPath());
		}
	}

}
