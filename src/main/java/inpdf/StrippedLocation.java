package inpdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class StrippedLocation extends PDFTextStripper {
	public float startX, startY;
	
	public StrippedLocation() throws IOException {
		super();
	}
	
	@Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
    {
		// HACK: Sobrescrevendo um método protegido da classe PDFTextStripper para obter a localização do Codigo "Compe" dos boletos,
		// para tentar eliminar o máximo de texto inútil que precede o boleto
		TextPosition firstCharacterPosition = null;
		for (String code : BoletoReader.COMPE_CODES) {
			if (string.contains(code)) {
				firstCharacterPosition = textPositions.get(0);
				setExtractPosition(firstCharacterPosition.getXDirAdj(), firstCharacterPosition.getYDirAdj());
			}	
		}
    }
	
	public void extractGeneralBoletoPosition(PDDocument doc) throws Exception {
		// extrai a posição de strings do arquivo
		this.setSortByPosition(false);
		Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());

		try {
			this.writeText(doc, dummy);
		} catch (IOException e) {
			dummy.close();
			doc.close();
			throw new Exception(e);
		}
		
		debugStartPositions();
	}

	private void setExtractPosition(float x, float y) {
		System.err.println("X: " + x + " Y: " + y);
		startX = x;
		startY = y;
	}
	
	private void debugStartPositions() {
		System.out.println( "\nDEBUG:   " + 
				"Start X: " + startX +
				"   " +
				"Start Y: " + startY );
	}
}
