package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;

import inpdf.DirectoryManager;
import inpdf.Reader;

public class ButtonActionExtract implements ActionListener {
	LabelManager label;
	
	public ButtonActionExtract(LabelManager label) {
		this.label = label;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		if(DirectoryManager.getInputDirectoryPath() != null) {
//			try {
//				Path documentDir = DirectoryManager.getInputDirectoryPath();
//				
//				if (Reader.checkFileConformity(documentDir)) {
//					Reader reader = new Reader();
//					reader.ReadPDF(documentDir);
//				} 
//				else {
//					System.out.println("Arquivo não é formato PDF");
//				}			
//			} 
//			catch (IOException er) {
//				label.addText("Houve um erro");
//				er.printStackTrace();
//				throw new RuntimeException();
//			}
//		}	
	}
}