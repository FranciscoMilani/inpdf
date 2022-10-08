package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.module.ModuleDescriptor.Exports.Modifier;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

public class DocumentConfigurationManager {
	private static final String CONFIG_DIRECTORY_NAME = "configuracoes";
	private static final Path DEFAULT_CONFIG_PATH = Paths.get(System.getProperty("user.dir") + File.separator + CONFIG_DIRECTORY_NAME);
	private static Path filePath;
	private static ArrayList<DocumentConfiguration> configurations = new ArrayList<>();
	private static HashMap<DocumentType, DocumentConfiguration> configTypeMap = new HashMap<>();
	public static String[] irfFieldNames = {"A definir"}; // serve p/ definir campos da UI
	public static String[] boletoFieldNames = {"Compe", "Linha Digitável", "Local de Pagamento", "Beneficiário", "Data do Documento", "Nº do Documento", "Espécie Documento", "Aceite", "Data do Processamento", "Uso do Banco", "Carteira", "Moeda", "Quantidade", "Valor", "Vencimento", "Agência", "Nosso Número", "Valor do Documento", "Desconto", "Outras Deduções", "Mora", "Outros Acréscimos", "Valor Cobrado", "Sacado" }; // serve p/ definir campos da UI
	public static HashMap<String, Integer> boletoCodeFieldMap = new HashMap<String, Integer>();

	static {
		// TODO: a inicialização no momento está excluindo os arquivos se ja existem e recriando-os, corrigir se implementarmos o salvamento
		for (int i = 0; i < boletoFieldNames.length; i++) {
			boletoCodeFieldMap.put(boletoFieldNames[i], i);
		}
		
		initAllConfigurations();	
	}
		
	private static void setupDefaultFields(DocumentConfiguration config) {
		List<DocumentField> fields = new ArrayList<DocumentField>();
		DocumentType type = config.type;

		switch (type) {
			case BOLETO_BANCARIO_SANTANDER:
				fields.add(new DocumentField(0, "Compe", 0, 1));
				fields.add(new DocumentField(1, "Linha Digitável", 0, 2));
				fields.add(new DocumentField(2, "Local de Pagamento", 0, 5));					
				fields.add(new DocumentField(3, "Beneficiário", "Cedente", 0, 9));
				fields.add(new DocumentField(4, "Data do Documento", 0, 17)); 
				fields.add(new DocumentField(5, "Nº do Documento", 0, 18)); 
//				fields.add(new DocumentField(6, "Espécie Documento", 0, 0)); //19
//				fields.add(new DocumentField(7, "Aceite", 0, 0)); //20
				fields.add(new DocumentField(8, "Data do Processamento", 0, 19)); //21
//				fields.add(new DocumentField(9, "Uso do Banco", 0, 0)); 
				fields.add(new DocumentField(10, "Carteira", 0, 27)); 
				fields.add(new DocumentField(11, "Moeda", 0, 28));
//				fields.add(new DocumentField(12, "Quantidade", 0, 0)); 
//				fields.add(new DocumentField(13, "Valor", 0, 0)); 
				fields.add(new DocumentField(14, "Vencimento", 0, 6)); 
				fields.add(new DocumentField(15, "Agência", "Código Beneficiário", 0, 10)); 
				fields.add(new DocumentField(16, "Nosso Número", 0, 20)); 
				fields.add(new DocumentField(17, "Valor do Documento", 0, 30)); 
//				fields.add(new DocumentField(18, "Desconto", "Abatimento", 0, 0)); 
//				fields.add(new DocumentField(19, "Outras Deduções", 0, 0)); 
//				fields.add(new DocumentField(20, "Mora","Multa", 0, 0)); 
//				fields.add(new DocumentField(21, "Outros Acréscimos", 0, 0));
//				fields.add(new DocumentField(22, "Valor Cobrado", 0, 0));
				fields.add(new DocumentField(23, "Sacado", "Pagador", 0, 49));		 
				break;
			case BOLETO_BANCARIO_BANCO_DO_BRASIL:
				break;
			case BOLETO_BANCARIO_BANRISUL:
				break;
			case BOLETO_BANCARIO_BRADESCO:
				break;
			case BOLETO_BANCARIO_ITAU:
				break;
			case BOLETO_BANCARIO_SICREDI:
				break;
			case DECLARACAO_IMPOSTO_DE_RENDA:
				break;			
		}
		
		config.fields = fields;
	}
	
