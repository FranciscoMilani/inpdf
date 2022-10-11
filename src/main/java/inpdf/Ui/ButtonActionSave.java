package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentField;
import inpdf.DocumentType;

public class ButtonActionSave implements ActionListener{
	static List <JRadioButton> checkboxes;
	static JComboBox comboBox;
	
	public ButtonActionSave(List<JRadioButton> checkboxes, JComboBox comboBox) {
		ButtonActionSave.checkboxes = checkboxes;
		ButtonActionSave.comboBox = comboBox;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(getSelectedBoxesString());
		System.out.println(getSelectedDocumentType());
		System.out.println("A");
		if (!getSelectedBoxesString().isEmpty()) {
			DocumentConfigurationManager.setConfigSelectedFields(getSelectedDocumentType(), getSelectedBoxesString());
			DocumentConfiguration config = DocumentConfigurationManager.getConfigurationFromType(getSelectedDocumentType());
			config.selectedFieldsString = getSelectedBoxesString();
		}
		else {
			System.out.println("Nenhum campo selecionado");
		}
	}
	
	public static ArrayList <String> getSelectedBoxesString(){
		ArrayList <String> checkedBoxes = new ArrayList<String>();
		checkboxes.forEach(box -> {
			if(box.isSelected()) checkedBoxes.add(box.getText());
		});
		return checkedBoxes;
	}
	
	public static String getSelectedDocumentTypeToString() {
		return comboBox.getSelectedItem().toString();
	}
	
	public static DocumentType getSelectedDocumentType() {
		return (DocumentType) comboBox.getSelectedItem();
	}
}