package inpdf;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DocumentField {
	private Integer id;
	private ArrayList<String> names = new ArrayList<String>();
	private Integer pageLocated;
	private Integer lineLocated;
	//private Rectangle2D coordinates;
	private Boolean shouldRead = false;
	
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

	public ArrayList<String> getFieldName() {
		return names;
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