	private static void initAllConfigurations() {
		DocumentType[] types = DocumentType.values();
		for (DocumentType t : types) {
			if (t.toString().contains("BOLETO_BANCARIO")) {
				createConfiguration(t);
			}
		}
	}
	
	public static DocumentConfiguration createConfiguration(DocumentType type) {		
		// Configuração e criação do Gson
		DocumentConfiguration config = new DocumentConfiguration();
		config.type = type;
		setupDefaultFields(config);

//		String dir = DEFAULT_CONFIG_PATH + 
//				File.separator + 
//				type.toString().toLowerCase() + 
//				".json"; 
//		
//		GsonBuilder gb = new GsonBuilder().
//				setPrettyPrinting().
//				excludeFieldsWithoutExposeAnnotation();
//		
//		Gson gson = gb.create();
//		String jsonText = gson.toJson(config);
//
//		try {	
//			filePath = Paths.get(dir);		
//			Files.deleteIfExists(filePath);
//			Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);				
//		} 
//		catch (NoSuchFileException e) {
//			new File(DEFAULT_CONFIG_PATH.toString()).mkdir();
//			
//			try {
//				Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);
//			}
//			catch (Exception er) {
//				er.printStackTrace();
//				throw new RuntimeException(e);
//			}
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
		
		configurations.add(config);
		configTypeMap.put(type, config);
		return config;
	}
	
	public void updateConfiguration(Path configPath, ArrayList<DocumentField> fields) {
		// TODO: Atualizar configuração quando o usuário reconfigurar (excluir o json e salvar um novo, mais fácil)
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		updateSelectedFieldsList();
	}
	
	private void updateSelectedFieldsList() {
		// atualizar a lista de campos selecionados que estão marcados como "shouldread"
	}
	
	public HashMap<DocumentField, String> readConfiguration(ArrayList<DocumentField> selectedFields) {
		// Método a ser chamado quando o Leitor precisar ler um arquivo
		return null;
	}
	
//	public LinkedHashMap<DocumentField, Integer> getFieldLineMap(DocumentConfiguration config) {
//		ArrayList<DocumentField> fields = (ArrayList<DocumentField>) config.selectedFields;
//		LinkedHashMap<DocumentField, Integer> map = new LinkedHashMap<DocumentField, Integer>();
//		for (DocumentField field : fields) {
//			map.put(field, field.getLineLocated());
//		}
//		
//		return map;
//	}
	
	public void addFieldToRead(DocumentField fieldToRead) {
		// implementar se formos salvar
	}
	
	public void removeFieldToRead(DocumentField fieldToRead) {
		// implementar se formos salvar
	}
	
	public List<DocumentField> getFields(DocumentConfiguration config) {
		// retornar todos os campos default da configuração
		return null;
	}
	
	public static DocumentConfiguration getConfigurationFromType(DocumentType configType) {
		return configTypeMap.get(configType);
	}
	
	public static List<DocumentField> getSelectedFieldsFromConfig(DocumentType configType) {
		DocumentConfiguration c = configTypeMap.get(configType);
		if (c == null) {	
			System.out.println("Nenhuma configuração com " + configType + " foi criada, ou o valor é nulo.");
		}
		
		return c.selectedFields;
	}
	
	public static void setConfigSelectedFields(DocumentType configType, ArrayList<String> selected) {
		System.out.println("Atualizando configurações do tipo " + configType);
		DocumentConfiguration c = configTypeMap.get(configType);
		c.selectedFieldsString = selected;
	}
}