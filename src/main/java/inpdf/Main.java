package inpdf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import inpdf.Ui.ButtonActionReadToDisplay;
import inpdf.Ui.ButtonActionSaveBoleto;
import inpdf.Ui.ButtonActionSaveGeneralConfig;
import inpdf.Ui.ButtonActionSwitchFrame;
import inpdf.Ui.ButtonActionWatcher;
import inpdf.Ui.ButtonChangeConfigStateAction;
import inpdf.Ui.DirectoryConfigPanel;
import inpdf.Ui.DropdownChangeAction;
import inpdf.Ui.IRScreen;
import inpdf.Ui.SpringUtilities;
import inpdf.Ui.TableManager;
import inpdf.irpf.IRDocumentManager;
import inpdf.watcher.WatcherService;  

public class Main {
	public static void main(String[] args) throws Exception {	
		
		// WatchService
		WatcherService watcherRunnable = new WatcherService();
		Thread thread = new Thread(watcherRunnable, "Watcher");
		thread.start();
		
		// Interface
		// ========= FRAME 1 ==========
		JFrame frame = new JFrame();
		frame.setTitle("InPDF");
		frame.setLayout(new BorderLayout());
		frame.setSize(640, 480);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// ========= FRAME 3 ==========
		JFrame frame3 = new JFrame("Configurador de Documentos");
		frame3.setSize(1280, 960);
		frame3.setLocationRelativeTo(null);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// ========= FRAME 4 ==========
		JFrame frame4 = new JFrame("Configurador do InPDF");
		frame4.setLayout(new BorderLayout());
		frame4.setSize(640, 480);
		frame4.setResizable(false);
		frame4.setBackground(Color.LIGHT_GRAY);
		frame4.setLayout(new BorderLayout());
		frame4.setLocationRelativeTo(null);
		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// ========= FRAME 1 COMPONENTS ==========	
		//Watcher button
		JButton watcherButton = new JButton("Interromper");
		ButtonActionWatcher watcherAction = new ButtonActionWatcher(watcherRunnable, thread); 
		watcherButton.setPreferredSize(new Dimension(150, 25));
		watcherButton.addActionListener(watcherAction);
		
		//Document config button
		JButton docConfigButton = new JButton("Configurador de documentos");
		ButtonActionSwitchFrame switchAction = new ButtonActionSwitchFrame(frame, frame3);
		docConfigButton.addActionListener(switchAction);
		docConfigButton.setPreferredSize(new Dimension(200, 40));
		
		//InPDF config button
		JButton programConfigButton = new JButton("Configurador do programa");
		switchAction = new ButtonActionSwitchFrame(frame, frame4); 
		programConfigButton.addActionListener(switchAction);
		programConfigButton.setPreferredSize(new Dimension(200, 40));
		
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
		
		JPanel centerP = new JPanel(new GridBagLayout());
		JPanel bottomP = new JPanel();
		centerP.add(docConfigButton, gbc);
		centerP.add(programConfigButton, gbc);
		bottomP.add(watcherButton);
		
		frame.add(centerP, BorderLayout.CENTER);
		frame.add(bottomP, BorderLayout.SOUTH);
		
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
		
		
		
		
		
		// ========= FRAME 3 COMPONENTS ==========
		
		JTabbedPane tabPane = new JTabbedPane();
		JPanel mainBoletoPanel = new JPanel(new BorderLayout());
		JPanel mainIRPFPanel = new JPanel();
		
		JPanel textAreaMainPanel = new JPanel(new BorderLayout());
		JPanel textAreaPanel = new JPanel(new BorderLayout());
		JPanel textAreaBottomPanel = new JPanel();
		ExtractedTextArea extractedTextArea = new ExtractedTextArea();
		
		JPanel tableMainPanel = new JPanel(new BorderLayout());
		JPanel tablePanel = new JPanel(new BorderLayout());
		JPanel tableBottomPanel = new JPanel(new FlowLayout());
		
		// PANES
		IRScreen irScreen = new IRScreen(frame, frame3);
		tabPane.addTab("Boletos", mainBoletoPanel);
		tabPane.addTab("IRPF", irScreen);
		
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
		
		// ================ MAIN AREA ================
		GridLayout grid = new GridLayout(1,2);
		grid.setHgap(10);
		grid.setVgap(10);
		mainBoletoPanel.setLayout(grid);
		
		
		// ================ TEXT AREA ================
		textAreaMainPanel.setBorder(new TitledBorder("Texto Extraído"));
        JScrollPane textAreaScrollPane = new JScrollPane(textAreaPanel);
        textAreaPanel.add(extractedTextArea, BorderLayout.CENTER);

        JButton jbtnSearch = new JButton("Procurar");
        ButtonActionReadToDisplay readFileToDisplayAction = new ButtonActionReadToDisplay(comboBox, extractedTextArea);
        jbtnSearch.addActionListener(readFileToDisplayAction);
        
        JButton jbtnClear = new JButton("Limpar");
        ButtonChangeConfigStateAction clearTextAction = new ButtonChangeConfigStateAction(comboBox, extractedTextArea);
        jbtnClear.addActionListener(clearTextAction);
        
        textAreaMainPanel.add(textAreaScrollPane, BorderLayout.CENTER);
        textAreaMainPanel.add(textAreaBottomPanel, BorderLayout.SOUTH);
        textAreaBottomPanel.add(jbtnSearch);
        textAreaBottomPanel.add(jbtnClear);
        
        
		// ================ TABLE AREA ================
        JScrollPane tableScrollPane = new JScrollPane(tablePanel);
		JTable table = new JTable(tableModel);
		new TableManager(table);
		
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		tablePanel.add(table, BorderLayout.CENTER);
		
		tableMainPanel.setBorder(new TitledBorder("Configurações"));
		tableMainPanel.add(tableScrollPane, BorderLayout.CENTER);	
		tableMainPanel.add(tableBottomPanel, BorderLayout.SOUTH);
			
		// BOTTOM BUTTONS
		JButton jbtnSave = new JButton("Salvar");
		jbtnSave.addActionListener(new ButtonActionSaveBoleto(comboBox));
		JButton jbtnBack = new JButton("Voltar");
		jbtnBack.addActionListener(new ButtonActionSwitchFrame(frame3, frame));
		//jbtnBack.addActionListener(new ButtonActionSaveBoleto(comboBox));
	
		tableBottomPanel.add(jbtnSave);
		tableBottomPanel.add(jbtnBack);
		tableBottomPanel.add(dropdown);
		
		// MAIN PANEL ADDITIONS	
		mainBoletoPanel.add(textAreaMainPanel);
		mainBoletoPanel.add(tableMainPanel);
			
		frame3.add(tabPane);
		
		
		
		// ========= FRAME 4 COMPONENTS ==========		
		JTextPane titlePane = new JTextPane();
		JPanel middlePanel = new JPanel(new GridBagLayout());
		JPanel bottomPanel = new JPanel();
		JPanel innerMiddlePanel = new JPanel(new SpringLayout());
		
		// titlePane
		StyledDocument doc = titlePane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(center, 25);
		StyleConstants.setSpaceAbove(center, 20);
		StyleConstants.setSpaceBelow(center, 20);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		titlePane.setDisabledTextColor(Color.BLACK);
		titlePane.setText("Configurador do Programa");
		titlePane.setEnabled(false);
		
		
		// middlePanel
		JPanel timePanel = new JPanel();
		JLabel timeLabel = new JLabel("Intervalo de procura: ");
		JComboBox timeBox = new JComboBox(new Integer[] {1, 5, 15, 30, 60});

		timePanel.add(timeLabel);
		timePanel.add(timeBox);
		
		DirectoryConfigPanel panelI = new DirectoryConfigPanel("Entrada");
		DirectoryConfigPanel panelO = new DirectoryConfigPanel("Saída");
		DirectoryConfigPanel panelP = new DirectoryConfigPanel("Processados");
		DirectoryConfigPanel panelR = new DirectoryConfigPanel("Rejeitados");
		DirectoryConfigPanel[] dirConfigPanels = new DirectoryConfigPanel[] {panelI, panelO, panelP, panelR};
		
		innerMiddlePanel.add(panelI);
		innerMiddlePanel.add(panelO);
		innerMiddlePanel.add(panelP);
		innerMiddlePanel.add(panelR);
		innerMiddlePanel.add(timePanel);
		
		middlePanel.add(innerMiddlePanel);
//		middlePanel.add(timePanel);
		
		SpringUtilities.makeCompactGrid(innerMiddlePanel, 5, 1, 0, 0, 0, 20);
		
		// bottomPanel
		FlowLayout fl = new FlowLayout();
		fl.setHgap(25);
		bottomPanel.setLayout(fl);
		bottomPanel.setBackground(Color.WHITE);
		
		JButton save = new JButton("Salvar");
		save.addActionListener(new ButtonActionSaveGeneralConfig(watcherAction, new DirectoryConfigPanel[] { panelI, panelO, panelP, panelR }, timeBox));
		save.setPreferredSize(new Dimension(100, 30));
		bottomPanel.add(save);
		
		JButton back = new JButton("Voltar");
		back.addActionListener(new ButtonActionSwitchFrame(frame4, frame));
		back.setPreferredSize(new Dimension(100, 30));
		bottomPanel.add(back);
		
		
		// ADD
		frame4.add(titlePane, BorderLayout.NORTH);
		frame4.add(middlePanel, BorderLayout.CENTER);
		frame4.add(bottomPanel, BorderLayout.SOUTH);
	
		
		// EVENTS
		frame4.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
				
				Arrays.asList(dirConfigPanels).forEach(p -> {
					p.setInitialPath();
				});
				
				timeBox.setSelectedItem( WatcherService.getInterval() / 1000);
			}
		});
		
		frame.setVisible(true);
		new IRDocumentManager(irScreen.table);
	}
}