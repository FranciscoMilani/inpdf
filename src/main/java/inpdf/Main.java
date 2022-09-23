package inpdf;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import inpdf.Ui.ButtonActionClear;
import inpdf.Ui.ButtonActionConfirm;
import inpdf.Ui.ButtonActionReadFile;
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
		
		frame.add(selectFileButton);
		frame.add(confirmButton);
		frame.add(clearButton);
	}
}
