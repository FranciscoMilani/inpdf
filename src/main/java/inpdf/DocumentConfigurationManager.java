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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javatuples.Triplet;

import com.google.gson.*;

public class DocumentConfigurationManager {
	private static final String CONFIG_DIRECTORY_NAME = "configuracoes";
	private static final Path DEFAULT_CONFIG_PATH = Paths.get(System.getProperty("user.dir") + File.separator + CONFIG_DIRECTORY_NAME);
	private static Path filePath;
	private static HashMap<DocumentType, DocumentConfiguration> configTypeMap = new HashMap<>();
	public static String[] irfFieldNames = {"A definir"}; // serve p/ definir campos da UI
	public static String[] boletoFieldNames = {"Compe", "Linha Digitável", "Local de Pagamento", "Beneficiário", "Data do Documento", "Nº do Documento", "Espécie Documento", "Aceite", "Data do Processamento", "Uso do Banco", "Carteira", "Moeda", "Quantidade", "Valor", "Vencimento", "Agência", "Nosso Número", "Valor do Documento", "Desconto", "Outras Deduções", "Mora", "Outros Acréscimos", "Valor Cobrado", "Sacado" }; // serve p/ definir campos da UI
	public static HashMap<String, Integer> boletoCodeFieldMap = new HashMap<String, Integer>();
	
	/*
	public static enum FieldEnum{
		COMPE("Compe"),
		LINHA_DIGITAVEL("Linha Digitável");
		
		public final String name;
			
		FieldEnum(String name) {
			this.name = name;
		}	
	}
	*/
	
	static {
		// TODO: a inicialização no momento está excluindo os arquivos se ja existem e recriando-os, corrigir se implementarmos o salvamento
		for (int i = 0; i < boletoFieldNames.length; i++) {
			boletoCodeFieldMap.put(boletoFieldNames[i], i);
		}
		
		initAllConfigurations();	
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
		DocumentConfiguration config = new DocumentConfiguration(type);
		setupDefaultFields(config);		
		configTypeMap.put(config.type, config);
		return config;

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
	}
	
	public static void updateConfigurationValues(DocumentConfiguration config, List<Triplet<String, Integer, Boolean>> values) {
		if (config.fields != null) {
			for (int i = 0; i < config.fields.size(); i++) {
				config.fields.get(i).setLineLocated(values.get(i).getValue1());
				config.fields.get(i).setShouldRead(values.get(i).getValue2());
			}	
		}
		
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