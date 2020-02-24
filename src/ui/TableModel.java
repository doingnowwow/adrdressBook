package ui;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

	public TableModel(String[] columnNames, int rowCount) {
		 super(convertToVector(columnNames), rowCount);

	}

 
	@Override
	public Class getColumnClass(int column) {
		
		switch (column) {
		case 0:

			return boolean.class; 
		case 1:
				return String.class; 
		case 2:
			return String.class; 
		case 3:

			return String.class; 
		case 4:

			return String.class; 
		case 5:

			return String.class; 
		case 6:

			return String.class; 
		case 7:

			return String.class; 
		case 8:

			return String.class; 
		case 9:

			return String.class; 

		default:
			return String.class; 
		}
		
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 0) {
			return true;
		}

		return false;

	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}