package inpdf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DirectoryManager{
	private String inputDirectoryPath;
	private String outputDirectoryPath;
	private String rejectedDirectoryPath;
	
	
	public void saveDirectories() throws Exception{
		if(this.getInputDirectoryPath() != null) {
			//Reader reader = new Reader();
			String projectDir = System.getProperty("user.dir");
			String documentDir = this.getInputDirectoryPath();
			Reader.ReadPDF(documentDir);
		}	
	}
	
	private String[] LoadDirectories() {
		// TO-DO: Carregar diretórios necessários para as outra classes
		return null;
	}
	
	public String getInputDirectoryPath() {
		return inputDirectoryPath;
	}
	
	public void setInputDirectoryPath(String inputDirectoryPath) {
		this.inputDirectoryPath = inputDirectoryPath;
	}
	
	public String getOutputDirectoryPath() {
		return outputDirectoryPath;
	}
	
	public void setOutputDirectoryPath(String outputDirectoryPath) {
		this.outputDirectoryPath = outputDirectoryPath;
	}
	
	public String getRejectedDirectoryPath() {
		return rejectedDirectoryPath;
	}
	
	public void setRejectedDirectoryPath(String rejectedDirectoryPath) {
		this.rejectedDirectoryPath = rejectedDirectoryPath;
	}
	
	public void clearInputDirectoryPath() {
		this.setInputDirectoryPath(null);
	}

}
