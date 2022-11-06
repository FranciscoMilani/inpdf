package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
		DirectoryManager.saveDirectories(panels);
		
		for (DirectoryConfigPanel p : panels) {
			if (p.getPathString().isBlank()) {
				// TODO: PERGUNTAR SE DESEJA SALVAR CAMPOS VAZIOS, (USA CAMINHOS DEFAULT)
			}
			
			if (p.id == "Entrada") {
				watcherAction.watcher.change(DirectoryManager.getInputDirectoryPath());			
			}		
		}
	}
}