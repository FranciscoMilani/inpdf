package inpdf.Ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicTableUI.FocusHandler;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import inpdf.DocumentConfigurationManager;

public class TableManager {
	public static JTable table;
	
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
	
	public static <T> ArrayList<T> getColumnValues(int column) {
		ArrayList<T> list = new ArrayList<T>();
		for (int i = 0; i < table.getRowCount(); i++) {
			list.add((T) table.getValueAt(i, column));
		}
		
		return list;
	}
}
