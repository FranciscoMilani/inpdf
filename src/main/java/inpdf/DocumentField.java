package inpdf;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class DocumentField {
	@Expose
	private Integer id;
	@Expose
	private ArrayList<String> names = new ArrayList<String>();
	@Expose
	private Boolean shouldRead = false;
	private Integer pageLocated;
	private Integer lineLocated;
	//private Rectangle2D coordinates;
	
	public DocumentField(Integer id, String name, Integer pageFoundIndex, Integer lineLocated) {
		names.add(name);
		this.pageLocated = pageFoundIndex;
		this.setLineLocated(lineLocated);
		this.id = id;
	}
	
	public DocumentField(Integer id, String name, String secondName, Integer pageFoundIndex, Integer lineLocated) {
		names.add(name);
		names.add(secondName);
		this.pageLocated = pageFoundIndex;
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

	public int getPageFoundIndex() {
		return pageLocated;
	}

	public void setPageFoundIndex(Integer pageFoundIndex) {
		this.pageLocated = pageFoundIndex;
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