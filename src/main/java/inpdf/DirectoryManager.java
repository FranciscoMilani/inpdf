package inpdf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonStreamParser;
import com.google.gson.internal.GsonBuildConfig;
import com.google.gson.stream.JsonReader;

import inpdf.Ui.DirectoryConfigPanel;
import inpdf.irpf.IRDocument;
import inpdf.irpf.IRSectionsEnum;
import inpdf.watcher.WatcherService;

public class DirectoryManager {
	private static final Path userDirPath = Paths.get(System.getProperty("user.dir"));
	private static final Path generalConfigPath = Paths.get(userDirPath + "/config_geral.json");
	private static final Path boletoConfigPath = Paths.get(userDirPath + "/boleto_config.json");
	private static final Path irpfConfigPath = Paths.get(userDirPath + "/irpf_config.json");
	
	private static final Path defaultInputDirectoryPath = userDirPath.resolve("entrada");
	private static final Path defaultOutputDirectoryPath = userDirPath.resolve("saida");
	private static final Path defaultProcessedDirectoryPath = userDirPath.resolve("processados");
	private static final Path defaultRejectedDirectoryPath = userDirPath.resolve("rejeitados");
	
	private static Path inputDirectoryPath = defaultInputDirectoryPath;
	private static Path outputDirectoryPath = defaultOutputDirectoryPath;
	private static Path processedDirectoryPath = defaultProcessedDirectoryPath;
	private static Path rejectedDirectoryPath = defaultRejectedDirectoryPath;
	
	static {
		loadDirectories();
	}
	
