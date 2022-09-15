package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdfparser.PDFObjectStreamParser;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDParentTreeValue;
import org.apache.pdfbox.pdmodel.graphics.state.PDTextState;
import org.apache.pdfbox.pdmodel.interactive.annotation.handlers.PDFreeTextAppearanceHandler;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.util.filetypedetector.FileType;

public class Reader {
	
	public Reader() {
		
	}
	
	public void ReadPDF(String readPath) throws Exception {
		String regionName = "Vencimento";
		File file = new File(readPath);
		FileInputStream inputStream = new FileInputStream(file);	
		PDDocument document = PDDocument.load(inputStream);
		
		// Tipos de strippers de texto
		PDFTextStripperByArea strArea = new PDFTextStripperByArea();
		PDFTextStripper str = new PDFTextStripper();
		
		Float page0Height = document.getPage(0).getMediaBox().getHeight();
		Float page0Width = document.getPage(0).getMediaBox().getWidth();
				
		//System.out.println(str.getText(document));
		
		//Stripper de área
		strArea.addRegion(regionName, new Rectangle2D.Float(0, 0, page0Width, page0Height));
		strArea.extractRegions(document.getPage(0));
		String strippedText = strArea.getTextForRegion(regionName);
		System.out.println(strippedText);
		
		document.close();
		inputStream.close();	
	}
	
	private void CheckFileFormat() {
		// TODO: Checar se arquivo é pdf e, de alguma forma, adivinhar o tipo de documento
	}
	
	private DocumentType DetermineDocumentType() {
		// TODO: Determinar o tipo de documento do arquivo sendo lido (descartar se for imagem, ou for documento não suportado, ou não der para ler)
		return null;
	}
}