package inpdf;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class StrippedLocation extends PDFTextStripper {

	public StrippedLocation() throws IOException {
		super();
	}
	
//	@Override
//    protected void writeString(String string, List<TextPosition> textPositions) throws IOException
//    {
//		// HACK: Sobrescrevendo um método protegido da classe PDFTextStripper para obter a localização do Codigo "Compe" dos boletos,
//		// para tentar eliminar o máximo de texto inútil que precede o boleto
//		TextPosition firstCharacterPosition = null;
//		for (String code : compeCodes) {
//			if (string.contains(code)) {
//				firstCharacterPosition = textPositions.get(0);
//				setExtractPosition(firstCharacterPosition.getXDirAdj(), firstCharacterPosition.getYDirAdj());
//			}	
//		}
//    }

}
