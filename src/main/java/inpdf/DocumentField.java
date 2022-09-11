package inpdf;

import java.awt.geom.Rectangle2D;

public class DocumentField {
	private String fieldName;
	private Integer pageFoundIndex;
	private Rectangle2D coordinates;
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getPageFoundIndex() {
		return pageFoundIndex;
	}

	public void setPageFoundIndex(Integer pageFoundIndex) {
		this.pageFoundIndex = pageFoundIndex;
	}
	
	public Rectangle2D getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Rectangle2D coordinates) {
		this.coordinates = coordinates;
	}
}