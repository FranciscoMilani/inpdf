package inpdf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DirectoryManager{
	private String inputDirectoryPath;
	private String outputDirectoryPath;
	private String rejectedDirectoryPath;
	
	public void saveDirectories() {
		if(this.getInputDirectoryPath() != null) {
			try {
				String projectDir = System.getProperty("user.dir");
				String documentDir = this.getInputDirectoryPath();
				
				if (Reader.checkFileConformity(documentDir)) {
					// temporário de teste
					Reader reader = new Reader();
					reader.ReadPDF(documentDir);
					
					DocumentType type = Reader.determineDocumentType(documentDir);
					
					if (type == DocumentType.UNKNOWN) {
						// Indeterminado, informar no log que não foi reconhecido
						
					}
					else if (type == DocumentType.DECLARACAO_IMPOSTO_DE_RENDA) {
						// IRPF
						IRPFReader IRPFReader = new IRPFReader();
					} 
					else {
						// Boletos
						BoletoReader BoletoReader = new BoletoReader();
					}		
				} 
				else {
					System.out.println("Arquivo não é formato PDF");
				}			
			} 
			catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}	
	}
	
	private String[] loadDirectories() {
		// TODO: Carregar diretórios necessários para as outra classes
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
