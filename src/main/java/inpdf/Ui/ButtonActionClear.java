package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import inpdf.DirectoryManager;

public class ButtonActionClear implements ActionListener{
	private DirectoryManager directory;
	private LabelManager labelManager;

	public ButtonActionClear(DirectoryManager directory, LabelManager labelManager) {
		this.directory = directory;
		this.labelManager = labelManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.directory.getInputDirectoryPath() != null) {
			this.directory.clearInputDirectoryPath();
			labelManager.clearText();	
		}
	}

}
