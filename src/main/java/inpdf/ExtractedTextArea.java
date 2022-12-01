package inpdf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ExtractedTextArea extends JPanel implements ActionListener {
	private JTextArea textArea = new JTextArea(55, 65);
    private List<String> pages = new ArrayList<String>();
    private int currentPage = 1;
    private JLabel pageLabel;
    private String fullText;
	List<String> formattedStrings = new ArrayList<String>();

    public ExtractedTextArea() {
        super();
        
        textArea.setEditable(false);
        add(textArea);
    }
    
    private void displayText(int i) {
		textArea.setText(formattedStrings.get(i - 1));
    	textArea.setCaretPosition(0);
    }
    
    private void displayTextBefore(int i) {
    	int start = 0;
    	
		for (String page : pages) {
			String[] outputLines = page.split(System.lineSeparator());
			StringBuilder newStr = new StringBuilder();
			int digits = (int) Math.log10(outputLines.length) + 1;
			for (int l = 1; l <= outputLines.length; l++) {
				String formatted = String.format("%0" + digits + "d", l + start);
				StringBuilder sb = new StringBuilder(outputLines[l-1]);
				sb.insert(0, formatted + " : ");
				sb.append(System.lineSeparator());
				newStr.append(sb);
			}
			
			formattedStrings.add(newStr.toString());		
			start += outputLines.length;
		}
    }
    
    private void displayAllText() {	
		String[] outputFullLines = fullText.split(System.lineSeparator());
		StringBuilder newStr = new StringBuilder();
		int digits = (int) Math.log10(outputFullLines.length) + 1;

		for (int l = 1; l <= outputFullLines.length; l++) {
			String formatted = String.format("%0" + digits + "d", l);
			StringBuilder sb = new StringBuilder(outputFullLines[l-1]);
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
    
    public int getCurrentPage() {
    	return currentPage;
    }
    
    public void clear() {
    	textArea.setText("");
    	fullText = "";
    	currentPage = 1;
    	pages = new ArrayList<String>();
    	formattedStrings = new ArrayList<String>();
    }
    
    public void setLabel(JLabel label) {
    	this.pageLabel = label;
    }
    
    public void setPage(String page) {
    	clear();
        this.pages.add(page);
        fullText = page;
    }
    
    public void setPages(String[] pages, int firstPage) {
    	clear();

    	StringBuilder sb = new StringBuilder();
    	for (String p : pages) {
    		sb.append(p);
    	}
    	
    	fullText = sb.toString(); 	
        this.pages = Arrays.asList(pages);
        currentPage = firstPage;
    }
    
    public void displayFirst(int firstPage) {
    	currentPage = firstPage;
    	displayTextBefore(currentPage);
    	displayText(currentPage);
    	//displayAllText();
    	
		if (pageLabel != null) {
			pageLabel.setText(Integer.toString(currentPage));
		}
    }
    
    public void displayNext() {
    	currentPage = currentPage % pages.size() + 1;
    	displayText(currentPage);
    }
    
    public void displayPrev() {
    	currentPage = currentPage == 1 ? currentPage = getPageAmount() : currentPage - 1;
    	displayText(currentPage);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (pages.isEmpty() || pages.size() == 1) {
			return;
		}
		
		if (e.getActionCommand() == "next") {
			displayNext();
		} else if (e.getActionCommand() == "prev") {
			displayPrev();
		}
		
		if (pageLabel != null) {
			pageLabel.setText(Integer.toString(currentPage));
		}
	}
}