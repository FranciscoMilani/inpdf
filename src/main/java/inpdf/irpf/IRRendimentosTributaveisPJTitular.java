package inpdf.irpf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

public class IRRendimentosTributaveisPJTitular extends IRSection implements IAddable {
	@Expose
	private final IRSectionsEnum type = IRSectionsEnum.RENDIMENTOS_TRIBUTAVEIS_PJ_TITULAR;
	@Expose
	private final List<IRItem> items = new ArrayList<IRItem>();
	@Expose
	private final List<IRField> fields = Arrays.asList(
			new IRField("Nome da Fonte Pagadora"),
			new IRField("CNPJ/CPF Fonte Pagadora"),
			new IRField("Rend. Recebidos de Pes. Jurídica"),
			new IRField("Contr. Previd. Oficial"),
			new IRField("Imposto Retido Na Fonte"),
			new IRField("13º Salário"),
			new IRField("IRPF Sobre 13º Salário"));
	
	public IRRendimentosTributaveisPJTitular() {

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