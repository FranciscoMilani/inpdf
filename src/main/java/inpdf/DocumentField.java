package inpdf;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class DocumentField {
	@Expose
	private Integer id;
	@Expose
	private ArrayList<String> names = new ArrayList<String>();
	@Expose
	private Integer lineLocated = null;
	@Expose
	private Boolean shouldRead = false;

	public DocumentField(Integer id, String name, Integer lineLocated) {
		names.add(name);
		this.setLineLocated(lineLocated);
		this.id = id;
	}
	
	public DocumentField(Integer id, String name, String secondName, Integer lineLocated) {
		names.add(name);
		names.add(secondName);
		this.setLineLocated(lineLocated);
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public ArrayList<String> getFieldName() {
		return names;
	}
	
	public String getFieldNameAtIndexOrFirst(int i) {
		if (i < this.names.size())
			return names.get(i);
		else
			return names.get(0);
	}

	public void setFieldName(ArrayList<String> fieldName) {
		this.names = fieldName;
	}

	public Boolean getShouldRead() {
		return shouldRead;
	}

	public void setShouldRead(Boolean shouldRead) {
		this.shouldRead = shouldRead;
	}

	public Integer getLineLocated() {
		return lineLocated;
	}

	public void setLineLocated(Integer lineLocated) {
		this.lineLocated = lineLocated;
	}
}