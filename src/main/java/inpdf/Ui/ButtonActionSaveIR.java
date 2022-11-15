package inpdf.Ui;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.xml.transform.Templates;

import org.javatuples.Triplet;

import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentType;
import inpdf.irpf.ConfigTable;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRField;
import inpdf.irpf.IRSection;
import inpdf.irpf.IRSectionsEnum;

public class ButtonActionSaveIR implements ActionListener{
	JComboBox comboBox;
	JTable table;
	
	public ButtonActionSaveIR(JComboBox comboBox, JTable table) {
		this.comboBox = comboBox;
		this.table = table;
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
		
		// TODO: PASSAR PRA CLASSE DOCUMENTMANAGER?
		IRSectionsEnum selectedType = (IRSectionsEnum) comboBox.getSelectedItem();
		List<IRField> fields = new ArrayList<IRField>();
		for (int i = 0; i < table.getRowCount(); i++) {		
			String name = table.getValueAt(i, 0).toString();
			Integer line = (Integer) table.getValueAt(i, 1);
			Boolean read = (Boolean) table.getValueAt(i, 2);
			
			IRField temp = new IRField(name);		
			if (line == null) {
				temp.setLine(line);
				temp.setRead(false);
			} else {
				temp.setLine(line);
				
				if (read == null) {
					temp.setRead(false);
				} else {
					temp.setRead(read);
				}
			}
			
			fields.add(temp);
		}
		
		IRSection section = IRDocumentManager.getSection(selectedType);
		if (section != null) {
			section.setFieldsValues(fields);
		}
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