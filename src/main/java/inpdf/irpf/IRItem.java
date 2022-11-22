package inpdf.irpf;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class IRItem {
	private final IRSection parent;
	@Expose
	private final int itemId;
	@Expose
	private List<IRField> fields;
	
	public IRItem(int index, IRSection parent, List<IRField> fields) {
		this.itemId = index + 1;
		this.parent = parent;
		this.fields = fields;
	}
	
	public int getId() {
		return itemId;
	}
	
	public IRSection getParent() {
		return parent;
	}
	
	public List<IRField> getFields() {
		return fields;
	}
}