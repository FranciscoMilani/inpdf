package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Engineering;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentField;
import inpdf.DocumentType;
import inpdf.ExtractedTextArea;

public class DropdownChangeAction implements ActionListener {
	private JFrame frame;
	private ArrayList<JRadioButton> boletoFieldOptions;
	private ArrayList<JRadioButton> irfFieldOptions;
	private JComboBox comboBox;
	private Object previousBox;
	
	public DropdownChangeAction(
			JFrame frame, 
			ArrayList<JRadioButton> boletoFieldOptions, 
			ArrayList<JRadioButton> irfFieldOptions, 
			JComboBox comboBox
			) 
	{
		previousBox = comboBox.getSelectedItem();
//		this.frame = frame;
//		this.boletoFieldOptions = boletoFieldOptions;
//		this.irfFieldOptions = irfFieldOptions;
		this.comboBox = comboBox;
//		
//		this.irfFieldOptions.forEach(opt -> this.frame.remove(opt));
//		this.boletoFieldOptions.forEach(opt -> this.frame.remove(opt));
//		
//		this.boletoFieldOptions.forEach(opt ->{
//			this.frame.add(opt);
//		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!previousBox.equals(comboBox.getSelectedItem())) {
			previousBox = comboBox.getSelectedItem();
			//ExtractedTextArea.clear();
			updateTableCells();
		}
		
		
//		DocumentType selectedDoc = (DocumentType) this.comboBox.getSelectedItem();
//		System.out.println(selectedDoc);
//		
//		
//		if(selectedDoc.equals(DocumentType.DECLARACAO_IMPOSTO_DE_RENDA)) {
//			updateOptions(boletoFieldOptions, irfFieldOptions);
//		}else {
//			updateOptions(irfFieldOptions, boletoFieldOptions);
//		}
	}
	
	private void updateTableCells() {
		DocumentConfiguration config = DocumentConfigurationManager.getConfigurationFromType((DocumentType) comboBox.getSelectedItem());
		List<DocumentField> fields = config.fields;	
		
		if(fields == null) {
			TableManager.resetValuesToNull();	
			return;
		}
		
		Integer[] lines = new Integer[fields.size()];
		Boolean[] bools = new Boolean[fields.size()];
			
		for (int i = 0; i < fields.size(); i++) {
			lines[i] = fields.get(i).getLineLocated();
			bools[i] = fields.get(i).getShouldRead();
		}

		TableManager.setLineValues(lines);
		TableManager.setBoolValues(bools);	
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




















 








