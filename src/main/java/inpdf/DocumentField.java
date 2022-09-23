package inpdf;

import java.awt.geom.Rectangle2D;

public class DocumentField {
	private String fieldName;
	private Integer pageLocated;
	private Integer lineLocated;
	//private Rectangle2D coordinates;
	private Boolean shouldRead = false;
	
	public DocumentField(String fieldName, Integer pageFoundIndex, Integer lineLocated) {
		this.fieldName = fieldName;
		this.pageLocated = pageFoundIndex;
		this.setLineLocated(lineLocated);
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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