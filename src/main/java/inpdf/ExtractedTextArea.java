package inpdf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
 
public class ExtractedTextArea extends JPanel implements ActionListener {
    private static JTextArea textArea = new JTextArea(55, 65);
 
    public ExtractedTextArea(String text) {
        super();
        
        textArea.setEditable(false);
        add(textArea);
        //JScrollPane scrollPane = new JScrollPane(textArea);
        //add(scrollPane);
    }
 
    public void actionPerformed(ActionEvent evt) {
    	
    }

    public static void displayText(String text) {	
		String[] outputLines = text.split(System.lineSeparator());
		StringBuilder newStr = new StringBuilder();
		int digits = (int) Math.log10(outputLines.length) + 1;
		
		for (int l = 1; l <= outputLines.length; l++) {
			String formatted = String.format("%0" + digits + "d", l);
			StringBuilder sb = new StringBuilder(outputLines[l-1]);
			sb.insert(0, formatted + " : ");
			sb.append(System.lineSeparator());
			newStr.append(sb);
		}

        textArea.setText(newStr.toString());
    	textArea.setCaretPosition(0);
    }
    
    public static void clear() {
    	textArea.setText("");
    }
}
