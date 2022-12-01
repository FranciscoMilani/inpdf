package inpdf.irpf;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import inpdf.DirectoryManager;

public class IRDocumentManager {
	
	public static IRDocument irDocument;
	public static ConfigTable table;
	
	static {
		irDocument = DirectoryManager.getIRConfigFromJson();
		
		if (irDocument == null) {
			irDocument = new IRDocument();
			createJsonConfig();
		}
	}
	
	public IRDocumentManager(ConfigTable table) {
		IRDocumentManager.table = table;
		IRDocumentManager.table.setup();
	}

	public static IRSection getSection(IRSectionsEnum s) {
		irDocument.put();
		return irDocument.sections.get(s);
	}
	
	public static void updateSectionValues(IRSection section) {	
		if (section == null) {
			System.out.println("Section null \"updateSectionValues\"");
			return;
		}
		
		List<IRField> fields = new ArrayList<IRField>();
		for (int i = 0; i < table.getRowCount(); i++) {		
			String name = table.getValueAt(i, 0).toString();
			Integer line = (Integer) table.getValueAt(i, 1);
			Boolean read = (Boolean) table.getValueAt(i, 2);
			
			IRField temp = new IRField(name);		
			if (line == null) {
				temp.setLine(line);
				temp.setRead(false);
			} else {
				temp.setLine(line);
				
				if (read == null) {
					temp.setRead(false);
				} else {
					temp.setRead(read);
				}
			}
			
			fields.add(temp);
		}
		
		if (section instanceof IAddable) {
			IAddable ad = (IAddable) section;
			ad.createItem(fields);
		} else {
			section.setFieldsValues(fields);
		}
		
		irDocument.refresh();
	}
	
	public static void createJsonConfig() {
		Gson gson = new GsonBuilder().				
				setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).
				setPrettyPrinting().
				serializeNulls().
				create();
		
		String str = gson.toJson(irDocument);
		DirectoryManager.saveConfigJson(str, DirectoryManager.getIRConfigPath());
	}
}