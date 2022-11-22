package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Engineering;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import org.javatuples.Pair;

import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentField;
import inpdf.DocumentType;
import inpdf.ExtractedTextArea;

public class DropdownChangeAction implements ActionListener {
	private JComboBox comboBox;
	private Object previousType;
	
	public DropdownChangeAction(JComboBox comboBox) {
		previousType = comboBox.getSelectedItem();
		this.comboBox = comboBox;	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTable t = TableManager.table;
		
		if (t.isEditing()) {
			t.getCellEditor().stopCellEditing();
		}

		if (!previousType.equals(comboBox.getSelectedItem())) {
			previousType = comboBox.getSelectedItem();
			TableManager.updateTableCells((DocumentType) comboBox.getSelectedItem());
			//updateTableCells();
		}
	}
	
//	private void updateTableCells() {
//		DocumentConfiguration config = DocumentConfigurationManager.getConfigurationFromType((DocumentType) comboBox.getSelectedItem());
//		List<DocumentField> fields = config.fields;	
//		
//		TableManager.resetValuesToNullAndFalse();
//		
//		if(fields == null) {
//			return;
//		}
//		
//		Integer[] lines = new Integer[fields.size()];
//		Boolean[] bools = new Boolean[fields.size()];
//			
//		for (int i = 0; i < fields.size(); i++) {
//			lines[i] = fields.get(i).getLineLocated();
//			bools[i] = fields.get(i).getShouldRead();
//		}
//
//		TableManager.setLineValues(lines);
//		TableManager.setBoolValues(bools);	
//	}
}