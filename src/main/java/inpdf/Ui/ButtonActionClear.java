package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import inpdf.DirectoryManager;

public class ButtonActionClear implements ActionListener{
	private LabelManager labelManager;

	public ButtonActionClear(DirectoryManager directory, LabelManager labelManager) {
		this.labelManager = labelManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(DirectoryManager.getInputDirectoryPath() != null) {
			labelManager.clearText();	
		}
	}
}