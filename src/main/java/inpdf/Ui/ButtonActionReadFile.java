package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import inpdf.DirectoryManager;
import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentType;
import inpdf.ExtractedTextArea;
import inpdf.Reader;

public class ButtonActionReadFile implements ActionListener{
	private LabelManager labelManager;
	private JComboBox comboBox;

	public ButtonActionReadFile(DirectoryManager directory, LabelManager labelManager, JComboBox comboBox) {
		this.comboBox = comboBox;
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
			
			try {
				Reader r = new Reader();
				DocumentType type =  r.readAndShowPDFText(file.toPath().toAbsolutePath());
				if (type != null) {
					comboBox.setEnabled(false);
					comboBox.setSelectedItem(type);				
				}
			} catch (Exception err) {
				err.printStackTrace();
				labelManager.addText("Arquivo inválido");
			}
		}
	}
}