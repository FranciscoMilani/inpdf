package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import inpdf.irpf.ConfigTable;
import inpdf.irpf.IAddable;
import inpdf.irpf.IRDocument;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRSection;
import inpdf.irpf.IRSectionsEnum;

public class ButtonActionResetSection implements ActionListener {

	ConfigTable table;
	
	ButtonActionResetSection(ConfigTable table) {
		this.table = table;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		IRSectionsEnum s = table.selectedSection;
		IRSection addable = IRDocumentManager.getSection(s);
		if (addable instanceof IAddable) {
			IAddable ad = (IAddable) addable;
			ad.resetItems();
			table.resetValuesToNullExcept(0);
		}
	}
}