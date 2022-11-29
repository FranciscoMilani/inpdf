package inpdf;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.javatuples.Triplet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import inpdf.Ui.TableManager;

public class DocumentConfigurationManager {
	public static final LinkedHashMap<DocumentType, DocumentConfiguration> configTypeMap = new LinkedHashMap<>();
	public static final String[] boletoFieldNames = {"Compe", "Linha Digitável", "Local de Pagamento", "Beneficiário", "Data do Documento", "Número do Documento", "Espécie Documento", "Aceite", "Data do Processamento", "Uso do Banco", "Carteira", "Moeda", "Quantidade", "Valor", "Vencimento", "Agência", "Nosso Número", "Valor do Documento", "Desconto", "Outras Deduções", "Mora/Multa", "Outros Acréscimos", "Valor Cobrado", "Pagador" }; // serve p/ definir campos da UI
	public static final HashMap<String, Integer> boletoCodeFieldMap = new HashMap<String, Integer>();
	
	public static enum FieldEnum {
		COMPE("Compe"),
		LINHA_DIGITAVEL("Linha Digitável"),
		LOCAL_PAGAMENTO("Local de Pagamento"),
		BENEFICIARIO("Beneficiário"),
		DATA_DOCUMENTO("Data do Documento"),
		NUMERO_DOCUMENTO("Número do Documento"),
		ESPECIE_DOCUMENTO("Espécie Documento"),
		ACEITA("Aceite"),
		DATA_PROCESSAMENTO("Data do Processamento"),
		USO_BANCO("Uso do Banco"),
		CARTEIRA("Carteira"),
		MOEDA("Moeda"),
		QUANTIDADE("Quantidade"),
		VALOR("Valor"),
		VENCIMENTO("Vencimento"),
		AGENCIA("Agência"),
		NOSSO_NUMERO("Nosso Número"),
		VALOR_DOCUMENTO("Valor do Documento"),
		DESCONTO("Desconto"),
		OUTRAS_DEDUCOES("Outras Deduções"),
		MORA_MULTA("Mora/Multa"),
		OUTROS_ACRESCIMOS("Outros Acréscimos"),
		VALOR_COBRADO("Valor Cobrado"),
		PAGADOR("Pagador");
		
		private final String name;
			
