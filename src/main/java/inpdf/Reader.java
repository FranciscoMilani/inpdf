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
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Hex;
import org.apache.pdfbox.util.filetypedetector.FileTypeDetector;

public class Reader extends PDFTextStripper  {
	
	private static float startX, startY;
	private static final String[] compeCodes = {"001-9", "041-8", "104-0", "341-7", "033-7"};
	
	public Reader() throws IOException {
		super();
	}
	
	public ArrayList<HashMap<DocumentField, String>> ReadPDF(String readPath) {
		
		try {
			// checa conformidade do arquivo (se possui "magic numbers" e bytes de fim de arquivo de PDFs)
			if (!checkFileConformity(Files.readAllBytes(Paths.get(readPath)))){
				System.out.println("Arquivo não é formato PDF");
				return null;
			}
			
			// determina tipo de documento (boletos ou DIRPF)
			DocumentType dt = determineDocumentType();
			
			if (dt == DocumentType.UNKNOWN) {
				System.out.println("INDETERMINADO");
			} else if (dt == DocumentType.DECLARACAO_IMPOSTO_DE_RENDA) {
				System.out.println("IRPF");
			} else {
				System.out.println("BOLETOS");
			}

			// carrega o documento com o PDFBox
			File file = new File(readPath.toString());
			FileInputStream inputStream = new FileInputStream(file);	
			PDDocument document = PDDocument.load(inputStream);
			
			AccessPermission ap = document.getCurrentAccessPermission();
			if (!ap.canExtractContent()) {
				throw new IOException("Não há permissão para extrair texto do documento " + readPath);
			}	
			
			Map<String, Integer> mp = new HashMap<String, Integer>();
			
			PDFTextStripper str = new PDFTextStripper();
			str.setSortByPosition(false);
			str.setWordSeparator("\n");
			String text = str.getText(document);
			//System.out.println(text);
			
			
			// extrai a posição de strings do arquivo
	        PDFTextStripper stripper = new Reader();
			str.setSortByPosition(false);
	        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
	        stripper.writeText(document, dummy);
		
	        // mostra X e Y da posição do código Compe que marca o início do boleto
			System.out.println( 
					"x: " + startX +
					"   " +
					"y: " + startY );

			// area stripper
			PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
			Float pageWidth = document.getPage(0).getMediaBox().getWidth();
			Float pageHeight = document.getPage(0).getMediaBox().getHeight();
			
			stripperArea.setSortByPosition(true);
			stripperArea.setWordSeparator(System.lineSeparator());
			stripperArea.addRegion("boleto", new Rectangle2D.Float(0, startY, pageWidth, pageHeight - startY));	
			stripperArea.extractRegions(document.getPage(0));
			
			String strippedText = stripperArea.getTextForRegion("boleto");
			String[] lineTextArray = strippedText.split(System.lineSeparator());
			
			// mostra linhas
			for (int l = 1; l <= lineTextArray.length; l++) {
				System.out.println(l + " " + lineTextArray[l-1]);
			}

			document.close();
			inputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	@Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
		// HACK: Sobrescrevendo um método protegido da classe PDFTextStripper para obter a localização do Codigo "Compe" dos boletos,
		// para tentar eliminar o máximo de texto inútil que precede o conteúdo útil
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
	
	private static boolean checkFileConformity(byte[] bytes) {
		// TODO: INFORMAR QUE ARQUIVO NAO É PDF NO LOG
		// Checa se o arquivo é PDF
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
	
	private static DocumentType determineDocumentType() {
		// TODO: Determinar o tipo de documento do arquivo sendo lido. Descartar se não for possivel identificar.
		// Se for boleto, determinar também qual é
		return null;
	}
}