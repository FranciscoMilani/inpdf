package inpdf.Ui;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import inpdf.irpf.IAddable;
import inpdf.irpf.IRDocumentManager;
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
		JButton saveBtn = (JButton) e.getSource();
		
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
		
		
		IRSectionsEnum selectedType = (IRSectionsEnum) comboBox.getSelectedItem();	
		IRSection section = IRDocumentManager.getSection(selectedType);
		IRDocumentManager.updateSectionValues(section);
		
		// TODO: Gson entra em loop, corrigir
		if (!(section instanceof IAddable)) {
			saveBtn.setEnabled(true);
			IRDocumentManager.createJsonConfig();
		} else {
			saveBtn.setEnabled(false);
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