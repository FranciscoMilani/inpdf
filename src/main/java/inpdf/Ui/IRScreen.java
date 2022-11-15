package inpdf.Ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;

import inpdf.ExtractedTextArea;
import inpdf.irpf.ConfigTable;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRSectionsEnum;

public class IRScreen extends JPanel implements ActionListener {
	private JFrame baseFrame;
	private JFrame thisFrame;
	
	private JSplitPane splitPane = new JSplitPane();
	
	private ExtractedTextArea extractedTextArea = new ExtractedTextArea();
	private JPanel textAreaPanel = new JPanel(new BorderLayout());
	private JPanel bottomAreaPanel = new JPanel();
	private JScrollPane scrollArea = new JScrollPane(extractedTextArea);
	
	private JButton prevBtn = new JButton();
	private JButton nextBtn = new JButton();
	private JLabel pageLabel = new JLabel();
	private JButton findBtn = new JButton();
	private JComboBox<Object> comboBox = new JComboBox(IRSectionsEnum.values());
	
	private JPanel mainTablePanel = new JPanel(new BorderLayout());
	private JPanel tablePanel = new JPanel(new BorderLayout());
	private JPanel bottomTablePanel = new JPanel();
	private JScrollPane scrollTable = new JScrollPane(tablePanel);
	private JButton backBtn = new JButton("Voltar");
	private JButton saveBtn = new JButton("Salvar");
	
	private JPanel buttonsBottomPanel = new JPanel();
	private JButton addBtn = new JButton("+");
	private JButton rmvBtn = new JButton("-");
	private JButton lftBtn = new JButton("<");
	private JButton rightBtn = new JButton(">");
	
	public ConfigTable table = new ConfigTable(buttonsBottomPanel);
	
	
	private ButtonActionReadToDisplay readAction = new ButtonActionReadToDisplay(comboBox, extractedTextArea);
	
	public IRScreen(JFrame baseFrame, JFrame thisFrame) {
		super();

		this.baseFrame = baseFrame;
		this.thisFrame = thisFrame;
		
		init();
		events();
		add();
	}
	
	
	private void init() {			
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1280, 960));
		
		prevBtn.setText("<");
		nextBtn.setText(">");
		findBtn.setText("Procurar");	
		
		pageLabel.setPreferredSize(new Dimension(30, 20));
		pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		extractedTextArea.setLabel(pageLabel);
		
		buttonsBottomPanel.setEnabled(false);
		buttonsBottomPanel.setVisible(false);
	}
	
	private void events() {
		findBtn.addActionListener(readAction);
		
		prevBtn.setActionCommand("prev");
		nextBtn.setActionCommand("next");
		prevBtn.addActionListener(extractedTextArea);
		nextBtn.addActionListener(extractedTextArea);
		
		comboBox.addActionListener(table);
		
		saveBtn.addActionListener(new ButtonActionSaveIR(comboBox, table));
		backBtn.addActionListener(new ButtonActionSwitchFrame(thisFrame, baseFrame));
		
		addBtn.addActionListener(new ButtonActionAddItem(table));
	}
	
	private void add() {
		textAreaPanel.add(scrollArea, BorderLayout.CENTER);
		textAreaPanel.add(bottomAreaPanel, BorderLayout.SOUTH);
		
		buttonsBottomPanel.add(addBtn);
		buttonsBottomPanel.add(rmvBtn);
		buttonsBottomPanel.add(lftBtn);
		buttonsBottomPanel.add(rightBtn);
		
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		tablePanel.add(table, BorderLayout.CENTER);
		tablePanel.add(buttonsBottomPanel, BorderLayout.SOUTH);
		
		mainTablePanel.add(scrollTable, BorderLayout.CENTER);
		mainTablePanel.add(bottomTablePanel, BorderLayout.SOUTH);
		
		splitPane.setLeftComponent(textAreaPanel);
		splitPane.setRightComponent(mainTablePanel);
		
		bottomAreaPanel.add(prevBtn);
		bottomAreaPanel.add(pageLabel);
		bottomAreaPanel.add(nextBtn);	
		bottomAreaPanel.add(findBtn);
		
		bottomTablePanel.add(saveBtn);
		bottomTablePanel.add(backBtn);
		bottomTablePanel.add(comboBox);
		
		add(splitPane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
