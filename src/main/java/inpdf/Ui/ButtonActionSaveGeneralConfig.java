package inpdf.Ui;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import inpdf.DirectoryManager;
import inpdf.watcher.WatcherService;

public class ButtonActionSaveGeneralConfig implements ActionListener {

	private DirectoryConfigPanel[] panels;
	private ButtonActionWatcher watcherAction;
	private JComboBox interval;
	
	public ButtonActionSaveGeneralConfig(ButtonActionWatcher watcherAction, DirectoryConfigPanel[] panels, JComboBox intervalBox) {
		this.watcherAction = watcherAction;
		this.panels = panels;
		this.interval = intervalBox;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int blanks = 0;
		
		for (DirectoryConfigPanel p : panels) {
			if (p.getPathString().isBlank()) {
				blanks++;				
			}
		}

		
		if (blanks > 0) {
			int ans = JOptionPane.showOptionDialog(null, 
					"Os campos vazios utilizarão diretórios padrão do InPDF", 
					"Encontrado " + blanks + " campos vazios", 
					JOptionPane.OK_CANCEL_OPTION, 
					MessageType.WARNING.ordinal(), 
					null, 
					null, 
					null);
			
			switch(ans) {
				case (JOptionPane.OK_OPTION):
					break;
				case (JOptionPane.CANCEL_OPTION):
					return;
			}
			
		}
		
		int val = (Integer) interval.getSelectedItem();
		DirectoryManager.saveDirectories(panels, val);
		watcherAction.watcher.change(DirectoryManager.getInputDirectoryPath());
	}
}