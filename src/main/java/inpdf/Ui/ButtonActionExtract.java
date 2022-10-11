package inpdf.Ui;

import inpdf.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;

import inpdf.Reader;

public class ButtonActionExtract implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(DirectoryManager.getInputDirectoryPath() != null) {
			try {
				Path documentDir = DirectoryManager.getInputDirectoryPath();
				
				if (Reader.checkFileConformity(documentDir)) {
					Reader reader = new Reader();
					reader.ReadPDF(documentDir);
				} 
				else {
					System.out.println("Arquivo não é formato PDF");
				}			
			} 
			catch (IOException er) {
				er.printStackTrace();
				throw new RuntimeException();
			}
		}	
	}
}
