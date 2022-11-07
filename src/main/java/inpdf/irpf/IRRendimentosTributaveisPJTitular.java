package inpdf.irpf;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

public class IRRendimentosTributaveisPJTitular {
	public ArrayList<Integer> pages;
	public final String TYPE = "Rendimentos Tributáveis PJ Titular";
	private List<Item> items = new ArrayList<Item>();
	
	public IRRendimentosTributaveisPJTitular() {
		
	}
	
	public void createItem() {
		Item item = new Item(items.size());
		items.add(item);
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public Item getItemByIndex(int index) {
		return items.get(index);
	}
	
	public Item getItemByID(int id) {
		return items.get(id - 1);
	}
	
	public class Item {
		private final int itemID;
		private List<IRField> fields;
		
		public Item(int index) {
			this.itemID = index + 1;
		}
		
		public int getID() {
			return itemID;
		}
		
		public List<IRField> getFields() {
			return fields;
		}
		
		private void addFields() {
			this.fields = Arrays.asList(
					new IRField(""),
					new IRField(""),
					new IRField("")
					);
		}
	}
}