package inpdf.irpf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ConfigTable extends JTable implements ActionListener {
	
	private final String[] columns = new String[] {"Campo", "Linha", "Marcar"};
	private final IRSectionsEnum defaultSection = IRSectionsEnum.IDENTIFICACAO_CONTRIBUINTE;
	public IRSectionsEnum selectedSection;
	private JPanel buttonsPanel;
	
	public static enum ColumnEnum {
		FIELD(0),
		LINE(1),
		CHECK(2);
		
		private final int column;
		
		ColumnEnum(int col) {
			this.column = col;
		}
		
		public int getCol() {
			return column;
		}
	}
	
	DefaultTableModel tableModel = new DefaultTableModel(null, columns) {
		public boolean isCellEditable(int row, int column) {
			return column != 0;
		}
		
		public Class<?> getColumnClass(int column) {
			if (column == 2) {	
				return Boolean.class;
			}
			else if (column ==  1) {
				return Integer.class;
			}
			else if (column == 0) {
				return String.class;
			}
			else {
				return String.class;
			}
		}
	};

	public ConfigTable(JPanel buttons) {
		super();
		
		setModel(tableModel);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(true);
		getTableHeader().setReorderingAllowed(false);
			
		//tableModel.addTableModelListener();
		this.buttonsPanel = buttons;
		
		setup();	
	}
	
	private void setup() {
		selectSection(defaultSection);
	}
	
	public void selectSection(IRSectionsEnum section) {
		selectedSection = section;
		resetValuesToNull();
		setValuesForSection(selectedSection);
	}
	
	private void setValuesForSection(IRSectionsEnum section) {
		List<String> names = IRDocumentManager.getFieldNames(section);
		List<IRField> fields = IRDocumentManager.getSection(section).getFields();
		tableModel.setRowCount(names.size());
		
		// preenche os nomes
		for (int i = 0; i < names.size(); i++) {		
			setValueAt(names.get(i), i, ColumnEnum.FIELD.getCol());
		}

		if (fields == null) {
			System.out.println("Não tem campos");
			return;
		}
		
		// preenche os outros campos
		for (int i = 0; i < fields.size(); i++) {
			setValueAt(fields.get(i).getLine(), i, ColumnEnum.LINE.getCol());	
			setValueAt(fields.get(i).getRead(), i, ColumnEnum.CHECK.getCol());
		}	
	}

	
	public void resetValuesToNull() {
		for (int i = 0; i < getRowCount(); i++) {
			for(int j = 0; j < getColumnCount(); j++) {
				setValueAt(null, i, j);
			}
		}
	}
	
	public void resetValuesToNullExcept(int exceptCol) {
		for (int i = 0; i < getRowCount(); i++) {
			for(int j = 0; j < getColumnCount(); j++) {
				if (j == exceptCol) continue;
				setValueAt(null, i, j);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox) {
			JComboBox box = (JComboBox) e.getSource();
			IRSectionsEnum selected = (IRSectionsEnum) box.getSelectedItem();
			
			if (IRDocumentManager.getSection(selected) instanceof IAddable) {
				buttonsPanel.setEnabled(true);
				buttonsPanel.setVisible(true);
				//Arrays.asList(buttons).forEach(b -> setEnabled(true));
			} else {
				buttonsPanel.setEnabled(false);
				buttonsPanel.setVisible(false);
				//Arrays.asList(buttons).forEach(b -> setEnabled(false));
			}
			
			selectSection(selected);
		}
	}
	
//	private void updateFields(JComboBox box) {
//		IRSectionsEnum selected = (IRSectionsEnum) box.getSelectedItem();
//		
//		if (selected == IRSectionsEnum.IDENTIFICACAO_CONTRIBUINTE) {
//			
//		}else if (selected == IRSectionsEnum.RENDIMENTOS_TRIBUTAVEIS_PJ_TITULAR) {
//			
//		}else if (selected == IRSectionsEnum.RENDIMENTOS_ISENTOS_NAO_TRIBUTAVEIS) {
//			
//		}else if (selected == IRSectionsEnum.RENDIMENTOS_TRIBUTACAO_EXCLUSIVA) {
//			
//		}else if (selected == IRSectionsEnum.RENDIMENTOS_ACUMULADOS_PJ_TITULAR) {
//			
//		}else if (selected == IRSectionsEnum.IMPOSTO_PAGO_RETIDO) {
//			
//		}else if (selected == IRSectionsEnum.PAGAMENTOS_EFETUADOS) {
//			
//		}else if (selected == IRSectionsEnum.BENS_DIREITOS) {
//			
//		}else if (selected == IRSectionsEnum.DIVIDAS_ONUS) {
//			
//		}else if (selected == IRSectionsEnum.RENDA_VARIAVEL_DAY_TRADE) {
//			
//		}else if (selected == IRSectionsEnum.RESUMO_DECLARACAO) {
//			
//		}
//	}
}
