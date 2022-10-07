package inpdf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EventListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DirectoryManager{
	private static String inputDirectoryPath;
	private static String outputDirectoryPath;
	private static String rejectedDirectoryPath;
	
	static {
		outputDirectoryPath = System.getProperty("user.dir") + File.separator + "Saida";
	}
	
	public static void saveDirectories() {
		if(getInputDirectoryPath() != null) {
			try {
				String projectDir = System.getProperty("user.dir");
				String documentDir = getInputDirectoryPath();
				
				if (Reader.checkFileConformity(documentDir)) {
					Reader reader = new Reader();
					reader.ReadPDF(documentDir);
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
	
	public static void saveJsonToOutputPath(String jsonText, String fileName) {	
		String newName = getNewFileName(fileName);
		
		Path filePath = Paths.get(outputDirectoryPath + 
				File.separator + 
				newName +
				".json" );

		try {
			Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);
		} 
		catch (NoSuchFileException e) {
			new File(outputDirectoryPath.toString()).mkdir();
			
			try {
				Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);
			}
			catch (Exception er) {
				er.printStackTrace();
				throw new RuntimeException(e);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private static String getNewFileName(String fileName) {
		File file = new File(outputDirectoryPath + File.separator + fileName + ".json");
		int number = 0;
		String fName = "";
		
		if (file.exists() && file.isFile()) {
			while(file.exists()) {
				number++;
				fName = fileName + "("+ number +")";
				file = new File(outputDirectoryPath + File.separator + fName + ".json");
			}
		} else if(!file.exists()) {
			fName = fileName;
		}

		return fName;
	}
	
	private static String[] loadDirectories() {
		// TODO: Carregar diretórios necessários para as outra classes
		return null;
	}
	
	public static String getInputDirectoryPath() {
		return inputDirectoryPath;
	}
	
	public static void setInputDirectoryPath(String newInputDirectoryPath) {
		inputDirectoryPath = newInputDirectoryPath;
	}
	
	public static String getOutputDirectoryPath() {
		return outputDirectoryPath;
	}
	
	public static void setOutputDirectoryPath(String newOutputDirectoryPath) {
		outputDirectoryPath = newOutputDirectoryPath;
	}
	
	public static String getRejectedDirectoryPath() {
		return rejectedDirectoryPath;
	}
	
	public static void setRejectedDirectoryPath(String newRejectedDirectoryPath) {
		rejectedDirectoryPath = newRejectedDirectoryPath;
	}
	
	public static void clearInputDirectoryPath() {
		setInputDirectoryPath(null);
	}
}