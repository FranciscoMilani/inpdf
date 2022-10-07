package inpdf;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import inpdf.Ui.ButtonActionBack;
import inpdf.Ui.ButtonActionClear;
import inpdf.Ui.ButtonActionConfirm;
import inpdf.Ui.ButtonActionOptions;
import inpdf.Ui.ButtonActionReadFile;
import inpdf.Ui.ButtonActionSave;
import inpdf.Ui.JCheckBoxList;
import inpdf.Ui.LabelManager;  

public class Main {
	
	public static void main(String[] args) throws Exception{
		
		//interface
		JFrame frame = new JFrame();
		frame.setTitle("PDF READER");
		frame.setSize(1000, 700);
		frame.setLocation(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	    frame.setLayout(null);	
	    
	    JFrame frame2 = new JFrame();
		frame2.setTitle("OPTIONS");
		frame2.setSize(1000, 700);
		frame2.setLocation(500, 300);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(false);
		frame2.setLocationRelativeTo(null);
	    frame2.setLayout(null);	
		
		JLabel label = new JLabel();	
		DirectoryManager directory = new DirectoryManager();
		LabelManager labelManager = new LabelManager(frame);
		
		//File button
		JButton selectFileButton = new JButton("Selecione um arquivo...");
		ButtonActionReadFile readFileAction = new ButtonActionReadFile(directory, labelManager);
		selectFileButton.setBounds(400 ,280, 200, 100);  
		selectFileButton.addActionListener(readFileAction);
	
		
		//Clear button
		JButton clearButton = new JButton("LIMPAR");
		ButtonActionClear cancelAction = new ButtonActionClear(directory, labelManager);
		clearButton.setBounds(80, 580, 100, 50);  
		clearButton.addActionListener(cancelAction);
	
		
		//Confirm button
		JButton confirmButton = new JButton("OK");
		ButtonActionConfirm confirmAction = new ButtonActionConfirm(directory, labelManager);
		confirmButton.setBounds(820 ,580, 100, 50);  
		confirmButton.addActionListener(confirmAction);
		
		//Options button
		JButton optionsButton = new JButton("OPÇÕES");
		ButtonActionOptions optionsAction = new ButtonActionOptions(frame, frame2);
		optionsButton.setBounds(620 ,580, 100, 50);  
		optionsButton.addActionListener(optionsAction);
		
		//Back button
		JButton backButton = new JButton("VOLTAR");
		ButtonActionBack backAction = new ButtonActionBack(frame, frame2);
		backButton.setBounds(620 ,580, 100, 50);  
		backButton.addActionListener(backAction);
		
		frame.add(selectFileButton);
		frame.add(optionsButton);
		frame.add(confirmButton);
		frame.add(clearButton);
		frame2.add(backButton);
		
		//Dropdown
		JPanel dropdown = new JPanel();
		frame2.add(dropdown);
		JLabel dropdownLabel = new JLabel("Selecione um tipo de documento...");
		dropdownLabel.setVisible(true);
		dropdown.add(dropdownLabel);
		dropdown.setBounds(620, 200, 300, 50);
//		String[] dropdownOptions = { "Declaração de imposto de renda",
//				 "Boleto bancário Bradesco", 
//				 "Boleto bancário Santander",
//				 "Boleto bancário Banco do Brasil",
//				 "Boleto bancário Banrisul",
//				 "Boleto bancário Itaú",
//				 "Boleto bancário Sicredi"
//				 };
		 
		DocumentType[] dropdownOptionsEnum = { DocumentType.DECLARACAO_IMPOSTO_DE_RENDA,
				 DocumentType.BOLETO_BANCARIO_BRADESCO, 
				 DocumentType.BOLETO_BANCARIO_SANTANDER,
				 DocumentType.BOLETO_BANCARIO_BANCO_DO_BRASIL,
				 DocumentType.BOLETO_BANCARIO_BANRISUL,
				 DocumentType.BOLETO_BANCARIO_ITAU,
				 DocumentType.BOLETO_BANCARIO_SICREDI
				 };
		 
		//dropdown
		final JComboBox<DocumentType> comboBox = new JComboBox<DocumentType>(dropdownOptionsEnum);
		comboBox.setVisible(true);
		dropdown.add(comboBox);
		//String[] items = (String[])comboBox.getSelectedItem();
		//String selectedDocument = ((String[]) comboBox.getSelectedItem())[0];
		frame2.add(dropdown);
		 
		ArrayList <JRadioButton> boletoFieldOptions = new ArrayList <JRadioButton>();
		
		comboBox.addActionListener(e -> {
			boletoFieldOptions.forEach(boletoOption -> {
				 boletoOption.setSelected(false);
			});		
		});
		
		int yCurrent = 100;
		final int yOffset = 20;
		
		String[] boletoFieldNames = DocumentConfigurationManager.boletoFieldNames;
		for (String str : boletoFieldNames) {
			JRadioButton rbtn = new JRadioButton(str);
			rbtn.setBounds(100, yCurrent, 150, 20);
			boletoFieldOptions.add(rbtn);
			frame2.add(rbtn);
			yCurrent += yOffset;
		}
		
//		JPanel optionsPanel = new JPanel(new BorderLayout());
//		optionsPanel.setSize(500, 500);
//		optionsPanel.setVisible(true);	
//		comboBox.addActionListener( e -> {
//			int yCurrent = 100;
//			int yOffset = 20;
//			frame2.remove(optionsPanel);
//			
//			boletoFieldOptions.removeAll(boletoFieldOptions);
//			JComboBox<DocumentType> cBox = (JComboBox<DocumentType>) e.getSource();
//			DocumentType newSelectedType = (DocumentType) cBox.getSelectedItem();	
//			DocumentConfiguration config = DocumentConfigurationManager.getConfiguration(newSelectedType);
//			
//			for (DocumentField field : config.fields) {	
//				JRadioButton rbtn = new JRadioButton(field.getFieldNameAtIndexOrFirst(0));
//				optionsPanel.add(rbtn);
//				rbtn.setBounds(100, yCurrent, 150, 20);
//				yCurrent += yOffset;
//				boletoFieldOptions.add(rbtn);
//			}
//			frame2.add(optionsPanel);
//		});
	
  
	    //checkboxes boleto
//		JRadioButton vencimento = new JRadioButton("Data do vencimento");
//		vencimento.setBounds(100, 100, 150, 20);
//		
//		JRadioButton dataDocumento = new JRadioButton("Data do documento");
//		dataDocumento.setBounds(100, 130, 150, 20);
//		
//		JRadioButton local = new JRadioButton("Local");
//		local.setBounds(100, 160, 100, 20);
//		
//		JRadioButton valor = new JRadioButton("Valor");
//		valor.setBounds(100, 190, 100, 20);
//		
//		JRadioButton beneficiario = new JRadioButton("Beneficiario");
//		beneficiario.setBounds(100, 220, 100, 20);
//		
//		JRadioButton pagador = new JRadioButton("Pagador");
//		pagador.setBounds(100, 250, 100, 20);
		
		
		//checkboxes irpf	
//		boletoFieldOptions.add(vencimento);
//		boletoFieldOptions.add(dataDocumento);
//		boletoFieldOptions.add(local);
//		boletoFieldOptions.add(valor);
//		boletoFieldOptions.add(beneficiario);
//		boletoFieldOptions.add(pagador);
//
//		
//		frame2.add(vencimento);
//		frame2.add(local);
//		frame2.add(valor);
//		frame2.add(beneficiario);
//		frame2.add(pagador);
//		frame2.add(dataDocumento);
		
		JButton saveButton = new JButton("SALVAR");

		String selectedDoc = comboBox.getSelectedItem().toString();
		
		ButtonActionSave saveAction = new ButtonActionSave(boletoFieldOptions, comboBox);
		saveButton.setBounds(420, 580, 100, 50);  
		saveButton.addActionListener(saveAction);	
		frame2.add(saveButton);
		
		//saveAction.getSelectedBoxes(); ESSE MÉTODO PEGA OS CHECKBOXES DAS CONFIGURAÇÕES, RETORNA UM ARRAY DE STRINGS COM OS SELECIONADOS
		//saveAction.getSelectedDocumentType(); ESSE MÉTODO PEGA O TIPO DE DOCUMENTO SELECIONADO, BOLETOS OU IRF, RETORNA UMA STRING
	
	}
}