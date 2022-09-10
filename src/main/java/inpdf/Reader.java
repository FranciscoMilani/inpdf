package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdfparser.PDFObjectStreamParser;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDParentTreeValue;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.util.filetypedetector.FileType;

public class Reader {
	
	private String urlTeste = "";
	
	public Reader() throws Exception {
		ReadPDF();
	}
	
	public void ReadPDF(String readPath) throws Exception {
		String regionName = "Vencimento";
		File file = new File(readPath);
		FileInputStream inputStream = new FileInputStream(file);	
		PDDocument document = PDDocument.load(inputStream);
		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		
		stripper.addRegion(regionName, new Rectangle2D.Float(10, 10, 60, 20));
		stripper.extractRegions(document.getPage(0));
		String strippedText = stripper.getTextForRegion(regionName);
		System.out.println(strippedText);
		
		document.close();
		inputStream.close();	
	}
	
	private void CheckFileFormat() {
		// TODO: Checar se arquivo é pdf e, de alguma forma, adivinhar o tipo de documento
	}
	
	private DocumentType DetermineDocumentType() {
		// TODO: Determinar o tipo de documento do arquivo sendo lido (descartar se for imagem, ou for documento não suportado, ou não der para ler)
	}
}