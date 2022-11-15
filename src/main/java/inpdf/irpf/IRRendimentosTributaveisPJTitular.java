package inpdf.irpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IRRendimentosTributaveisPJTitular extends IRSection implements IAddable {
	public final IRSectionsEnum type = IRSectionsEnum.RENDIMENTOS_TRIBUTAVEIS_PJ_TITULAR;
	public ArrayList<Integer> pages;
	private List<IRItem> items = new ArrayList<IRItem>();
	private List<String> fieldNames = Arrays.asList("Nome",
													"Data de Nascimento",
													"CPF",
													"Título Eleitoral",
													"Possui cônjuge ou companheiro(a)",
													"CPF do cônjuge ou companheiro(a)",
													"Endereço");
	
	public IRRendimentosTributaveisPJTitular() {
		
	}
	
	public List<IRItem> getItems() {
		return items;
	}
	
	public IRItem getItemByIndex(int index) {
		return items.get(index);
	}
	
	public IRItem getItemByID(int id) {
		return items.get(id - 1);
	}
	
	@Override
	public IRItem createItem() {
		IRItem item = new IRItem(items.size(), this, this.getFields());
		items.add(item);
		return item;
	}
	
	@Override
	public List<List<IRField>> getItemsList() {
		List<List<IRField>> list = new ArrayList<List<IRField>>();
		for (IRItem item : items) {
			list.add(item.getFields());
		}
		
		return list;
	}
	
	@Override
	public List<String> getFieldNames() {
		return fieldNames;
	}

	@Override
	public List<IRField> getFields() {
		return null;
	}

	@Override
	public IRSectionsEnum getType() {
		return type;
	}
}