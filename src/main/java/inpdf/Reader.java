package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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

public class Reader extends PDFTextStripper  {
	
	private static float startX, startY;
	
	public Reader() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final String[] compeCodes = {"001-9", "041-8", "104-0", "341-7", "033-7"};
	
	public static ArrayList<HashMap<DocumentField, String>> ReadPDF(String readPath) throws IOException {
		DocumentType dt = DetermineDocumentType();
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
	
		System.out.println( 
				"x: " + startX +
				"   " +
				"y: " + startY );

		PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
		Float pageWidth = document.getPage(0).getMediaBox().getWidth();
		Float pageHeight = document.getPage(0).getMediaBox().getHeight();
		
		stripperArea.setSortByPosition(true);
		stripperArea.setWordSeparator(System.lineSeparator());
		stripperArea.addRegion("boleto", new Rectangle2D.Float(0, startY, pageWidth, pageHeight - startY));
		
		stripperArea.extractRegions(document.getPage(0));
		String strippedText = stripperArea.getTextForRegion("boleto");
		String[] lineTextArray = strippedText.split(System.lineSeparator());
		
		for (int l = 1; l <= lineTextArray.length; l++) {
			System.out.println(l + " " + lineTextArray[l-1]);
		}

		document.close();
		inputStream.close();
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
	
	
	private static void CheckFileFormat() {
		// TODO: Checar se arquivo é pdf e, de alguma forma, adivinhar o tipo de documento
	}
	
	private static DocumentType DetermineDocumentType() {
		// TODO: Determinar o tipo de documento do arquivo sendo lido (checar Compe p/ boleto), descartar se for imagem, documento não suportado ou não der para ler)
		return null;
	}
}