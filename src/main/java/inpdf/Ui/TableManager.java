package inpdf.Ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import inpdf.DocumentConfiguration;
import inpdf.DocumentConfigurationManager;
import inpdf.DocumentField;
import inpdf.DocumentType;

public class TableManager {
	public static JTable table;
	public static final DocumentType defaultBoleto = DocumentType.BOLETO_BANCARIO_BRADESCO;
	
	public static enum Column {
		NAME(0),
		LINE(1),
		BOOL(2);
		
		public final int index;
		
		Column(int i) {
			this.index = i;
		}	
	}
	
	public TableManager(JTable nTable) {
		table = nTable;	
		setupTable();
	}
	
	private static void setupTable() {
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().getColumnModel().getColumn(table.getTableHeader().getColumnModel().getColumnCount() - 1).setResizable(false);

		setNameValues(DocumentConfigurationManager.boletoFieldNames);
		DefaultTableColumnModel tcm = (DefaultTableColumnModel) TableManager.table.getColumnModel();
		TableColumn tc;
		
		tc = tcm.getColumn(1);
		tc.setMaxWidth(125);
		tc.setMinWidth(75);
		tc.setPreferredWidth(50);	
		
		tc = tcm.getColumn(2);
		tc.setMaxWidth(125);
		tc.setMinWidth(75);
		tc.setPreferredWidth(50);
	}
	
	public static void updateTableCells(DocumentType newType) {
		DocumentConfiguration config = DocumentConfigurationManager.getConfigurationFromType(newType);
		List<DocumentField> fields = config.fields;
		
		TableManager.resetValuesToNullAndFalse();
		
		if(fields == null) {
			return;
		}
		
		Integer[] lines = new Integer[fields.size()];
		Boolean[] bools = new Boolean[fields.size()];
			
		for (int i = 0; i < fields.size(); i++) {
			lines[i] = fields.get(i).getLineLocated();
			bools[i] = fields.get(i).getShouldRead();
		}

		TableManager.setLineValues(lines);
		TableManager.setBoolValues(bools);	
	}
	
	public static void setNameValues(String[] values) {
		for (int i = 0; i < values.length; i++) {
			table.setValueAt(values[i], i, 0);
		}
	}
	
	public static void setLineValues(Integer[] values) {
		for (int i = 0; i < values.length; i++) {
			table.setValueAt(values[i], i, 1);
		}
	}
	
	public static void setBoolValues(Boolean[] values) {
		for (int i = 0; i < values.length; i++) {
			table.setValueAt(values[i], i, 2);
		}
	}
	
	public static void resetValuesToNullAndFalse() {
		for (int i = 0; i < table.getRowCount(); i++) {
			for(int j = 1; j < table.getColumnCount(); j++) {
				table.setValueAt(null, i, j);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> getColumnValues(int column) {
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i < table.getRowCount(); i++) {
			list.add((T) table.getValueAt(i, column));
		}
		
		return list;
	}
}
