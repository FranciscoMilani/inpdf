package inpdf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import inpdf.irpf.IAddable;
import inpdf.irpf.IRDocument;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRField;
import inpdf.irpf.IRItem;
import inpdf.irpf.IRSection;
import inpdf.irpf.IRSectionsEnum;

public class IRPFReader extends Reader {

	public final List<String> sectionNames;
	
	public IRPFReader() throws IOException {
		sectionNames = Arrays.asList(IRSectionsEnum.values()).stream().map(value -> value.str).collect(Collectors.toList());
	}

	public void readPDF(Path readPath) {
		PDDocument doc = null;
		String txt;
		String jsonTxt;
		
		try {
		
			doc = loadDocument(readPath);
			txt = extractAllText(doc);	
			jsonTxt = extractFieldsToJson(txt, readPath);
			
			String fileName = FilenameUtils.removeExtension(readPath.getFileName().toString());
			DirectoryManager.saveJsonToPath(jsonTxt, fileName, DirectoryManager.getOutputDirectoryPath());
			DirectoryManager.moveToProcessedFolder(readPath);
			
			doc.close();
		} 
		catch (InvalidPasswordException e) {	
			System.out.println("Não foi possível extrair. O arquivo \"" + readPath + "\" é criptografado com senha.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			DirectoryManager.moveToRejectedFolder(readPath);
		}
		finally {
			try {
				if (doc != null) {
					doc.close();	
					System.out.println("Limpou os recursos");
				}	
			} catch (IOException e){
				System.out.println("Não conseguiu/foi necessário limpar os recursos");
			}
		}
	}
	
	private String extractFieldsToJson(String strippedText, Path readPath) {
		IRDocument doc = IRDocumentManager.irDocument;
		String[] outputLines = strippedText.split(System.lineSeparator());
		
		// [Seção / (Nome / Categoria)]
		LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<>();
		// [Seção / {Item / (Nome / Categoria)}]
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> itemMap = new LinkedHashMap<>();

		for (Entry<IRSectionsEnum, IRSection> entry : doc.sections.entrySet()) {		
			IRSection section = entry.getValue();
			LinkedHashMap<String, LinkedHashMap<String, String>> innerMap = new LinkedHashMap<>();
			LinkedHashMap<String, String> innermostMap = new LinkedHashMap<>();
			
			if (section instanceof IAddable) {
				IAddable addable = (IAddable) section;
				
				int size = addable.getItems().size();
				for (int i = 0; i < size; i++) {
					IRItem item = addable.getItemByIndex(i);
					
					for (IRField field : item.getFields()) {
						if (field.getRead()) {	
							if (field.getLine() > outputLines.length || field.getLine() <= 0) {
								continue;
							}
							
							innermostMap.put(field.getName(), outputLines[field.getLine() - 1]);
						}
						
						innerMap.put(Integer.toString(item.getId()), innermostMap);
					}
					
					itemMap.put(section.getType().toString(), innerMap);
					innermostMap = new LinkedHashMap<>();
				}
			} else {
				for (IRField field : section.getFields()) {
					if (field.getRead() != null && field.getRead()) {	
						if (field.getLine() > outputLines.length || field.getLine() <= 0) {
							continue;
						}
						
						innermostMap.put(field.getName(), outputLines[field.getLine() - 1]);
					}
				}
			}
				
			map.put(section.getType().toString(), innermostMap);
		}
		
		return generateJson(map, itemMap, readPath);
	}
	

	private String generateJson(LinkedHashMap<String, LinkedHashMap<String, String>> fields,
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> itemsFields,
			Path readPath) {
		
//		if (fields.isEmpty()) {
//			return null;
//		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		JsonObject obj = gson.toJsonTree(fields).getAsJsonObject();
		JsonObject obj2 = gson.toJsonTree(itemsFields).getAsJsonObject();
		JsonObject base = new JsonObject();
		JsonObject section = new JsonObject();
	
		base.add("metadados", getMetadata(readPath));
		base.add("campos", section);
			
		for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
			section.add(entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<String, JsonElement> entry : obj2.entrySet()) {
			section.add(entry.getKey(), entry.getValue());
		}
		
		String outText = gson.toJson(base);	
//		System.out.println(outText);

		return outText;
	}
}