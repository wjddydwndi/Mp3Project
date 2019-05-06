package com.mp3.util;
/*
 * 현재 접속한 계정이 보유한 객체들을 출력하는 즉 스키마를 테이블과 연결하는 모델
 */

import javax.swing.table.AbstractTableModel;

public class listModel extends AbstractTableModel{
	public String[] columnName = new String[1];
	public String[][] data = new String[1][1];
	public int getColumnCount() {
		return columnName.length;
	}
	public int getRowCount() {
		return data.length;
	}
	public String getColumnName(int col) {
		return columnName[col];
	}
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
}
