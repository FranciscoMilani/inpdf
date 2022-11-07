package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import inpdf.ExtractedTextArea;

public class ButtonChangeConfigStateAction implements ActionListener {
	
	private JComboBox comboBox;
	private ExtractedTextArea extractedArea;

	public ButtonChangeConfigStateAction(JComboBox comboBox, ExtractedTextArea extractedArea) {
		this.comboBox = comboBox;
		this.extractedArea = extractedArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		extractedArea.clear();
		comboBox.setEnabled(true);
	}
}