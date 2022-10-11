package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import inpdf.ExtractedTextArea;

public class ButtonChangeConfigStateAction implements ActionListener {
	
	private JComboBox comboBox;

	public ButtonChangeConfigStateAction(JComboBox comboBox) {
		this.comboBox = comboBox;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ExtractedTextArea.clear();
		comboBox.setEnabled(true);
	}
}