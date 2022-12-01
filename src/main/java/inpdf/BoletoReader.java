package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class BoletoReader extends Reader {
	
	public static final String[] COMPE_CODES = {"001-9", "041-8", "237-2", "341-7", "033-7", "748-X"};
	public static final Map<DocumentType, List<String>> COMPE_MAP = new HashMap<>(6);
	private StrippedLocation strippedLocation = new StrippedLocation();
	
	static {
		COMPE_MAP.put(DocumentType.BOLETO_BANCARIO_BANCO_DO_BRASIL, Arrays.asList(COMPE_CODES[0], "BANCO DO BRASIL"));
		COMPE_MAP.put(DocumentType.BOLETO_BANCARIO_BANRISUL, Arrays.asList(COMPE_CODES[1], "BANRISUL"));
		COMPE_MAP.put(DocumentType.BOLETO_BANCARIO_BRADESCO, Arrays.asList(COMPE_CODES[2], "BRADESCO"));
		COMPE_MAP.put(DocumentType.BOLETO_BANCARIO_ITAU, Arrays.asList(COMPE_CODES[3], "ITAÚ", "ITAU"));
		COMPE_MAP.put(DocumentType.BOLETO_BANCARIO_SANTANDER, Arrays.asList(COMPE_CODES[4], "SANTANDER"));
		COMPE_MAP.put(DocumentType.BOLETO_BANCARIO_SICREDI, Arrays.asList(COMPE_CODES[5], "SICREDI"));
	}
	
	
	public BoletoReader() throws IOException {
		super();
	}
	
	public void readPDF(Path readPath, DocumentType docType) {
		PDDocument doc = null;
		String jsonTxt;
		DocumentConfiguration docConfig;
		LinkedHashMap<DocumentField, String> dataMap;
		
		try {
			doc = loadDocument(readPath);	
			
			strippedLocation.extractGeneralBoletoPosition(doc);
			
			docConfig = DocumentConfigurationManager.getConfigurationFromType(docType);	
			dataMap = extractFieldsFromText(docConfig, extractBoletoAreaFromLocation(doc, strippedLocation));		
			jsonTxt = generateJson(dataMap, readPath);
			
			if (jsonTxt == null)
				throw new Exception("JSON de saída ignorado. Não há campos marcados para esse tipo de documento");
			
			String fileName = FilenameUtils.removeExtension(readPath.toFile().getName());
			DirectoryManager.saveJsonToPath(jsonTxt, fileName, DirectoryManager.getOutputDirectoryPath());
			DirectoryManager.moveToProcessedFolder(readPath);
			
			doc.close();
		} 
		catch (InvalidPasswordException e) {	
			System.out.println("Não foi possível extrair. O arquivo \"" + readPath + "\" é criptografado com senha.");
		}
		catch (IOException e) {
			System.out.println("ERRO: " + e.getMessage());
		}
		catch (Exception e) {
			DirectoryManager.moveToRejectedFolder(readPath);
			System.out.println("ERRO: " + e.getMessage());
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

	// extrai os campos em formato String do PDF, e mapeia os campos selecionados que estão na config
	private LinkedHashMap<DocumentField, String> extractFieldsFromText(DocumentConfiguration config, String strippedText) {
		String[] outputLines = strippedText.split(System.lineSeparator());
		LinkedHashMap<DocumentField, String> dataMap = new LinkedHashMap<DocumentField, String>();
		
		// debuga as linhas
		for (int l = 1; l <= outputLines.length; l++) {
			System.out.println(l + " " + outputLines[l-1]);
		}

		List<DocumentField> fields = config.fields;	
		for (DocumentField f : fields) {
			if (f.getShouldRead()) {
				if (f.getLineLocated() > outputLines.length) {
					continue;
				}
				
				dataMap.put(f, outputLines[f.getLineLocated() - 1]);	
			}
		}
		
		return dataMap;
	}
	
	private String generateJson(LinkedHashMap<DocumentField, String> fields, Path readPath) {
		if (fields.isEmpty()) {
			return null;
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject obj = new JsonObject();
		JsonObject section = new JsonObject();
		
		obj.add("metadados", getMetadata(readPath));
		obj.add("campos", section);
		
		fields.forEach((field, data) -> {
			String str = field.getFieldNameAtIndexOrFirst(0).toLowerCase().replace(" ", "_");
			str = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			section.addProperty(str, data);
		});
		
		String txt = gson.toJson(obj);
		return txt;
	}
	
	protected String extractBoletoAreaFromLocation(PDDocument doc, StrippedLocation loc) throws Exception {
		String areaName = "boleto";
		PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
		
		Float pageWidth = doc.getPage(0).getMediaBox().getWidth();
		Float pageHeight = doc.getPage(0).getMediaBox().getHeight();
		
		stripperArea.setSortByPosition(true);
		stripperArea.setWordSeparator(System.lineSeparator());
		stripperArea.setStartPage(0);
		stripperArea.setEndPage(0);
		
		System.out.println("LOCATION Y:" + strippedLocation.startY);
		stripperArea.addRegion(areaName, new Rectangle2D.Float(0, loc.startY, pageWidth, pageHeight - loc.startY));	
		stripperArea.extractRegions(doc.getPage(0));	
		String strippedText = stripperArea.getTextForRegion(areaName);
		return strippedText;
	}
}