package inpdf;

public enum DocumentType {
	UNKNOWN ("Indeterminado"),
	BOLETO_BANCARIO_BANCO_DO_BRASIL ("Boleto Banco do Brasil"),
	BOLETO_BANCARIO_BANRISUL ("Boleto Banrisul"),
	BOLETO_BANCARIO_BRADESCO ("Boleto Bradesco"),
	BOLETO_BANCARIO_ITAU ("Boleto Itaú"),
	BOLETO_BANCARIO_SANTANDER ("Boleto Santander"),
	BOLETO_BANCARIO_SICREDI ("Boleto Sicredi"),
	DECLARACAO_IMPOSTO_DE_RENDA ("Declaração IRPF");
	
	private String type;

	private DocumentType(String type){
	    this.type = type;
	}

	public String getDocumentType(){
	    return this.type;
	}

	public String toStringFormatted(){
		return this.type;
	}
}