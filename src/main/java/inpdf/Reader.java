package inpdf;

import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.encryption.PDEncryption;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Hex;
import org.apache.pdfbox.util.filetypedetector.FileTypeDetector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import inpdf.Ui.TableManager;

public class Reader extends PDFTextStripper  {
	
	private static float startX, startY;
	private static final String[] compeCodes = {"001-9", "041-8", "237-2", "341-7", "033-7", "748-X"};
	private static Map<DocumentType, String> compeMap = new HashMap<>(6);
	
	static {
		compeMap.put(DocumentType.BOLETO_BANCARIO_BANCO_DO_BRASIL, compeCodes[0]);
		compeMap.put(DocumentType.BOLETO_BANCARIO_BANRISUL, compeCodes[1]);
		compeMap.put(DocumentType.BOLETO_BANCARIO_BRADESCO, compeCodes[2]);
		compeMap.put(DocumentType.BOLETO_BANCARIO_ITAU, compeCodes[3]);
		compeMap.put(DocumentType.BOLETO_BANCARIO_SANTANDER, compeCodes[4]);
		compeMap.put(DocumentType.BOLETO_BANCARIO_SICREDI, compeCodes[5]);
	}
	
	
	public Reader() throws IOException {
		super();
	}
	
	public void ReadPDF(Path readPath) {			
		try {
			PDDocument doc = loadDocument(readPath);
			boolean canReadFile = canReadFile(doc);
			
			if (!canReadFile) {
				System.out.println("Não há permissão para extrair texto do documento.");
				return;
			}
			
			String text = extractBasicText(doc);
			DocumentType docType = determineDocumentType(text);
			if (docType == DocumentType.UNKNOWN) {
				System.out.println("Não foi possível determinar o tipo do documento.");
				return;
			}
			//createReaderFromType(readPath, docType);
			
			// extrai a posição de strings do arquivo
	        PDFTextStripper stripper = new Reader();
			stripper.setSortByPosition(false);
	        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	        stripper.writeText(doc, dummy);
	        
	        debugStartPositions();
			String areaTxt = extractBoletoArea(doc);

			DocumentConfiguration docConfig = DocumentConfigurationManager.getConfigurationFromType(docType);
			LinkedHashMap<DocumentField, String> dataMap = extractFieldsFromText(docConfig, areaTxt);
			String jText = generateJson(dataMap);
			
			if (jText == null) {
				System.out.println("JSON de saída ignorado. \"Reader.generateJson\" recebeu um HashMap nulo.");
				return;
			}
			
			String fileName = FilenameUtils.removeExtension(readPath.toFile().getName());
			DirectoryManager.saveJsonToOutputPath(jText, fileName);

			doc.close();
		} 
		catch (InvalidPasswordException e) {
			System.out.println("Não foi possível extrair. O arquivo \"" + readPath + "\" é criptografado com senha.");
		} 
		catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public DocumentType readAndShowPDFText(Path readPath) {	
		try {
			PDDocument doc = loadDocument(readPath);
			boolean canReadFile = canReadFile(doc);
			
			if (!canReadFile) {
				System.out.println("Não há permissão para extrair texto do documento.");
				return null;
			}
			
			String text = extractBasicText(doc);
			DocumentType docType = determineDocumentType(text);
			if (docType == DocumentType.UNKNOWN) {
				System.out.println("Não foi possível determinar o tipo do documento.");
				return null;
			}
			
	        PDFTextStripper stripper = new Reader();
			stripper.setSortByPosition(false);
	        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	        stripper.writeText(doc, dummy);
	
			String areaTxt = extractBoletoArea(doc);
			
			ExtractedTextArea.displayText(areaTxt);		
			doc.close();
			
			return docType;
		}
		catch (InvalidPasswordException e) {
			System.out.println("Não foi possível extrair. O arquivo \"" + readPath + "\" é criptografado com senha.");
			return null;
		} 
		catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}

	@Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
		// HACK: Sobrescrevendo um método protegido da classe PDFTextStripper para obter a localização do Codigo "Compe" dos boletos,
		// para tentar eliminar o máximo de texto inútil que precede o boleto
		TextPosition firstCharacterPosition = null;
		for (String code : compeCodes) {
			if (string.contains(code)) {
				firstCharacterPosition = textPositions.get(0);
				setExtractPosition(firstCharacterPosition.getXDirAdj(), firstCharacterPosition.getYDirAdj());
			}	
		}
    }
	
	public static boolean checkFileConformity(Path readPath) throws IOException {
		// TODO: INFORMAR QUE ARQUIVO NAO É PDF NO LOG
		byte[] bytes = Files.readAllBytes(readPath);
		
		if ((bytes != null && bytes.length > 4) && (
				bytes[0] == 0x25 && 
				bytes[1] == 0x50 && 
				bytes[2] == 0x44 && 
				bytes[3] == 0x46 && 
				bytes[4] == 0x2D)) {
			
				String s = new String(bytes);
				String delimiter = s.substring(s.length() - 7, s.length());
				if(delimiter.contains("%%EOF")){
					return true; 
				}
		}

		return false;
	}
	
