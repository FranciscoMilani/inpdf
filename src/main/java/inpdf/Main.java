package inpdf;

public class Main {

	public static void main(String[] args) throws Exception{
		// Instanciar a interface aqui
		Reader reader = new Reader();
		String projectDir = System.getProperty("user.dir");
		String documentDir = projectDir + "//documentospdf//boleto-facil-exemplo.pdf";
		//String documentDir = projectDir + "//documentospdf//boleto-ads.pdf";  CONFIGURAÇÃO FRANCISCO
		reader.ReadPDF(documentDir);
		
		DocumentConfiguration doc = new DocumentConfiguration(DocumentType.BOLETO_BANCARIO);
	}
}
