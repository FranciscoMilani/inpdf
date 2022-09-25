package inpdf;

import java.io.IOException;

public class BoletoReader extends Reader {
	private static final String[] compeCodes = {"001-9", "041-8", "104-0", "341-7", "033-7"};
	
	public BoletoReader() throws IOException  {
		super();
	}
	
	private static DocumentType determineBoletoType() {
		// TODO: Determinar tipo de boleto analisando o "Compe"
		return null;
	}
	
}
