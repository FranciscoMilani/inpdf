package inpdf;

import java.util.List;

import com.google.gson.annotations.Expose;

public class DocumentConfiguration {
	
	@Expose
	public DocumentType type;
	@Expose
	public List<DocumentField> fields;	
	
	public DocumentConfiguration(DocumentType type){
		this.type = type;
	}
}