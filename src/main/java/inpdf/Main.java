package inpdf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import inpdf.Ui.ButtonActionBack;
import inpdf.Ui.ButtonActionClear;
import inpdf.Ui.ButtonActionConfirm;
import inpdf.Ui.ButtonActionExtract;
import inpdf.Ui.ButtonActionOptions;
import inpdf.Ui.ButtonActionReadFile;
import inpdf.Ui.ButtonActionReadToDisplay;
import inpdf.Ui.ButtonActionSave;
import inpdf.Ui.ButtonActionSaveBoleto;
import inpdf.Ui.ButtonChangeConfigStateAction;
import inpdf.Ui.DropdownChangeAction;
import inpdf.Ui.LabelManager;
import inpdf.Ui.TableManager;  

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
	
		
		//Confirm button: temporário pois vai ser automático
		JButton extractButton = new JButton("EXTRAIR");
		//ButtonActionExtract extractAction = new ButtonActionExtract();
		ButtonActionConfirm confirmAction = new ButtonActionConfirm(directory, labelManager);
		extractButton.setBounds(820 ,580, 100, 50);  
		extractButton.addActionListener(confirmAction);
		//extractButton.addActionListener(extractAction);
		
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
		frame.add(extractButton);
		frame.add(clearButton);
		frame2.add(backButton);
		
		//Dropdown
		JPanel dropdown = new JPanel();
		frame2.add(dropdown);
		JLabel dropdownLabel = new JLabel("Tipo de Documento");
		dropdownLabel.setVisible(true);
		dropdown.add(dropdownLabel);
		dropdown.setBounds(620, 200, 300, 50);
		 
		DocumentType[] dropdownOptionsEnum = { 
				 DocumentType.BOLETO_BANCARIO_BRADESCO, 
				 DocumentType.BOLETO_BANCARIO_SANTANDER,
				 DocumentType.BOLETO_BANCARIO_BANCO_DO_BRASIL,
				 DocumentType.BOLETO_BANCARIO_BANRISUL,
				 DocumentType.BOLETO_BANCARIO_ITAU,
				 DocumentType.BOLETO_BANCARIO_SICREDI,
//				 DocumentType.DECLARACAO_IMPOSTO_DE_RENDA,
				 };
		 
		//dropdown
		final JComboBox<DocumentType> comboBox = new JComboBox<DocumentType>(dropdownOptionsEnum);
		comboBox.setVisible(true);
		dropdown.add(comboBox);
		frame2.add(dropdown);
		 
		ArrayList <JRadioButton> boletoFieldOptions = new ArrayList <JRadioButton>();
		
		comboBox.addActionListener(e -> {
			
			
//			boletoFieldOptions.forEach(boletoOption -> {
//				 boletoOption.setSelected(false);
//			});		
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
		
		ArrayList <JRadioButton> irfFieldOptions = new ArrayList <JRadioButton>();
		
		comboBox.addActionListener(e -> {
			irfFieldOptions.forEach(irfOption -> {
				 irfOption.setSelected(false);
			});		
		});
		
		
		String[] irfFieldNames = DocumentConfigurationManager.irfFieldNames;
		for (String str : irfFieldNames) {
			JRadioButton rbtn = new JRadioButton(str);
			rbtn.setBounds(100, yCurrent, 150, 20);
			irfFieldOptions.add(rbtn);
			frame2.add(rbtn);
			yCurrent += yOffset;
		}
		
		DropdownChangeAction dropdownAction = new DropdownChangeAction(frame2, boletoFieldOptions, irfFieldOptions, comboBox);
		comboBox.addActionListener(dropdownAction);
		
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

		
		JButton saveButton = new JButton("SALVAR");

		String selectedDoc = comboBox.getSelectedItem().toString();
		
		ButtonActionSave saveAction = new ButtonActionSave(boletoFieldOptions, comboBox);
		saveButton.setBounds(420, 580, 100, 50);  
		saveButton.addActionListener(saveAction);	
		frame2.add(saveButton);
		
		//saveAction.getSelectedBoxes(); ESSE MÉTODO PEGA OS CHECKBOXES DAS CONFIGURAÇÕES, RETORNA UM ARRAY DE STRINGS COM OS SELECIONADOS
		//saveAction.getSelectedDocumentType(); ESSE MÉTODO PEGA O TIPO DE DOCUMENTO SELECIONADO, BOLETOS OU IRF, RETORNA UMA STRING
		
		
		// --------------------------------------------------
		
		JFrame frame3 = new JFrame("Configurador do InPDF");
		//frame3.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame3.setSize(640, 480);
		JTabbedPane tabPane = new JTabbedPane();
		JPanel mainBoletoPanel = new JPanel(new BorderLayout());
		JPanel mainIRPFPanel = new JPanel();
		JPanel textAreaMainPanel = new JPanel(new BorderLayout());
		JPanel textAreaPanel = new JPanel(new BorderLayout());
		JPanel textAreaBottomPanel = new JPanel();
		JPanel tablePanel = new JPanel(new BorderLayout());
		JScrollPane tableScrollPane = new JScrollPane(tablePanel);
		ExtractedTextArea extractedTextArea = new ExtractedTextArea(null);
		
		String[] columnHeaders = new String[] {"Campo", "Linha", "Selecionar"};
		DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, 24) {
			// Bloqueia a edição da primeira coluna
		    public boolean isCellEditable(int row, int column) {
		        return column != 0;
			}
		    
		    // Cria colunas de tipos diferentes
		    public Class<?> getColumnClass(int column) {
		    	if (column == 2) {
		    		return Boolean.class;
		    	}
		    	else if (column == 1) {
	    			return Integer.class;
		    	}
			    else if (column == 0) {
			    	return String.class;
			    }
			    else {
			    	return String.class;
			    }
		    }
		};

		JTable configTable = new JTable(tableModel);
		new TableManager(configTable);
		
		textAreaPanel.setBorder(new TitledBorder("Texto Extraído"));
        JScrollPane textAreaScrollPane = new JScrollPane(textAreaPanel);
        textAreaPanel.add(extractedTextArea, BorderLayout.CENTER);
        
        JButton jbtnSearch = new JButton("Procurar");
        ButtonActionReadToDisplay readFileToDisplayAction = new ButtonActionReadToDisplay(directory, labelManager, comboBox);
        jbtnSearch.addActionListener(readFileToDisplayAction);
        
        JButton jbtnClear = new JButton("Limpar");
        ButtonChangeConfigStateAction clearTextAction = new ButtonChangeConfigStateAction(comboBox);
        jbtnClear.addActionListener(clearTextAction);
        
        textAreaMainPanel.add(textAreaScrollPane, BorderLayout.CENTER);
        textAreaMainPanel.add(textAreaBottomPanel, BorderLayout.SOUTH);
        textAreaBottomPanel.add(jbtnSearch);
        textAreaBottomPanel.add(jbtnClear);
		
		tablePanel.setBorder(new TitledBorder("Configurações"));		
		tablePanel.add(configTable, BorderLayout.CENTER);
		tablePanel.add(configTable.getTableHeader(), BorderLayout.NORTH);

		GridLayout grid = new GridLayout(1,2);
		grid.setHgap(10);
		grid.setVgap(10);
		mainBoletoPanel.setLayout(grid);
		mainBoletoPanel.add(textAreaMainPanel);
		mainBoletoPanel.add(tableScrollPane);

		tabPane.addTab("Boletos", mainBoletoPanel);
		tabPane.addTab("IRPF", mainIRPFPanel);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton jbtnSave = new JButton("Salvar");
		JButton jbtnBack = new JButton("Voltar");
		jbtnSave.addActionListener(new ButtonActionSaveBoleto(comboBox));
		
		buttonPanel.add(jbtnSave);
		buttonPanel.add(jbtnBack);
		buttonPanel.add(dropdown);
		tablePanel.add(buttonPanel, BorderLayout.PAGE_END);

		frame3.add(tabPane);
		frame3.pack();
		frame3.setVisible(true);
	
//		JButton selectFileConfigButton = new JButton("Arquivo para configurar");
//		ButtonActionReadFile barf = new ButtonActionReadFile(directory, labelManager, comboBox);
//		selectFileConfigButton.setBounds(820, 580, 100, 50);
//		selectFileConfigButton.addActionListener(barf);	
//		frame2.add(selectFileConfigButton);
	}
}