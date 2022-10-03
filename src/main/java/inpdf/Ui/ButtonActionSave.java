package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class ButtonActionSave implements ActionListener{
	List <JRadioButton> checkboxes;
	JComboBox comboBox;
	
	public ButtonActionSave(List<JRadioButton> checkboxes, JComboBox comboBox) {
		this.checkboxes = checkboxes;
		this.comboBox = comboBox;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public List <String> getSelectedBoxes(){
		ArrayList <String> checkedBoxes = new ArrayList<String>();
		checkboxes.forEach(box -> {
			if(box.isSelected()) checkedBoxes.add(box.getLabel());
		});
		return checkedBoxes;
	}
	
	public String getSelectedDocumentType() {
		return comboBox.getSelectedItem().toString();
	}
	
	
	
}


