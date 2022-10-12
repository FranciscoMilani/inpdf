package inpdf;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import inpdf.Ui.ButtonActionClear;
import inpdf.Ui.ButtonActionConfirm;
import inpdf.Ui.ButtonActionExtract;
import inpdf.Ui.ButtonActionReadFile;
import inpdf.Ui.ButtonActionReadToDisplay;
import inpdf.Ui.ButtonActionSaveBoleto;
import inpdf.Ui.ButtonChangeConfigStateAction;
import inpdf.Ui.DropdownChangeAction;
import inpdf.Ui.LabelManager;
import inpdf.Ui.TableManager;  

public class Main {
	
	public static void main(String[] args) throws Exception{
		//interface
		JFrame frame = new JFrame();
		frame.setTitle("InPDF");
		frame.setSize(1000, 700);
		frame.setLocation(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	    frame.setLayout(null);	
		
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
		ButtonActionExtract extractAction = new ButtonActionExtract(labelManager);
		//ButtonActionConfirm confirmAction = new ButtonActionConfirm(directory, labelManager);
		extractButton.setBounds(820 ,580, 100, 50);  
		extractButton.addActionListener(extractAction);
		//extractButton.addActionListener(extractAction);
		
		//Options button
//		JButton optionsButton = new JButton("OPÇÕES");
//		ButtonActionOptions optionsAction = new ButtonActionOptions(frame, frame2);
//		optionsButton.setBounds(620 ,580, 100, 50);  
//		optionsButton.addActionListener(optionsAction);
		
		//Back button
//		JButton backButton = new JButton("VOLTAR");
//		ButtonActionBack backAction = new ButtonActionBack(frame, frame2);
//		backButton.setBounds(620 ,580, 100, 50);  
//		backButton.addActionListener(backAction);
		
		frame.add(selectFileButton);
//		frame.add(optionsButton);
		frame.add(extractButton);
		frame.add(clearButton);
		
		JPanel dropdown = new JPanel();
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
				 };
		
		final JComboBox<DocumentType> comboBox = new JComboBox<DocumentType>(dropdownOptionsEnum);
		comboBox.setVisible(true);
		dropdown.add(comboBox);
		
		comboBox.addActionListener(new DropdownChangeAction(comboBox));
			
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
	}
}