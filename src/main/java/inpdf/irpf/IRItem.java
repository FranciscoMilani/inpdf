package inpdf.irpf;

import java.util.List;

public class IRItem {
	private final IRSection parent;
	private final int itemId;
	private List<IRField> fields;
	
	public IRItem(int index, IRSection parent, List<IRField> defaultFields) {
		this.itemId = index + 1;
		this.parent = parent;
		this.fields = defaultFields;
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