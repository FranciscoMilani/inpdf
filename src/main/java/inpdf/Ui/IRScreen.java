package inpdf.Ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import inpdf.ExtractedTextArea;
import inpdf.irpf.IRSectionsEnum;

public class IRScreen implements ActionListener {
	private JPanel mainPanel;
	
	private ExtractedTextArea extractedTextArea = new ExtractedTextArea();
	private JPanel textAreaPanel = new JPanel(new BorderLayout());
	private JPanel bottomAreaPanel = new JPanel();
	private JScrollPane scrollArea = new JScrollPane(textAreaPanel);
	
	private JButton prevBtn = new JButton();
	private JButton nextBtn = new JButton();
	private JButton findBtn = new JButton();
	private JComboBox<String> comboBox = new JComboBox(IRSectionsEnum.values());
	
	private ButtonActionReadToDisplay readAction = new ButtonActionReadToDisplay(comboBox, extractedTextArea);
	
	public IRScreen(JPanel mainPanel) {
		this.mainPanel = mainPanel;
		
		init();
		events();
		add();
	}
	
	
	private void init() {
		mainPanel.setLayout(new GridLayout(1, 2));
		
		prevBtn.setText("<");
		nextBtn.setText(">");
		findBtn.setText("Procurar");
	}
	
	private void events() {
		findBtn.addActionListener(readAction);
	}
	
	private void add() {
		textAreaPanel.add(extractedTextArea, BorderLayout.CENTER);
		textAreaPanel.add(bottomAreaPanel, BorderLayout.SOUTH);
		
		bottomAreaPanel.add(prevBtn);
		bottomAreaPanel.add(nextBtn);
		bottomAreaPanel.add(findBtn);
		bottomAreaPanel.add(comboBox);
		
		mainPanel.add(textAreaPanel);
		//mainPanel.add(bottomAreaPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