	public static void saveDirectories(DirectoryConfigPanel[] panels, Integer configInterval) {
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		JsonObject obj = new JsonObject();
		
		obj.addProperty("intervalo", configInterval);
		
		for (DirectoryConfigPanel panel : panels) {
			obj.addProperty(panel.id.toLowerCase(), panel.getPathString());
		}
		
		String jText = gson.toJson(obj);
		
		try {
			Files.write(generalConfigPath, jText.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadDirectories();
	}
	
	public static void loadDirectories() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		if (!Files.exists(generalConfigPath)) {
			try {
				JsonObject object = new JsonObject();
				object.add("intervalo", new JsonPrimitive(5));
				object.add("entrada", new JsonPrimitive(getInputDirectoryPath().toString()));
				object.add("saída", new JsonPrimitive(getOutputDirectoryPath().toString()));
				object.add("processados", new JsonPrimitive(getProcessedDirectoryPath().toString()));
				object.add("rejeitados", new JsonPrimitive(getRejectedDirectoryPath().toString()));
				String out = gson.toJson(object);
				
				Files.write(generalConfigPath, out.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				Main.interruptWatcher();
				throw new RuntimeException(e);
			}
		}
		
		try (BufferedReader br = Files.newBufferedReader(generalConfigPath)) {
			JsonReader reader = gson.newJsonReader(br);
			JsonElement tree = JsonParser.parseReader(reader);
			JsonObject obj = tree.getAsJsonObject();
			
			WatcherService.setInterval(obj.get("intervalo").getAsInt() * 1000);
			
			System.out.println(obj.toString());
			assignDir(obj, "entrada");
			assignDir(obj, "saída");
			assignDir(obj, "processados");
			assignDir(obj, "rejeitados");

		} catch (IOException e) {
			e.printStackTrace();
			Main.interruptWatcher();
		}	
	}
	
	private static void assignDir(JsonObject obj, String primitiveKey) {
		JsonPrimitive primitive = obj.getAsJsonPrimitive(primitiveKey);
		String value = primitive.getAsString();
		
		if (value.isBlank()) {
			if (primitiveKey.equals("entrada")) {
				setInputDirectoryPath(defaultInputDirectoryPath);
			} else if (primitiveKey.equals("saída")) {
				setOutputDirectoryPath(defaultOutputDirectoryPath);
			} else if (primitiveKey.equals("processados")) {
				setProcessedDirectoryPath(defaultProcessedDirectoryPath);
			} else if (primitiveKey.equals("rejeitados")) {
				setRejectedDirectoryPath(defaultRejectedDirectoryPath);
			}
			return;
		} else {
			Path path = Paths.get(value);
			
			if (primitiveKey.equals("entrada")) {
				setInputDirectoryPath(path);
			} else if (primitiveKey.equals("saída")) {
				setOutputDirectoryPath(path);
			} else if (primitiveKey.equals("processados")) {
				setProcessedDirectoryPath(path);
			} else if (primitiveKey.equals("rejeitados")) {
				setRejectedDirectoryPath(path);
			}
		}
	}
	
	public static void moveToProcessedFolder(Path filePath) {
		moveToFolder(filePath, getProcessedDirectoryPath());
	}
	
	public static void moveToRejectedFolder(Path filePath) {
		moveToFolder(filePath, getRejectedDirectoryPath());
	}
	
	private static void moveToFolder(Path filePath, Path destination) {
		Path nPath = destination.resolve(filePath.getFileName());
		
		try {
			if (Files.exists(nPath, LinkOption.NOFOLLOW_LINKS)) {
				String fileExt = FilenameUtils.getExtension(filePath.getFileName().toString());
				String fileNameWithOutExt = FilenameUtils.removeExtension(filePath.getFileName().toString());
				nPath = destination.resolve(fileNameWithOutExt + " (" + String.valueOf(System.currentTimeMillis() + ")." + fileExt));
			}
			
			Files.move(filePath, nPath, StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveJsonToPath(String jsonText, String fileName, Path path) {	
		fileName = getNewFileName(fileName);
		Path newPath = path.resolve(fileName + ".json");
		
		try {
			Files.write(newPath, jsonText.getBytes());	
		} catch (NoSuchFileException e) {
			path.toFile().mkdir();
			
			try {
				Files.write(newPath, jsonText.getBytes());
			} catch (Exception er) {
				er.printStackTrace();
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static IRDocument getIRConfigFromJson() {
		if (!getIRConfigPath().toFile().exists()) {
			System.out.println("Configuração de IRPF não existe, retornando nulo");
			return null;
		}
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		try {
			BufferedReader br = new BufferedReader(new FileReader(getIRConfigPath().toString()));
			JsonElement jElement = JsonParser.parseReader(gson.newJsonReader(br));
			System.out.println(jElement);
			IRDocument newDoc = new IRDocument();
			newDoc = gson.fromJson(jElement, IRDocument.class);
			System.out.println(newDoc.cid.getFieldAtIndex(1).getRead());
			
			return newDoc;	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static DocumentConfiguration getConfigFromJson(DocumentType type) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (BufferedReader br = Files.newBufferedReader(DirectoryManager.getBoletoConfigPath(), StandardCharsets.ISO_8859_1)) {
			JsonElement jElement = JsonParser.parseReader(gson.newJsonReader(br));
			JsonArray arr = jElement.getAsJsonArray();
			DocumentConfiguration dc = gson.fromJson( arr.get(0)
					  								.getAsJsonObject()
					  								.get(type.toString()),
					  								DocumentConfiguration.class );
			
			return dc;
			
		} catch (Exception e) {
			e.printStackTrace();
			Main.interruptWatcher();
		}
		
		return null;
	}
	
	public static void saveConfigJson(String jsonText, Path path) {	
		try {
			Files.write(path, jsonText.getBytes());		
		} 
		catch (NoSuchFileException e) {
			path.toFile().mkdir();
			
			try {
				Files.write(path, jsonText.getBytes());
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
		File file = getOutputDirectoryPath().resolve(fileName + ".json").toFile();
		int number = 0;
		String fName = "";
		
		if (file.exists() && file.isFile()) {
			while(file.exists()) {
				number++;
				fName = fileName + "("+ number +")";
				file = getOutputDirectoryPath().resolve(fName + ".json").toFile();
			}
		} else if(!file.exists()) {
			fName = fileName;
		}

		return fName;
	}
	
	private static void createIfDoesntExist(Path path) {
		if (path != null && !Files.exists(path)){
			File directory = new File(path.toString());
			directory.mkdir();
		}
	}
	
	public static Path getInputDirectoryPath() {
		createIfDoesntExist(inputDirectoryPath);
		return inputDirectoryPath;
	}
	
	public static void setInputDirectoryPath(Path newInputDirectoryPath) {
		inputDirectoryPath = newInputDirectoryPath;
	}
	
	public static Path getOutputDirectoryPath() {
		createIfDoesntExist(outputDirectoryPath);
		return outputDirectoryPath;
	}
	
	public static void setOutputDirectoryPath(Path newOutputDirectoryPath) {
		outputDirectoryPath = newOutputDirectoryPath;
	}
	
	public static Path getProcessedDirectoryPath() {
		createIfDoesntExist(processedDirectoryPath);
		return processedDirectoryPath;
	}
	
	public static void setProcessedDirectoryPath(Path newProcessedDirectoryPath) {
		processedDirectoryPath = newProcessedDirectoryPath;
	}
	
	public static Path getRejectedDirectoryPath() {
		createIfDoesntExist(rejectedDirectoryPath);
		return rejectedDirectoryPath;
	}
	
	public static void setRejectedDirectoryPath(Path newRejectedDirectoryPath) {
		rejectedDirectoryPath = newRejectedDirectoryPath;
	}

	public static Path getBoletoConfigPath() {
		return boletoConfigPath;
	}
	
	public static Path getIRConfigPath() {
		return irpfConfigPath;
	}
	
	public static Path getGeneralConfigPath() {
		return generalConfigPath;
	}
	
}