	public static DocumentType determineDocumentType(String documentText) {
		// TODO: Determinar o tipo de documento do arquivo sendo lido. Descartar se não for possivel identificar.
		if (documentText.contains("IMPOSTO SOBRE A RENDA")) {
			return DocumentType.DECLARACAO_IMPOSTO_DE_RENDA;
		}

		for (Map.Entry<DocumentType, String> entry : compeMap.entrySet()) {
			if (documentText.contains(entry.getValue())) {
				return entry.getKey();
			}
		}
		
		return DocumentType.UNKNOWN;
	}
	
	// extrai os campos em formato String do PDF, e mapeia os campos selecionados que estão na config
	/*
	private LinkedHashMap<DocumentField, String> extractFieldsFromText(DocumentConfiguration config, String strippedText) {
		String[] outputLines = strippedText.split(System.lineSeparator());
		LinkedHashMap<DocumentField, String> dataMap = new LinkedHashMap<DocumentField, String>();
		
		// debuga as linhas
		for (int l = 1; l <= outputLines.length; l++) {
			System.out.println(l + " " + outputLines[l-1]);
		}

		List<DocumentField> fields = config.fields;
		List<String> selectedFields = config.selectedFieldsString;
		
		for (String s : selectedFields) {
			int code = DocumentConfigurationManager.boletoCodeFieldMap.get(s);
			for (DocumentField f : fields) {
				if (f.getId() == code) {
					int line = f.getLineLocated();
					dataMap.put(f, outputLines[line - 1]);
					//System.out.println(f.getFieldName().get(0) + " : " + outputLines[line - 1]);  // printa NOME_DO_CAMPO : STRING
				}
			}
		}
		
		return dataMap;
	}
	*/
	
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
				} else {
					dataMap.put(f, outputLines[f.getLineLocated() - 1]);			
				}			
			}
		}
		
		return dataMap;
	}
	
	private String generateJson(LinkedHashMap<DocumentField, String> fields) {
		if (fields.isEmpty()) {
			return null;
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject obj = new JsonObject();
		fields.forEach((field, data) -> {
			String str = field.getFieldNameAtIndexOrFirst(0).toLowerCase().replace(" ", "_");
			str = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			obj.addProperty(str, data);
		});
		
		String txt = gson.toJson(obj);
		return txt;
	}
	
	private boolean canReadFile(PDDocument doc) {
		// checa se arquivo está com senha ou não há permissão de leitura
		AccessPermission ap = doc.getCurrentAccessPermission();
		if (!ap.canExtractContent()) {
			return false;
		}
		
		return true;
	}
	
	private PDDocument loadDocument(Path readPath) throws IOException {
		// carrega o documento com o PDFBox
		FileInputStream inputStream = new FileInputStream(readPath.toFile());		
		PDDocument doc = PDDocument.load(inputStream);
		inputStream.close();
		return doc;
	}
	
	private String extractBasicText(PDDocument doc) throws IOException {
		// extrai texto do arquivo
		PDFTextStripper str = new PDFTextStripper();
		str.setSortByPosition(false);
		str.setWordSeparator(System.lineSeparator());
		String text = str.getText(doc);
		return text;
	}
	
	private String extractBoletoArea(PDDocument doc) throws IOException {
		String areaName = "boleto";
		PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
		
		Float pageWidth = doc.getPage(0).getMediaBox().getWidth();
		Float pageHeight = doc.getPage(0).getMediaBox().getHeight();
		
		stripperArea.setSortByPosition(true);
		stripperArea.setWordSeparator(System.lineSeparator());
		stripperArea.setStartPage(0);
		stripperArea.setEndPage(0);
		
		stripperArea.addRegion(areaName, new Rectangle2D.Float(0, startY, pageWidth, pageHeight - startY));	
		stripperArea.extractRegions(doc.getPage(0));	
		String strippedText = stripperArea.getTextForRegion(areaName);
		return strippedText;
	}
	
	private void setExtractPosition(float x, float y) {
		startX = x;
		startY = y;
	}
	
	private void debugStartPositions() {
		System.out.println( "\nDEBUG:   " + 
				"Start X: " + startX +
				"   " +
				"Start Y: " + startY );
	}
	
	public static <T extends Reader> T createReaderFromType(String docDir, DocumentType docType) throws IOException {		
		if (docType == DocumentType.UNKNOWN) {
			// TODO: Informar a falha no log
			System.out.println("Não foi possível determinar o tipo de documento para o arquivo: " + docDir);
		}
		else {
			try {
				return (docType == DocumentType.DECLARACAO_IMPOSTO_DE_RENDA) ? (T) new IRPFReader() : (T) new BoletoReader();
			} 
			catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return null;
	}
}