package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

public class DocumentConfiguration {
	private final String CONFIG_DIRECTORY_NAME = "configuracoes";
	private final Path DEFAULT_CONFIG_PATH = Paths.get(System.getProperty("user.dir") + File.separator + CONFIG_DIRECTORY_NAME);
	private final DocumentType type;
	
	private Path filePath;
	private List<DocumentField> fields = new ArrayList<DocumentField>(); // todos os campos do documento documento
	private List<DocumentField> selectedFields; // somente campos que estão marcados como "shouldRead = true" o json
	
	public DocumentConfiguration(DocumentType type) {
		this.type = type;
		createConfiguration();
	}
		
	private void setupFields() {
		// Salvar o tipo de documento no JSON?

		if (type == DocumentType.BOLETO_BANCARIO_SANTANDER) {
			fields.add(new DocumentField(1, "Compe", 0, 1));
			fields.add(new DocumentField(2, "Linha Digitável", 0, 2));
			fields.add(new DocumentField(3, "Local de Pagamento", 0, 5));
//			fields.add(new DocumentField("Beneficiário", "Cedente", 0, 35));
//			fields.add(new DocumentField("Data do Documento", 0, 37));
//			fields.add(new DocumentField("Nº do Documento", 0, 0));
//			fields.add(new DocumentField("Espécie Documento ", 0, 0));
//			fields.add(new DocumentField("Aceite", 0, 0));
//			fields.add(new DocumentField("Data do Processamento", 0, 0));
//			fields.add(new DocumentField("Uso do Banco", 0, 0));
//			fields.add(new DocumentField("Carteira", 0, 0));
//			fields.add(new DocumentField("Moeda", 0, 0));
//			fields.add(new DocumentField("Quantidade", 0, 0));
//			fields.add(new DocumentField("Valor", 0, 0));
//			fields.add(new DocumentField("Mora", 0, 0));
//			fields.add(new DocumentField("Vencimento", 0, 0));
//			fields.add(new DocumentField("Agência", "Código Beneficiário", 0, 0));
//			fields.add(new DocumentField("Nosso Número", 0, 0));
//			fields.add(new DocumentField("Valor do Documento", 0, 0));
//			fields.add(new DocumentField("Desconto", "Abatimento", 0, 0));
//			fields.add(new DocumentField("Outras Deduções", 0, 0));
//			fields.add(new DocumentField("Mora", "Multa",  0, 0));
//			fields.add(new DocumentField("Outros Acréscimos", 0, 0));
//			fields.add(new DocumentField("Valor Cobrado", 0, 0));
//			fields.add(new DocumentField("Sacado", "Pagador", 0, 0));
		}
		else if (type == DocumentType.DECLARACAO_IMPOSTO_DE_RENDA) {
			// TODO: ADICIONAR CAMPOS DE IMPOSTO DE RENDA
		}
	}
	
	public void createConfiguration() {		
		// Configuração e criação do Gson	
		setupFields();
		
		String dir = DEFAULT_CONFIG_PATH + 
				File.separator + 
				this.type.toString().toLowerCase() + 
				".json"; 
		
		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		Gson gson = gb.create();
		String jsonText = gson.toJson(fields);


		try {	
			filePath = Paths.get(dir);
			Files.deleteIfExists(filePath);
			Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);
		} 
		catch (NoSuchFileException e) {
			new File(DEFAULT_CONFIG_PATH.toString()).mkdir();
			
			try {
				Files.write(filePath, jsonText.getBytes(), StandardOpenOption.CREATE);
			}
			catch (Exception er) {
				er.printStackTrace();
				throw new RuntimeException(e);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void addFieldToRead(DocumentField fieldToRead) {
		
	}
	
	public void removeFieldToRead(DocumentField fieldToRead) {
		
	}
	
	public void updateConfiguration(Path configPath, ArrayList<DocumentField> fields) {
		// Atualizar configuração quando o usuário reconfigurar (excluir o json e salvar um novo, mais fácil)
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		
		
		
//		try {
//			Map <?, ?> map = gson.fromJson(reader, Map.class);
//			for (Map.Entry<?, ?> entry : map.entrySet()) {
//				System.out.println(entry.getKey() +  " : " + entry.getValue());
//			}
//			
//			reader.close();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public HashMap<DocumentField, String> readConfiguration(ArrayList<DocumentField> selectedFields) {
		// Método a ser chamado quando o Leitor precisar ler um arquivo
		return null;
	}
	
	public LinkedHashMap<DocumentField, Integer> getFieldLineMap() {
		LinkedHashMap<DocumentField, Integer> map = new LinkedHashMap<DocumentField, Integer>();
		for (DocumentField field : fields) {
			map.put(field, field.getLineLocated());
		}
		
		return map;
	}
	
	public List<DocumentField> getFields() {
		return this.fields;
	}
	
	public List<DocumentField> getSelectedFields() {
		return this.selectedFields;
	}
	
	private void updateSelectedFieldsList() {
		// atualizar a lista de campos selecionados que estão marcados como "shouldread"
	}
}