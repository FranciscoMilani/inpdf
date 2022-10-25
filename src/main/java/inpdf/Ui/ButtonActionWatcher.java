package inpdf.Ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Queue;

import javax.swing.JButton;

import inpdf.watcher.WatcherService;

public class ButtonActionWatcher implements ActionListener {

	WatcherService watcher;
	Thread t;
	
	public ButtonActionWatcher(WatcherService watcher, Thread t){
		this.watcher = watcher;
		this.t = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean actionState = switchAction();
		JButton btn = (JButton) e.getSource();
		String btnName = actionState ? "DESLIGAR" : "LIGAR";
		btn.setText(btnName);
		
//		Queue<Path> queue = watcher.getFilePathsQueue();
//		
//		if (!queue.isEmpty()) {
//			Path polledPath = queue.poll();
//			System.out.println(polledPath);
//		}
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