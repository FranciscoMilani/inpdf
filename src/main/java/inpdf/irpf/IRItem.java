package inpdf.irpf;

import java.util.List;

import com.google.gson.annotations.Expose;

public class IRItem {
	@Expose
	private final int itemId;
	@Expose
	private List<IRField> fields;
	
	public IRItem(int index, IRSection parent, List<IRField> fields) {
		this.itemId = index + 1;
		this.fields = fields;
	}
	
	public int getId() {
		return itemId;
	}
	
	public List<IRField> getFields() {
		return fields;
	}
}