package inpdf;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class DocumentConfiguration {
	
	@Expose
	public DocumentType type;
	@Expose
	public List<DocumentField> fields;	
	public List<DocumentField> selectedFields; // tirar?
	public List<String> selectedFieldsString;
	
	public DocumentConfiguration(DocumentType type){
		this.type = type;
	}
}