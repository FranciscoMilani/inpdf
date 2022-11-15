package inpdf.irpf;

import java.util.HashMap;
import java.util.List;

public class IRDocumentManager {
	
	public static IRDocument irDocument = new IRDocument();
	public static IRSectionsEnum activeSection;
	public static ConfigTable table;
	
	static {
		getFieldNames(IRSectionsEnum.IDENTIFICACAO_CONTRIBUINTE);
		activeSection = IRSectionsEnum.IDENTIFICACAO_CONTRIBUINTE;
	}
	
	public IRDocumentManager(ConfigTable table) {
		IRDocumentManager.table = table;
	}
	
	public static IRSection getSection(IRSectionsEnum s) {
		return irDocument.sections.get(s);
	}
	
//	public static <T> List<T> getFields(IRSectionsEnum sectionId) {
//		IRSection section = irDocument.sections.get(sectionId);
//		return section.getFields();
//	}
	

	public static List<String> getFieldNames(IRSectionsEnum sectionId) {
		IRSection section = irDocument.sections.get(sectionId);
		return section.getFieldNames();
	}
	
	public static void updateSectionValues() {
		
	}
	
//	public static void setFieldsValues(IRSectionsEnum sectionId, List<IRField> fields) {
//		IRSection section = irDocument.sections.get(sectionId);
//		section.setFieldsValues();
//	}
}