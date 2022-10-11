package inpdf;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

import com.google.gson.annotations.Expose;

public class DocumentField {
	@Expose
	private Integer id;
	@Expose
	private ArrayList<String> names = new ArrayList<String>();
	@Expose
	private Integer lineLocated;
	@Expose
	private Boolean shouldRead = false;
	
	//private Rectangle2D coordinates;
	
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
	
//	public Rectangle2D getCoordinates() {
//		return coordinates;
//	}
//	
//	public void setCoordinates(Rectangle2D coordinates) {
//		this.coordinates = coordinates;
//	}

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