package inpdf.Ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;

public class ButtonActionSelectDir implements ActionListener {
	private DirectoryConfigPanel dcp;

	public ButtonActionSelectDir(DirectoryConfigPanel dcp) {
		this.dcp = dcp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(Path.of(new File(".").getAbsolutePath()).getRoot().toFile());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		int res = fileChooser.showSaveDialog(null);
		if(res == JFileChooser.APPROVE_OPTION) {
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			dcp.setPath(path);
		}
	}
}