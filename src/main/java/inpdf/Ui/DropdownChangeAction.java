package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTable;

import inpdf.DocumentType;

public class DropdownChangeAction implements ActionListener {
	private JComboBox<?> comboBox;
	private Object previousType;
	
	public DropdownChangeAction(JComboBox<?> comboBox) {
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
		}
	}
}