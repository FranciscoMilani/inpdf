package inpdf.irpf;

import java.util.List;

public interface IAddable {
	public List<List<IRField>> getItemsList();
	public List<IRItem> getItems();
	public IRItem createItem();
}