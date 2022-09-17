package inpdf.Ui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class LabelManager{
	private JFrame frame;
	private JLabel label;
	
	public LabelManager(JFrame frame) {
		this.frame = frame;
		this.label = new JLabel();
	}
	
	public void addText(String str) {
		this.label.setText(str);
		this.label.setBounds(400 ,350, 200, 100);
		this.frame.add(label);
		this.frame.repaint();
		System.out.println(this.label.bounds());
	}
	
	public void clearText() {
		this.label.setText("");
		this.frame.add(label);
		this.frame.repaint();
	}
	
}