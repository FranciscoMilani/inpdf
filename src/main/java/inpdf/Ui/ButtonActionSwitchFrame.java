package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ButtonActionSwitchFrame implements ActionListener{
	private JFrame from;
	private JFrame to;
	
	public ButtonActionSwitchFrame(JFrame from, JFrame to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		from.dispose();
		to.setVisible(true);
	}
}