package inpdf;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdfparser.PDFObjectStreamParser;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDParentTreeValue;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.graphics.state.PDTextState;
import org.apache.pdfbox.pdmodel.interactive.annotation.handlers.PDFreeTextAppearanceHandler;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.util.filetypedetector.FileType;
import org.apache.pdfbox.text.*;

public class Reader extends PDFTextStripper  {
	
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
		System.out.println(text);
		
        PDFTextStripper stripper = new Reader();
        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        stripper.writeText(document, dummy);
	
		
/*
		PDFTextStripperByArea stripperArea = new PDFTextStripperByArea();
		Float pageWidth = document.getPage(0).getMediaBox().getWidth();
		Float pageHeight = document.getPage(0).getMediaBox().getHeight();
		
		stripperArea.setSortByPosition(true);
		stripperArea.addRegion("regiao x", new Rectangle2D.Float(0, 0, pageWidth, pageHeight));
		
		stripperArea.extractRegions(document.getPage(0));
		String strippedText = stripperArea.getTextForRegion("regiao x");
		System.out.println(strippedText);
*/
        
		document.close();
		inputStream.close();
		return null;
	}
	

	@Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
		// HACK: Sobrescrevendo um método protegido da classe PDFTextStripper para obter a localização do "Compe" dos boletos,
		// para tentar eliminar o máximo de texto inútil que precede o conteúdo útil
		for (String code : compeCodes) {
			if (string.equalsIgnoreCase(code)) {
				TextPosition firstCharacterPosition = textPositions.get(0);
				setExtractPosition(firstCharacterPosition.getXDirAdj(), firstCharacterPosition.getYDirAdj());
			}	
		}
    }
	
	private void setExtractPosition(float x, float y) {
		System.out.println("x: " + x + "   y: " + y);
	}
	
	
	private static void CheckFileFormat() {
		// TODO: Checar se arquivo é pdf e, de alguma forma, adivinhar o tipo de documento
	}
	
	private static DocumentType DetermineDocumentType() {
		// TODO: Determinar o tipo de documento do arquivo sendo lido (checar Compe p/ boleto), descartar se for imagem, documento não suportado ou não der para ler)
		return null;
	}
}