		FieldEnum(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	static {
		for (int i = 0; i < boletoFieldNames.length; i++) {
			boletoCodeFieldMap.put(boletoFieldNames[i], i);
		}

		if (Files.exists(DirectoryManager.getBoletoConfigPath())) { 
			loadConfigurations();
		} else {
			System.out.println("Arquivo de config não existe, criando configs");
			createConfigurations();
		}
		
		TableManager.updateTableCells(TableManager.defaultBoleto);
	}
	
	private static void loadConfigurations() {
		for (DocumentType type : DocumentType.values()) {
			if (type.toString().contains("BOLETO_BANCARIO")) {
				DocumentConfiguration config = DirectoryManager.getConfigFromJson(type);
				configTypeMap.put(config.type, config);
			}
		}
	}
		
	private static void createConfigurations() {
		for (DocumentType type : DocumentType.values()) {
			if (type.toString().contains("BOLETO_BANCARIO")) {
				DocumentConfiguration config = new DocumentConfiguration(type);
				setupDefaultFields(config);
				configTypeMap.put(config.type, config);
			}
		}
		
		allConfigsToJson(configTypeMap.values());
	}
	
	@SuppressWarnings("incomplete-switch")
	private static void setupDefaultFields(DocumentConfiguration config) {
		List<DocumentField> list;
		
		switch (config.type) {
			case BOLETO_BANCARIO_SANTANDER:
				list = Arrays.asList (
						new DocumentField(0, "Compe", 1),
						new DocumentField(1, "Linha Digitável", 2),
						new DocumentField(2, "Local de Pagamento", 5),
						new DocumentField(3, "Beneficiário", "Cedente", 9),
						new DocumentField(4, "Data do Documento", 17),
						new DocumentField(5, "Nº do Documento", 18),
						new DocumentField(6, "Espécie Documento", null),
						new DocumentField(7, "Aceite", null),
						new DocumentField(8, "Data do Processamento", 19),
						new DocumentField(9, "Uso do Banco", null),
						new DocumentField(10, "Carteira", 27),
						new DocumentField(11, "Moeda", 28),
						new DocumentField(12, "Quantidade", null),
						new DocumentField(13, "Valor", null),
						new DocumentField(14, "Vencimento", 6),
						new DocumentField(15, "Agência", "Código Beneficiário", 10),
						new DocumentField(16, "Nosso Número", 20),
						new DocumentField(17, "Valor do Documento", 30),
						new DocumentField(18, "Desconto", "Abatimento", null),
						new DocumentField(19, "Outras Deduções", null),
						new DocumentField(20, "Mora","Multa", null),
						new DocumentField(21, "Outros Acréscimos", null),
						new DocumentField(22, "Valor Cobrado", null),
						new DocumentField(23, "Sacado", "Pagador", 49)
				);	
				config.fields = list;	
				break;
			case BOLETO_BANCARIO_BANCO_DO_BRASIL:
				list = Arrays.asList (
						new DocumentField(0, "Compe", null),
						new DocumentField(1, "Linha Digitável", null),
						new DocumentField(2, "Local de Pagamento", null),
						new DocumentField(3, "Beneficiário", "Cedente", null),
						new DocumentField(4, "Data do Documento", null),
						new DocumentField(5, "Nº do Documento", null),
						new DocumentField(6, "Espécie Documento", null),
						new DocumentField(7, "Aceite", null),
						new DocumentField(8, "Data do Processamento", null),
						new DocumentField(9, "Uso do Banco", null),
						new DocumentField(10, "Carteira", null),
						new DocumentField(11, "Moeda", null),
						new DocumentField(12, "Quantidade", null),
						new DocumentField(13, "Valor", null),
						new DocumentField(14, "Vencimento", null),
						new DocumentField(15, "Agência", "Código Beneficiário", null),
						new DocumentField(16, "Nosso Número", null),
						new DocumentField(17, "Valor do Documento", null),
						new DocumentField(18, "Desconto", "Abatimento", null),
						new DocumentField(19, "Outras Deduções", null),
						new DocumentField(20, "Mora","Multa", null),
						new DocumentField(21, "Outros Acréscimos", null),
						new DocumentField(22, "Valor Cobrado", null),
						new DocumentField(23, "Sacado", "Pagador", null)
						);
				config.fields = list;
				break;
			case BOLETO_BANCARIO_BANRISUL:
				list = Arrays.asList (
						new DocumentField(0, "Compe", null),
						new DocumentField(1, "Linha Digitável", null),
						new DocumentField(2, "Local de Pagamento", null),
						new DocumentField(3, "Beneficiário", "Cedente", null),
						new DocumentField(4, "Data do Documento", null),
						new DocumentField(5, "Nº do Documento", null),
						new DocumentField(6, "Espécie Documento", null),
						new DocumentField(7, "Aceite", null),
						new DocumentField(8, "Data do Processamento", null),
						new DocumentField(9, "Uso do Banco", null),
						new DocumentField(10, "Carteira", null),
						new DocumentField(11, "Moeda", null),
						new DocumentField(12, "Quantidade", null),
						new DocumentField(13, "Valor", null),
						new DocumentField(14, "Vencimento", null),
						new DocumentField(15, "Agência", "Código Beneficiário", null),
						new DocumentField(16, "Nosso Número", null),
						new DocumentField(17, "Valor do Documento", null),
						new DocumentField(18, "Desconto", "Abatimento", null),
						new DocumentField(19, "Outras Deduções", null),
						new DocumentField(20, "Mora","Multa", null),
						new DocumentField(21, "Outros Acréscimos", null),
						new DocumentField(22, "Valor Cobrado", null),
						new DocumentField(23, "Sacado", "Pagador", null)
						);
				config.fields = list;
				break;
			case BOLETO_BANCARIO_BRADESCO:
				list = Arrays.asList (
						new DocumentField(0, "Compe", null),
						new DocumentField(1, "Linha Digitável", null),
						new DocumentField(2, "Local de Pagamento", null),
						new DocumentField(3, "Beneficiário", "Cedente", null),
						new DocumentField(4, "Data do Documento", null),
						new DocumentField(5, "Nº do Documento", null),
						new DocumentField(6, "Espécie Documento", null),
						new DocumentField(7, "Aceite", null),
						new DocumentField(8, "Data do Processamento", null),
						new DocumentField(9, "Uso do Banco", null),
						new DocumentField(10, "Carteira", null),
						new DocumentField(11, "Moeda", null),
						new DocumentField(12, "Quantidade", null),
						new DocumentField(13, "Valor", null),
						new DocumentField(14, "Vencimento", null),
						new DocumentField(15, "Agência", "Código Beneficiário", null),
						new DocumentField(16, "Nosso Número", null),
						new DocumentField(17, "Valor do Documento", null),
						new DocumentField(18, "Desconto", "Abatimento", null),
						new DocumentField(19, "Outras Deduções", null),
						new DocumentField(20, "Mora","Multa", null),
						new DocumentField(21, "Outros Acréscimos", null),
						new DocumentField(22, "Valor Cobrado", null),
						new DocumentField(23, "Sacado", "Pagador", null)
						);
				config.fields = list;
				break;
			case BOLETO_BANCARIO_ITAU:
				list = Arrays.asList (
						new DocumentField(0, "Compe", null),
						new DocumentField(1, "Linha Digitável", null),
						new DocumentField(2, "Local de Pagamento", null),
						new DocumentField(3, "Beneficiário", "Cedente", null),
						new DocumentField(4, "Data do Documento", null),
						new DocumentField(5, "Nº do Documento", null),
						new DocumentField(6, "Espécie Documento", null),
						new DocumentField(7, "Aceite", null),
						new DocumentField(8, "Data do Processamento", null),
						new DocumentField(9, "Uso do Banco", null),
						new DocumentField(10, "Carteira", null),
						new DocumentField(11, "Moeda", null),
						new DocumentField(12, "Quantidade", null),
						new DocumentField(13, "Valor", null),
						new DocumentField(14, "Vencimento", null),
						new DocumentField(15, "Agência", "Código Beneficiário", null),
						new DocumentField(16, "Nosso Número", null),
						new DocumentField(17, "Valor do Documento", null),
						new DocumentField(18, "Desconto", "Abatimento", null),
						new DocumentField(19, "Outras Deduções", null),
						new DocumentField(20, "Mora","Multa", null),
						new DocumentField(21, "Outros Acréscimos", null),
						new DocumentField(22, "Valor Cobrado", null),
						new DocumentField(23, "Sacado", "Pagador", null)
						);
				config.fields = list;
				break;
			case BOLETO_BANCARIO_SICREDI:
				list = Arrays.asList (
						new DocumentField(0, "Compe", null),
						new DocumentField(1, "Linha Digitável", null),
						new DocumentField(2, "Local de Pagamento", null),
						new DocumentField(3, "Beneficiário", "Cedente", null),
						new DocumentField(4, "Data do Documento", null),
						new DocumentField(5, "Nº do Documento", null),
						new DocumentField(6, "Espécie Documento", null),
						new DocumentField(7, "Aceite", null),
						new DocumentField(8, "Data do Processamento", null),
						new DocumentField(9, "Uso do Banco", null),
						new DocumentField(10, "Carteira", null),
						new DocumentField(11, "Moeda", null),
						new DocumentField(12, "Quantidade", null),
						new DocumentField(13, "Valor", null),
						new DocumentField(14, "Vencimento", null),
						new DocumentField(15, "Agência", "Código Beneficiário", null),
						new DocumentField(16, "Nosso Número", null),
						new DocumentField(17, "Valor do Documento", null),
						new DocumentField(18, "Desconto", "Abatimento", null),
						new DocumentField(19, "Outras Deduções", null),
						new DocumentField(20, "Mora","Multa", null),
						new DocumentField(21, "Outros Acréscimos", null),
						new DocumentField(22, "Valor Cobrado", null),
						new DocumentField(23, "Sacado", "Pagador", null)
						);
				config.fields = list;
				break;
			case DECLARACAO_IMPOSTO_DE_RENDA:
				list = Arrays.asList (
						new DocumentField(0, "Compe", null),
						new DocumentField(1, "Linha Digitável", null),
						new DocumentField(2, "Local de Pagamento", null),
						new DocumentField(3, "Beneficiário", "Cedente", null),
						new DocumentField(4, "Data do Documento", null),
						new DocumentField(5, "Nº do Documento", null),
						new DocumentField(6, "Espécie Documento", null),
						new DocumentField(7, "Aceite", null),
						new DocumentField(8, "Data do Processamento", null),
						new DocumentField(9, "Uso do Banco", null),
						new DocumentField(10, "Carteira", null),
						new DocumentField(11, "Moeda", null),
						new DocumentField(12, "Quantidade", null),
						new DocumentField(13, "Valor", null),
						new DocumentField(14, "Vencimento", null),
						new DocumentField(15, "Agência", "Código Beneficiário", null),
						new DocumentField(16, "Nosso Número", null),
						new DocumentField(17, "Valor do Documento", null),
						new DocumentField(18, "Desconto", "Abatimento", null),
						new DocumentField(19, "Outras Deduções", null),
						new DocumentField(20, "Mora","Multa", null),
						new DocumentField(21, "Outros Acréscimos", null),
						new DocumentField(22, "Valor Cobrado", null),
						new DocumentField(23, "Sacado", "Pagador", null)
						);
				config.fields = list;
				break;
		}
	}
	
	public static DocumentConfiguration getConfigurationFromType(DocumentType configType) {
		return configTypeMap.get(configType);
	}
	
	public static void updateConfigurationValues(DocumentConfiguration config, List<Triplet<String, Integer, Boolean>> values) {
		if (config.fields != null) {			
			// TODO: pra cada string do enumerador, se for igual à alguma string da lista de campos, atribuir
			
			for (int i = 0; i < config.fields.size(); i++) {
				config.fields.get(i).setLineLocated(values.get(i).getValue1());
				config.fields.get(i).setShouldRead(values.get(i).getValue2());
			}	
		}
	}
	
	public static void allConfigsToJson(Collection<DocumentConfiguration> collection) {	
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		JsonArray jArr = new JsonArray();
		JsonObject jObj = new JsonObject();
		JsonElement jEle = null;
		
		for (DocumentConfiguration dc : collection) {
			jEle = gson.toJsonTree(dc);
			jObj.add(dc.type.toString(), jEle);
		}
		
		jArr.add(jObj);
		String text = gson.toJson(jArr);	
		DirectoryManager.saveConfigJson(text, DirectoryManager.getBoletoConfigPath());	
	}
}