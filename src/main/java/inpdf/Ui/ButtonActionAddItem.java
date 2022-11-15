package inpdf.Ui;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import inpdf.irpf.ConfigTable;
import inpdf.irpf.IAddable;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRSection;
import inpdf.irpf.IRSectionsEnum;

public class ButtonActionAddItem implements ActionListener {

	ConfigTable table;
	
	ButtonActionAddItem(ConfigTable table) {
		this.table = table;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IRSectionsEnum s = table.selectedSection;
		IAddable section = (IAddable) IRDocumentManager.getSection(s);
		
		int res = JOptionPane.showConfirmDialog(null, "Deseja configurar um novo campo para essa seção? Certifique-se de salvar a configuração atual.", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res == 0) {
			// salvar antes sozinho?
			table.resetValuesToNullExcept(0);
			section.createItem();		
		}	
	}
}