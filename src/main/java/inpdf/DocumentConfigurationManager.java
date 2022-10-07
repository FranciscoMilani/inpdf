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
	public static String[] boletoFieldNames = {"Compe", "Linha Digitável", "Local de Pagamento", "Beneficiário", "Data do Documento"}; // serve p/ definir campos da UI
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
//				fields.add(new DocumentField("Beneficiário", "Cedente", 0, 35));
//				fields.add(new DocumentField("Data do Documento", 0, 37)); 
//				fields.add(new DocumentField("Nº do Documento", 0, 0)); 
//				fields.add(new DocumentField("Espécie Documento ", 0, 0)); 
//				fields.add(new DocumentField("Aceite", 0, 0)); 
//				fields.add(new DocumentField("Data do Processamento", 0, 0)); 
//				fields.add(new DocumentField("Uso do Banco", 0, 0)); 
//				fields.add(new DocumentField("Carteira", 0, 0)); 
//				fields.add(new DocumentField("Moeda", 0,0)); 
//				fields.add(new DocumentField("Quantidade", 0, 0)); 
//				fields.add(new DocumentField("Valor", 0, 0)); 
//				fields.add(new DocumentField("Mora", 0, 0));
//				fields.add(new DocumentField("Vencimento", 0, 0)); 
//				fields.add(new DocumentField("Agência", "Código Beneficiário", 0, 0)); 
//				fields.add(new DocumentField("Nosso Número", 0, 0)); 
//				fields.add(new DocumentField("Valor do Documento", 0, 0)); 
//				fields.add(new DocumentField("Desconto", "Abatimento", 0, 0)); 
//				fields.add(new DocumentField("Outras Deduções", 0, 0)); 
//				fields.add(new DocumentField("Mora","Multa", 0, 0)); 
//				fields.add(new DocumentField("Outros Acréscimos", 0, 0));
//				fields.add(new DocumentField("Valor Cobrado", 0, 0));
//				fields.add(new DocumentField("Sacado", "Pagador", 0, 0));		 
				break;
			case BOLETO_BANCARIO_BANCO_DO_BRASIL:
				fields.add(new DocumentField(0, "Compe", 0, 1));
				fields.add(new DocumentField(1, "Linha Digitável", 0, 2));
				fields.add(new DocumentField(2, "Local de Pagamento", 0, 5));	
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