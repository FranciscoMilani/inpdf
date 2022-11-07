package inpdf;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
 
public class ExtractedTextArea extends JPanel {
    private JTextArea textArea = new JTextArea(55, 65);
    private List<String> pages = new ArrayList<String>();
    
    public ExtractedTextArea() {
        super();

        textArea.setEditable(false);
        add(textArea);
    }

    public void displayText(String text) {	
    	
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
    
    public int getPageAmount() {
    	return pages.size();
    }
    
    public void clear() {
    	textArea.setText("");
    }
    
    public void setPage(String page) {
        this.pages.add(page);
    }
    
    public void setPages(String[] pages) {
        this.pages = Arrays.asList(pages);
    }
}