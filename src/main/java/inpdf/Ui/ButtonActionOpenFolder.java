package inpdf.Ui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import inpdf.DirectoryManager;

public class ButtonActionOpenFolder implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {	
		try {
			switch (Integer.parseInt(e.getActionCommand())) {
			case 0: 
				Desktop.getDesktop().open(DirectoryManager.getInputDirectoryPath().toFile());
				break;		
			case 1:
				Desktop.getDesktop().open(DirectoryManager.getOutputDirectoryPath().toFile());
				break;
			case 2:
				Desktop.getDesktop().open(new File("inpdf.log"));
				break;
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1);
			e1.printStackTrace();
		}
	}
}
