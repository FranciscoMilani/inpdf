package inpdf;

public class DirectoryManager {
	private String inputDirectoryPath;
	private String outputDirectoryPath;
	private String rejectedDirectoryPath;
	
	public void SaveDirectories() {
		// TO-DO: Armazenar diretórios quando o usuário salvar
		
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

}
