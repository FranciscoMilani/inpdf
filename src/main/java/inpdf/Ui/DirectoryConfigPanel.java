package inpdf.Ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DirectoryConfigPanel extends JPanel implements ActionListener {
	public final String id;
	private JTextField pathField = new JTextField("", 25);
	private JButton button = new JButton("Procurar ");
	private JButton clearButton = new JButton("X");
	private Path path;
	
	public DirectoryConfigPanel(String id) {
		this.id = id;
		this.setLayout(new BorderLayout(5, 0));
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(this);

		pathField.setEditable(false);
		button.setText(button.getText() + id);
		button.setActionCommand(id);

		this.add(pathField, BorderLayout.WEST);
		this.add(button, BorderLayout.CENTER);
		this.add(clearButton, BorderLayout.EAST);
		
		ButtonActionSelectDir btnAction = new ButtonActionSelectDir(this);
		button.addActionListener(btnAction);
	}
	
	public void setPath(String newPath) {
		path = Paths.get(newPath);
		pathField.setText(path.toString());
		pathField.setCaretPosition(0);
	}
	
	public Path getPath() {
		return path;
	}
	
	public String getPathString() {	
		return (path == null) ? "" : path.normalize().toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "clear") {
			this.setPath("");
		}
	}
}