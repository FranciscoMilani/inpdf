package inpdf.Ui;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.javatuples.Triplet;

import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentType;

public class ButtonActionSaveBoleto implements ActionListener{
	JComboBox comboBox;
	JTable table = TableManager.table;
	
	public ButtonActionSaveBoleto(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (table.isEditing()) {
			table.getCellEditor().stopCellEditing();
		}
		
		if (!passValueChecks()) {			
			JOptionPane.showMessageDialog(null, 
										"Valores igual a zero ou negativos encontados. Corrija para salvar", 
										"Valores inválidos encontrados",
										MessageType.INFO.ordinal());
			
			return;
		}
		
		DocumentType selectedType = (DocumentType) comboBox.getSelectedItem();		
		List<Triplet<String, Integer, Boolean>> tripletList = new ArrayList<Triplet<String, Integer, Boolean>>();
		
		for (int i = 0; i < table.getRowCount(); i++) {
			String str = (String) table.getValueAt(i, 0);
			Integer integer = (Integer) table.getValueAt(i, 1);
			Boolean bool = (Boolean) table.getValueAt(i, 2);
			
			if (integer == null) {
				tripletList.add(new Triplet<String, Integer, Boolean>(str, integer, false));	
				continue;
			}
			else {
				tripletList.add(new Triplet<String, Integer, Boolean>(str, integer, bool));			
			}		
		}
		
		DocumentConfiguration config = DocumentConfigurationManager.getConfigurationFromType(selectedType);
		DocumentConfigurationManager.updateConfigurationValues(config, tripletList);
		DocumentConfigurationManager.allConfigsToJson(DocumentConfigurationManager.configTypeMap.values());
	}
	
	private Boolean passValueChecks() {
		for (int i = 0; i < table.getRowCount(); i++) {
			Integer line = (Integer) table.getValueAt(i, 1);
			if (line != null && line <= 0) {
				return false;
			}
		}
		
		return true;
	}
}