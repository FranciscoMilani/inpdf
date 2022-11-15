package inpdf.irpf;

import java.util.List;

public abstract class IRSection {	
	//public abstract void addFields();
	public abstract IRSectionsEnum getType();
	public abstract List<IRField> getFields();
	public abstract List<String> getFieldNames();
	public void setFieldsValues(List<IRField> newFields) {
		List<IRField> oldFields = getFields();
		
		if (oldFields != null) {
			for (int i = 0; i < oldFields.size(); i++) {
				IRField f = newFields.get(i);
				oldFields.get(i).setLine(f.getLine());
				oldFields.get(i).setRead(f.getRead());
			}
		}
		else {
			System.err.println("CARREGAR DADO DOS ITEMS");
		}
	}
}
