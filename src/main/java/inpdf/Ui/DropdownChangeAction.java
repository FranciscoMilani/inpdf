package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class DropdownChangeAction implements ActionListener {
	private JFrame frame;
	private ArrayList<JRadioButton> boletoFieldOptions;
	private ArrayList<JRadioButton> irfFieldOptions;
	private JComboBox comboBox;
	
	public DropdownChangeAction(
			JFrame frame, 
			ArrayList<JRadioButton> boletoFieldOptions, 
			ArrayList<JRadioButton> irfFieldOptions, 
			JComboBox comboBox
			) 
	{
		this.frame = frame;
		this.boletoFieldOptions = boletoFieldOptions;
		this.irfFieldOptions = irfFieldOptions;
		this.comboBox = comboBox;
		
		this.irfFieldOptions.forEach(opt -> this.frame.remove(opt));
		this.boletoFieldOptions.forEach(opt -> this.frame.remove(opt));
		
		this.boletoFieldOptions.forEach(opt ->{
			this.frame.add(opt);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedDoc = this.comboBox.getSelectedItem().toString();
		System.out.println(selectedDoc);
		if(selectedDoc == "DECLARACAO_IMPOSTO_DE_RENDA") {
			updateOptions(boletoFieldOptions, irfFieldOptions);
		}else {
			updateOptions(irfFieldOptions, boletoFieldOptions);
		}
	}
	
	private void updateOptions(ArrayList<JRadioButton> oldOpts, ArrayList<JRadioButton> newOpts) {
		oldOpts.forEach(opt -> {
			this.frame.remove(opt);
		});
		
		newOpts.forEach(opt -> {
			this.frame.add(opt);
		});
		
		this.frame.repaint();
	}

}




















 








