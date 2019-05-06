package com.mp3.noticeboard;

import javax.swing.table.AbstractTableModel;

public class SetBoardModel extends AbstractTableModel {
	SetBoard setBoard;
	String[] columnName = new String[1];
	Object[][] data = new Object[1][1];

	public SetBoardModel(SetBoard setBoard) {
		this.setBoard = setBoard;

	}

	@Override
	public int getColumnCount() {

		return columnName.length;
	}

	@Override
	public int getRowCount() {

		return data.length;
	}

	@Override
	public String getColumnName(int col) {

		return columnName[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		return data[row][col];
	}
}
