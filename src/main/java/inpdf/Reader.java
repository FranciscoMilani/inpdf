package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

public class Reader extends PDFTextStripper  {
	
	private static float startX, startY;
	private static final String[] compeCodes = {"001-9", "041-8", "237-2", "341-7", "033-7", "748-X"};
	private static Map<DocumentType, String> compeMap;
	private List<DocumentField> selectedFields;
	
	static {
		compeMap = new HashMap<>(6);
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
	
	public HashMap<DocumentField, String> ReadPDF(String readPath) {	
		try {
			Map<String, Integer> mp = new HashMap<String, Integer>();
			
			// carrega o documento com o PDFBox
			File file = new File(readPath.toString());
			FileInputStream inputStream = new FileInputStream(file);		
			PDDocument document = PDDocument.load(inputStream);
			
			if(document.isEncrypted()) {
				System.out.println("Encrypted");
			}	

			AccessPermission ap = document.getCurrentAccessPermission();
			if (!ap.canExtractContent()) {
				throw new IOException("Não há permissão para extrair texto do documento: " + readPath);
			}			
			
			PDFTextStripper str = new PDFTextStripper();
			str.setSortByPosition(false);
			str.setWordSeparator(System.lineSeparator());
			String text = str.getText(document);
			determineDocumentType(text);
			
			// extrai a posição de strings do arquivo
	        PDFTextStripper stripper = new Reader();
			str.setSortByPosition(false);
	        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	        stripper.writeText(document, dummy);
		
	        // mostra X e Y da posição do código Compe que marca o início do boleto
			System.out.println( "\nDEBUG:   " + 
					"Start X: " + startX +
					"   " +
					"Start Y: " + startY );

			// area stripper
			PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
			Float pageWidth = document.getPage(0).getMediaBox().getWidth();
			Float pageHeight = document.getPage(0).getMediaBox().getHeight();
			stripperArea.setSortByPosition(true);
			stripperArea.setWordSeparator(System.lineSeparator());
			stripperArea.setStartPage(0);
			stripperArea.setEndPage(0);
			stripperArea.addRegion("boleto", new Rectangle2D.Float(0, startY, pageWidth, pageHeight - startY));	
			stripperArea.extractRegions(document.getPage(0));			
			String strippedText = stripperArea.getTextForRegion("boleto");
			
			DocumentConfigurationManager docManager = new DocumentConfigurationManager();
			DocumentConfiguration docConfig = docManager.createConfiguration(DocumentType.BOLETO_BANCARIO_SANTANDER);
			LinkedHashMap<DocumentField, String> dataMap = extractFieldsFromText(docConfig, strippedText);
			String jText = GenerateJsonObject(dataMap);
			String fileName = FilenameUtils.removeExtension(file.getName());
			DirectoryManager.saveJsonToOutputPath(jText, fileName);

			document.close();
			inputStream.close();
			
		} catch (InvalidPasswordException e) {
			System.out.println("Não foi possível ler, o arquivo \"" + readPath + "\" é criptografado com senha.");
		}
		catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return null;
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
	
	private void setExtractPosition(float x, float y) {
		startX = x;
		startY = y;
	}
	
	public static boolean checkFileConformity(String readPath) throws IOException {
		// TODO: INFORMAR QUE ARQUIVO NAO É PDF NO LOG
		byte[] bytes = Files.readAllBytes(Paths.get(readPath));
		
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
	private LinkedHashMap<DocumentField, String> extractFieldsFromText(DocumentConfiguration config, String strippedText) {
		String[] outputLines = strippedText.split(System.lineSeparator());
		LinkedHashMap<DocumentField, String> dataMap = new LinkedHashMap<DocumentField, String>();
		
		// debuga as linhas
		for (int l = 1; l <= outputLines.length; l++) {
			System.out.println(l + " " + outputLines[l-1]);
		}
		
		List<DocumentField> fields = config.fields;
		for (DocumentField f : fields) {
			int line = f.getLineLocated();
			dataMap.put(f, outputLines[line - 1]);
			System.out.println(f.getFieldName().get(0) + " : " + outputLines[line - 1]);  // printa NOME_DO_CAMPO : STRING
		}
		
		return dataMap;
	}
	
	private String GenerateJsonObject(LinkedHashMap<DocumentField, String> fields) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject obj = new JsonObject();
		
		fields.forEach((field, data) -> {
			obj.addProperty(field.getFieldNameAtIndexOrFirst(0), data);
		});
		
		String txt = gson.toJson(obj);
		return txt;
	}
}