package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import inpdf.DirectoryManager;
import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentField;
import inpdf.DocumentType;

public class ButtonActionSaveBoleto implements ActionListener{
	JComboBox comboBox;
	
	public ButtonActionSaveBoleto(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Integer> lineColumnValues = TableManager.getColumnValues(1);
		ArrayList<Boolean> selectedColumnValues = TableManager.getColumnValues(2);
		System.out.println(comboBox.getSelectedItem().toString());
		
//		if (!getSelectedBoxesString().isEmpty()) {
//			DocumentConfigurationManager.setConfigSelectedFields(getSelectedDocumentType(), getSelectedBoxesString());
//			DocumentConfiguration config = DocumentConfigurationManager.getConfigurationFromType(getSelectedDocumentType());
//			config.selectedFieldsString = getSelectedBoxesString();
//		}
//		else {
//			System.out.println("Nenhum campo selecionado");
//		}
	}
	
	private void saveBoletoConfig() {
		
	}
}