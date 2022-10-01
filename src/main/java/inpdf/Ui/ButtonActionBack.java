package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ButtonActionBack implements ActionListener{
	private JFrame frame1;
	private JFrame frame2;
	
	public ButtonActionBack(JFrame frame1, JFrame frame2) {
		this.frame1 = frame1;
		this.frame2 = frame2;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame1.setVisible(true);
		frame2.setVisible(false);
	}
}
