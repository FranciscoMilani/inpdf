package inpdf;

import java.io.IOException;

public class BoletoReader extends Reader {
	private static final String[] compeCodes = {"001-9", "041-8", "237-2", "341-7", "033-7", "748-X"};
	
	public BoletoReader() throws IOException  {
		super();
	}
	
	public static DocumentType determineDocumentType() {
		// TODO: Determinar tipo de boleto analisando o "Compe"
		return null;
	}
	
}
