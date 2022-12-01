package inpdf.irpf;

import java.util.List;

public interface IAddable {
	public List<IRItem> getItems();
	public default void resetItems() {
		List<IRItem> items = getItems();
		items.clear();
	}
	public IRItem createItem(List<IRField> fields);
	public IRItem getItemByIndex(int i);
	public IRItem getItemById(int id);
}