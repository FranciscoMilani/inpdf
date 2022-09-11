package inpdf;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentConfiguration {
	private final DocumentType type;
	private List<DocumentField> fields = new ArrayList<DocumentField>();
	private List<DocumentField> selectedFields;
	
	public DocumentConfiguration(DocumentType type) {
		this.type = type;
	}
	
	public void AddFieldToRead(DocumentField fieldToRead) {
		
	}
	
	public void RemoveFieldToRead(DocumentField fieldToRead) {
		
	}
	
	public void UpdateConfiguration(String savePath) {
		// Salvar/atualizar configuração em algum arquivo de alguma forma
	}
	
	public ArrayList<Map<DocumentField, String>> ReadConfiguration() {
		// Método a ser chamado quando o Leitor precisar ler um arquivo
		return null;
	}
}