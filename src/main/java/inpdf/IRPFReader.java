package inpdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.text.Normalizer;
import java.text.DateFormat.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.AppendableWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import inpdf.irpf.IRDocument;
import inpdf.irpf.IRDocumentManager;
import inpdf.irpf.IRField;
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
			//jsonTxt = generateJsonString(IRDocumentManager.irDocument);
			dataMap = extractFieldsFromText(txt);
			jsonTxt = generateJson(dataMap);
//			
//			if (jsonTxt == null)
//				throw new Exception("JSON de saída ignorado. Não há campos marcados para esse tipo de documento");
//			
//			String fileName = FilenameUtils.removeExtension(readPath.toFile().getName());
			DirectoryManager.saveJsonToPath(jsonTxt, readPath.getFileName().toString(), DirectoryManager.getOutputDirectoryPath());
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
	
	private String generateJsonConfig(IRDocument document) {
		Gson gson = new GsonBuilder().
				setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).
				setPrettyPrinting().
				serializeNulls().
				create();
		
		return gson.toJson(document);
	}
	
	LinkedHashMap<String, LinkedHashMap<String, String>> extractFieldsFromText(String strippedText) {
		IRDocument doc = IRDocumentManager.irDocument;
		String[] outputLines = strippedText.split(System.lineSeparator());
		LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<>();
		
		// debuga as linhas
//		for (int l = 1; l <= outputLines.length; l++) {
//			System.out.println(l + " " + outputLines[l-1]);
//		}
		
		for (Entry<IRSectionsEnum, IRSection> entry : doc.sections.entrySet()) {
			IRSection section = entry.getValue();
			LinkedHashMap<String, String> innerMap = new LinkedHashMap<>();
			
			if (section.getFields() != null) {
				for (IRField field : section.getFields()) {
					if (field.getRead()) {	
						if (field.getLine() > outputLines.length || field.getLine() <= 0) {
							continue;
						}
						
						innerMap.put(field.getName(), outputLines[field.getLine() - 1]);
						field.setValue(outputLines[field.getLine() - 1]); // REMOVER ISSO?
					}
				}
			}
			
			map.put(section.getType().toString(), innerMap);
		}
//
//		List<IRField> fields = ;	
//		for (IRField f : fields) {
//			if (f.getShouldRead()) {
//				if (f.getLineLocated() > outputLines.length) {
//					continue;
//				}
//				
//				dataMap.put(f, outputLines[f.getLineLocated() - 1]);	
//			}
//		}
		
		return map;
	}
	
	private String generateJson(LinkedHashMap<String, LinkedHashMap<String, String>> fields) {
		if (fields.isEmpty()) {
			return null;
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		JsonObject base = new JsonObject();
		for (Entry<String, LinkedHashMap<String, String>> entry : fields.entrySet()) {
			JsonObject inner = new JsonObject();
			
			entry.getValue().forEach((name, value) -> {
				if (value != null) {
					inner.add(name, new JsonPrimitive(value));
				} else {
					inner.add(name, new JsonPrimitive(""));
				}
			});
			
			base.add(entry.getKey(), inner);
		}
		
		String txt = gson.toJson(base);
		
		
		System.err.println(txt);
		
		Type typeOfHashMap = new TypeToken<LinkedHashMap<String, LinkedHashMap<String, String>>>() { }.getType();
		LinkedHashMap<IRSection, LinkedHashMap<String, String>> newMap = gson.fromJson(txt, typeOfHashMap);
		//JsonObject obj = new JsonObject();
		fields.forEach((section, data) -> {
			data.forEach((field, value) -> {
				
			});
//			List<IRField> fList = field.getFields();
//			if (fList != null) {
//				
//			}
//			String str = field.toLowerCase().replace(" ", "_");
//			str = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
//			obj.addProperty(str, data);
		});
		
		//String txt = gson.toJson(obj);
		return txt;
	}
}