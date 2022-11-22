package inpdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map.Entry;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

public abstract class Reader {

	public Reader() throws IOException {
		
	}
	
	public static DocumentType readAndShowPDFText(Path readPath, ExtractedTextArea extractedArea) {
		DocumentType docType = null;
		PDDocument doc = null;
		String txt;
		
		try {
			doc = loadDocument(readPath);	
			txt = extractAllText(doc);
			docType = determineDocumentType(txt);

			if (docType == DocumentType.UNKNOWN) {
				// TODO: LOG
				throw new Exception("Não foi possível ler o tipo de documento");
			}
			
			if (docType == DocumentType.DECLARACAO_IMPOSTO_DE_RENDA) {
				int startPage = 1;
				String[] pages = extractPagesText(doc, startPage, doc.getNumberOfPages());
				extractedArea.setPages(pages, startPage);
				extractedArea.displayFirst(startPage);
			} else {
				BoletoReader reader = new BoletoReader();
				StrippedLocation sl = new StrippedLocation();
				String txtFromArea;		
				
				sl.extractGeneralBoletoPosition(doc);
				txtFromArea = reader.extractBoletoAreaFromLocation(doc, sl);
				extractedArea.setPage(txtFromArea);
				extractedArea.displayFirst(1);
			}
				
			doc.close();		
			return docType;
		} 
		catch (Exception e) {
			System.out.println("ERRO: " + e.getMessage());
			e.printStackTrace();
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
		
		return docType;
	}

	public static boolean checkFileConformity(Path readPath) throws IOException {
		// TODO: INFORMAR QUE ARQUIVO NAO É PDF NO LOG
		if (!readPath.toFile().canWrite()) {
			throw new IOException("Arquivo não pode ser lido. Está aberto?");
		}
		
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
		documentText.toLowerCase();
		
		if (documentText.contains("IMPOSTO SOBRE A RENDA")) {
			return DocumentType.DECLARACAO_IMPOSTO_DE_RENDA;
		} 
		
		for (Entry<DocumentType, List<String>> entry : BoletoReader.COMPE_MAP.entrySet()) {
			for (String s : entry.getValue()) {
				if (documentText.contains(s)) {
					System.out.println("CHAVE: " + entry.getKey());
					return entry.getKey();
				}		
			}
		}	
		
		return DocumentType.UNKNOWN;
	}
	
	public static DocumentType determineDocumentType(Path filePath) {	
		PDDocument doc;
		String txt;
		
		try {
			doc = loadDocument(filePath);
			txt = extractAllText(doc);
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
		
		txt.toLowerCase();
		if (txt.contains("IMPOSTO SOBRE A RENDA")) {
			return DocumentType.DECLARACAO_IMPOSTO_DE_RENDA;
		} 
		
		for (Entry<DocumentType, List<String>> entry : BoletoReader.COMPE_MAP.entrySet()) {
			for (String s : entry.getValue()) {
				if (txt.contains(s)) {
					System.out.println("CHAVE: " + entry.getKey());
					return entry.getKey();
				}		
			}
		}	
		
		return DocumentType.UNKNOWN;
	}
	
	// carrega o documento com o PDFBox
	protected static PDDocument loadDocument(Path readPath) throws Exception {
		File file = readPath.toFile();
		FileInputStream inputStream = new FileInputStream(file);	
		
		try {
			PDDocument doc = PDDocument.load(inputStream);
			inputStream.close();
			canReadFile(doc);
			return doc;
		} catch (Exception e) {
			inputStream.close();
			throw new Exception(e);
		}
	}
	
	// checa se arquivo está com senha ou não há permissão de leitura
	protected static void canReadFile(PDDocument doc) throws Exception {
		AccessPermission ap = doc.getCurrentAccessPermission();
		if (!ap.canExtractContent()) {
			throw new Exception("Não há permissão para ler o documento.");
		}
	}
		
	// extrai texto do arquivo
	protected static String[] extractPagesText(PDDocument doc, int pageStart, int pageEnd) throws Exception {
		PDFTextStripper str = new PDFTextStripper();
		str.setSortByPosition(false);
		str.setWordSeparator(System.lineSeparator());
		
		String[] pages = new String[pageEnd-pageStart+1];
		for (int i = pageStart; i <= pageEnd; i++) {
			str.setStartPage(i);
			str.setEndPage(i);
			Array.set(pages, i - 1, str.getText(doc));
		}
		
		return pages;
	}
	
	protected static String extractAllText(PDDocument doc) throws Exception {
		// extrai texto do arquivo
		PDFTextStripper str = new PDFTextStripper();
		str.setSortByPosition(false);
		str.setWordSeparator(System.lineSeparator());
		return str.getText(doc);
	}
	
	public static <T extends Reader> T createReaderFromType(Path docPath, DocumentType docType) throws IOException {		
		if (docType == DocumentType.UNKNOWN) {
			// TODO: Informar a falha no log
			System.out.println("Não foi possível determinar o tipo de documento para o arquivo " + docPath);
		}
		else {
			try {
				return (docType == DocumentType.DECLARACAO_IMPOSTO_DE_RENDA) ?  (T) new IRPFReader() : (T) new BoletoReader();
			} 
			catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return null;
	}
}