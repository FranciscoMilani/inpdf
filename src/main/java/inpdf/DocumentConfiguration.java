package inpdf;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

public class DocumentConfiguration {
	private final DocumentType type;
	private List<DocumentField> fields = new ArrayList<DocumentField>();
	private List<DocumentField> selectedFields;
	private Path defaultConfigPath;
	
	public DocumentConfiguration(DocumentType type) throws IOException {
		this.type = type;
		String dir = System.getProperty("user.dir") + File.separator + "configuracoes" + File.separator + this.type.toString().toLowerCase() + ".json"; 
		defaultConfigPath = Paths.get(dir);
		
		SetupFields();	
		InitConfiguration(defaultConfigPath);
	}
	
	public void AddFieldToRead(DocumentField fieldToRead) {
		
	}
	
	public void RemoveFieldToRead(DocumentField fieldToRead) {
		
	}
	
	private void SetupFields() {
		// Salvar o tipo de documento no JSON?
		
		if (type.equals(DocumentType.BOLETO_BANCARIO)) {
			fields.add(new DocumentField("Compe", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Linha Digitável", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Local de Pagamento", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Beneficiário/Cedente", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Data do Documento", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Nº do Documento", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Espécie Documento ", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Aceite", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Data do Processamento", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Uso do Banco", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Carteira", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Moeda", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Quantidade", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Valor", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Mora", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Vencimento", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Agência/Código Beneficiário", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Nosso Número", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Valor do Documento", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Desconto/Abatimento", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Outras Deduções", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Mora/Multa", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Outros Acréscimos", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Valor Cobrado", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
			fields.add(new DocumentField("Sacado/Pagador", 0, new Rectangle2D.Float(0, 0, 0 ,0)));
		} 
		else if (type.equals(DocumentType.DECLARACAO_IMPOSTO_DE_RENDA)) {
			// TODO: ADICIONAR CAMPOS DE IMPOSTO DE RENDA
		}
	}
	
	public void InitConfiguration(Path savePath) {		
		// Configuração e criação do Gson
		GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
		Gson gson = gb.create();
		
		String jsonText = gson.toJson(fields);
		
		try {
			Files.deleteIfExists(savePath);
			Files.write(savePath, jsonText.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void UpdateConfiguration(Path savePath, List<DocumentField> fields) {
		// Atualizar configuração
	}
	
	public ArrayList<Map<DocumentField, String>> ReadConfiguration(ArrayList<DocumentField> selectedFields) {
		// Método a ser chamado quando o Leitor precisar ler um arquivo
		return null;
	}
}