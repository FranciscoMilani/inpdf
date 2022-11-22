package inpdf.Ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Queue;

import javax.swing.JButton;

import inpdf.watcher.WatcherService;

public class ButtonActionWatcher implements ActionListener {

	public WatcherService watcher;
	public Thread t;
	
	public ButtonActionWatcher(WatcherService watcher, Thread t){
		this.watcher = watcher;
		this.t = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean actionState = switchAction();
		JButton btn = (JButton) e.getSource();
		String btnName = actionState ? "Interromper" : "Escanear";
		btn.setText(btnName);	
	}
	
	public synchronized boolean switchAction() {
		if (watcher.getState()) {
			watcher.resume();
			return true;
		}
		else {
			watcher.suspend();
			return false;
		}
	}
}