package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import inpdf.irpf.ConfigTable;

public class ButtonActionAddItem implements ActionListener {
	ConfigTable table;
	JButton saveBtn;
	
	ButtonActionAddItem(ConfigTable table, JButton saveBtn) {
		this.table = table;
		this.saveBtn = saveBtn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		int res = JOptionPane.showConfirmDialog(null, 
				"Deseja configurar um novo campo para essa seção? Certifique-se de salvar a configuração atual.", 
				"Aviso", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE);
		
		if (res == JOptionPane.YES_OPTION) {
			table.resetValuesToNullExcept(0);
			saveBtn.setEnabled(true);
			table.setEnabled(true);	
		}	
	}
}