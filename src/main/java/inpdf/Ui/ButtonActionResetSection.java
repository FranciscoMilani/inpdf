package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import inpdf.irpf.ConfigTable;
import inpdf.irpf.IRDocument;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRSectionsEnum;

public class ButtonActionResetSection implements ActionListener {

	IRSectionsEnum selected;
	
	ButtonActionResetSection(ConfigTable table, IRSectionsEnum selected) {
		this.selected = selected;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}