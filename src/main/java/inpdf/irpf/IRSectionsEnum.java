package inpdf.irpf;

public enum IRSectionsEnum {
	IDENTIFICACAO_CONTRIBUINTE(""),
	RENDIMENTOS_TRIBUTAVEIS_PJ_TITULAR(""),
	RENDIMENTOS_ISENTOS_NAO_TRIBUTAVEIS(""),
	RENDIMENTOS_TRIBUTACAO_EXCLUSIVA(""),
	RENDIMENTOS_ACUMULADOS_PJ_TITULAR(""),
	IMPOSTO_PAGO_RETIDO(""),
	PAGAMENTOS_EFETUADOS(""),
	BENS_DIREITOS(""),
	DIVIDAS_ONUS(""),
	RENDA_VARIAVEL_DAY_TRADE("Renda Variável - Operações Comuns/Day-Trade"),
	RESUMO_DECLARACAO("Resumo da declaração");
	
	private String str;
	
	IRSectionsEnum(String str) {
		this.str = str;
	}
	
}
