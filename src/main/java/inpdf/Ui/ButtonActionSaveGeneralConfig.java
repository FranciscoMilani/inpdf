package inpdf.Ui;

import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import inpdf.DirectoryManager;
import inpdf.watcher.WatcherService;

public class ButtonActionSaveGeneralConfig implements ActionListener {

	private DirectoryConfigPanel[] panels;
	private ButtonActionWatcher watcherAction;
	
	public ButtonActionSaveGeneralConfig(ButtonActionWatcher watcherAction, DirectoryConfigPanel[] panels) {
		this.watcherAction = watcherAction;
		this.panels = panels;
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
		
		DirectoryManager.saveDirectories(panels);	
		watcherAction.watcher.change(DirectoryManager.getInputDirectoryPath());
	}
}