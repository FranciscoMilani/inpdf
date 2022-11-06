package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import inpdf.DocumentType;
import inpdf.Reader;

public class ButtonActionReadToDisplay implements ActionListener {
	private JComboBox comboBox;

	public ButtonActionReadToDisplay(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser("C:/");
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));	
		
		int res = fileChooser.showSaveDialog(null);
		if(res == JFileChooser.APPROVE_OPTION) {
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			
			try {
				Reader r = new Reader();
				DocumentType type =  r.readAndShowPDFText(file.toPath().toAbsolutePath());
				if (type != null) {
					comboBox.setEnabled(false);
					comboBox.setSelectedItem(type);				
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
}