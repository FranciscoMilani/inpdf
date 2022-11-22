package inpdf.irpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

public class IRRendimentosIsentos extends IRSection implements IAddable {
	@Expose
	private final IRSectionsEnum type = IRSectionsEnum.RENDIMENTOS_ISENTOS_NAO_TRIBUTAVEIS;	
	@Expose
	private final List<IRItem> items = new ArrayList<IRItem>();
	@Expose
	private final List<IRField> fields = Arrays.asList(
			new IRField("Tipo de Rendimento"),
			new IRField("Fonte Pagadora"),
			new IRField("Beneficiário"),
			new IRField("Valor (R$)"));
	
	
	public IRRendimentosIsentos() {

	}

	public List<IRItem> getItems() {
		return items;
	}
	
	@Override
	public IRItem getItemByIndex(int i) {
		return items.get(i);
	}
	
	@Override
	public IRItem getItemById(int id) {
		return items.get(id - 1);
	}
	
	@Override
	public IRItem createItem(List<IRField> fields) {
		IRItem item = new IRItem(items.size(), this, fields);
		items.add(item);
		return item;
	}
	
	@Override
	public IRSectionsEnum getType() {
		return type;
	}

	@Override
	public List<IRField> getFields() {
		return fields;
	}
}