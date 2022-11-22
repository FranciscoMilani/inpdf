package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import inpdf.DocumentType;
import inpdf.ExtractedTextArea;
import inpdf.Reader;

public class ButtonActionReadToDisplay implements ActionListener {
	private JComboBox comboBox;
	private ExtractedTextArea extractedArea;

	public ButtonActionReadToDisplay(JComboBox comboBox, ExtractedTextArea extractedArea) {
		this.comboBox = comboBox;
		this.extractedArea = extractedArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser("C:/");
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));	
		
		int res = fileChooser.showSaveDialog(null);
		if(res == JFileChooser.APPROVE_OPTION) {
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			
			try {
				DocumentType type =  Reader.readAndShowPDFText(file.toPath().toAbsolutePath(), extractedArea);
				if (((type != null && comboBox != null) && type != DocumentType.DECLARACAO_IMPOSTO_DE_RENDA)) {
					comboBox.setEnabled(false);
					comboBox.setSelectedItem(type);				
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
}