package inpdf;

import java.io.IOException;
import java.lang.reflect.Type;
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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

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
		LinkedHashMap<String, LinkedHashMap<String, String>> dataMap;
		
		try {
		
			doc = loadDocument(readPath);	
			txt = extractAllText(doc);	
			//dataMap = extractFieldsFromText(txt);
			jsonTxt = extractFieldsToJson(txt);
//			
//			if (jsonTxt == null)
//				throw new Exception("JSON de saída ignorado. Não há campos marcados para esse tipo de documento");
//			
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
	
	private String extractFieldsToJson(String strippedText) {
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
				if (section.getFields() != null) {
					System.out.println(section.getType());
					for (IRField field : section.getFields()) {
						if (field.getRead() != null && field.getRead()) {	
							if (field.getLine() > outputLines.length || field.getLine() <= 0) {
								continue;
							}
							
							innermostMap.put(field.getName(), outputLines[field.getLine() - 1]);
							//field.setValue(outputLines[field.getLine() - 1]); // REMOVER ISSO?
						}
					}
				}
				
				map.put(section.getType().toString(), innermostMap);
			}
			
		}
		
		return generateJson(map, itemMap);
	}
	

	private String generateJson(LinkedHashMap<String, LinkedHashMap<String, String>> fields, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> itemsFields) {
		if (fields.isEmpty()) {
			return null;
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		JsonObject obj = gson.toJsonTree(fields).getAsJsonObject();
		JsonObject obj2 = gson.toJsonTree(itemsFields).getAsJsonObject();
		JsonObject newObj = new JsonObject();
		
		for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
			newObj.add(entry.getKey(), entry.getValue());
		}
		
		for (Map.Entry<String, JsonElement> entry : obj2.entrySet()) {
			newObj.add(entry.getKey(), entry.getValue());
		}
		
		String outText = gson.toJson(newObj);	
		System.err.println(outText);

		
//		JsonObject base = new JsonObject();
//		for (Entry<String, LinkedHashMap<String, String>> entry : fields.entrySet()) {
//			JsonObject inner = new JsonObject();
//			
//			entry.getValue().forEach((name, value) -> {
//				if (value != null) {
//					inner.add(name, new JsonPrimitive(value));
//				} else {
//					inner.add(name, new JsonPrimitive(""));
//				}
//			});
//			
//			base.add(entry.getKey(), inner);
//		}
		
		//String txt = gson.toJson(base);
		
//		Type typeOfHashMap = new TypeToken<LinkedHashMap<String, LinkedHashMap<String, String>>>() { }.getType();
//		LinkedHashMap<IRSection, LinkedHashMap<String, String>> newMap = gson.fromJson(outText, typeOfHashMap);
//		//JsonObject obj = new JsonObject();
//		fields.forEach((section, data) -> {
//			data.forEach((field, value) -> {
//				
//			});
////			List<IRField> fList = field.getFields();
////			if (fList != null) {
////				
////			}
////			String str = field.toLowerCase().replace(" ", "_");
////			str = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
////			obj.addProperty(str, data);
//		});
		
		//String txt = gson.toJson(obj);
		return outText;
	}
}