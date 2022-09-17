package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import inpdf.DirectoryManager;

public class ButtonActionConfirm implements ActionListener{
	private DirectoryManager directory;
	private LabelManager labelManager;
	
	public ButtonActionConfirm(DirectoryManager directory, LabelManager labelManager) {
		this.directory = directory;
		this.labelManager = labelManager;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.directory.saveDirectories();
		} catch (Exception err) {
			labelManager.addText("Arquivo inválido");
		}
	}

}
