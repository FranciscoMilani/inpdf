package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import inpdf.irpf.ConfigTable;
import inpdf.irpf.IAddable;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRSectionsEnum;

public class ButtonActionResetSection implements ActionListener {

	ConfigTable table;
	
	ButtonActionResetSection(ConfigTable table) {
		this.table = table;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		IRSectionsEnum s = table.selectedSection;			
		IAddable addable = (IAddable) IRDocumentManager.getSection(s);
		
		if (!addable.getItems().isEmpty()) {
			int res = JOptionPane.showOptionDialog(null, 
					"Deseja mesmo resetar esta seção? Todas as configurações para ela serão excluídas.", 
					"Aviso", 
					JOptionPane.OK_CANCEL_OPTION, 
					JOptionPane.WARNING_MESSAGE, 
					null, null, null);
			
			if (res == JOptionPane.OK_OPTION) {
				addable.resetItems();
				table.resetValuesToNullExcept(0);
			}
		}
	}
}