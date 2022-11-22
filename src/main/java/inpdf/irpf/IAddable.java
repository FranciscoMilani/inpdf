package inpdf.irpf;

import java.util.List;

public interface IAddable {
	public List<IRItem> getItems();
	public IRItem createItem(List<IRField> fields);
	public IRItem getItemByIndex(int i);
	public IRItem getItemById(int id);
}