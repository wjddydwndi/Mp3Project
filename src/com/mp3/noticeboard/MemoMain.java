package com.mp3.noticeboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class MemoMain extends JPanel {
	MemoTableModel memoTableModel;
	ClickedPage clickedPage;

	JTable memo_table;
	JScrollPane memo_scroll;
	JPanel p_memoboard;
	JTextField t_memo;
	JButton bt_regist;
	JButton bt_del; // 댓글삭제

	Connection con;
	int board_id;// 해당글에 댓글을 불러오기 위한 글 번호.

	int select_memo;// 삭제하기 위해 선택한 댓글.
	String select_member;// 댓글쓴 회원과 일치할 때만, 삭제

	public MemoMain(ClickedPage clickedPage) {
		this.clickedPage = clickedPage;

		System.out.println("memeMain으로 넘어온 board_id first!" + board_id);

		memoTableModel = new MemoTableModel(this);
		memo_table = new JTable();
		memo_scroll = new JScrollPane(memo_table);
		p_memoboard = new JPanel();
		t_memo = new JTextField();
		bt_regist = new JButton("등록");
		bt_del = new JButton("댓글삭제");

		memo_scroll.setPreferredSize(new Dimension(770, 160));
		memo_table.setBackground(Color.BLACK);
		memo_table.setForeground(Color.GREEN);

		t_memo.setPreferredSize(new Dimension(550, 20));
		t_memo.setBackground(Color.GREEN);

		bt_regist.setPreferredSize(new Dimension(100, 20));
		bt_del.setPreferredSize(new Dimension(100, 20));

		p_memoboard.setBackground(Color.BLACK);

		add(memo_scroll);
		add(p_memoboard);
		p_memoboard.add(t_memo);
		p_memoboard.add(bt_regist);
		p_memoboard.add(bt_del);

		showMemo();

		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertMemo();
				showMemo();
				t_memo.setText("");

			}
		});

		memo_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = memo_table.getSelectedRow();
				int col = 0;
				System.out.println(memo_table.getValueAt(row, col));
				select_memo = (int) (memo_table.getValueAt(row, col));
				select_member = (String) (memo_table.getValueAt(row, 2));
				System.out.println("댓글 select_member " + select_member);
			}
		});

		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("dd   "+select_member);
				System.out.println("ee   "+clickedPage.boardMain.id);
				if (select_member.contentEquals(clickedPage.boardMain.id)) {
					delMemo();
					showMemo();
				} else {
					JOptionPane.showMessageDialog(null, "해당 글은 삭제할 수 없습니다.");
				}
			}
		});

		// table Size조정

		DefaultTableCellRenderer cellCenter = new DefaultTableCellRenderer();
		cellCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer cellRight = new DefaultTableCellRenderer();
		cellRight.setHorizontalAlignment(JLabel.RIGHT);

		memo_table.getColumnModel().getColumn(0).setPreferredWidth(70);
		memo_table.getColumnModel().getColumn(1).setPreferredWidth(50);
		memo_table.getColumnModel().getColumn(2).setPreferredWidth(100);
		memo_table.getColumnModel().getColumn(3).setPreferredWidth(500);

		memo_table.getColumnModel().getColumn(0).setCellRenderer(cellCenter);
		memo_table.getColumnModel().getColumn(1).setCellRenderer(cellCenter);
		memo_table.getColumnModel().getColumn(2).setCellRenderer(cellCenter);
		memo_table.getColumnModel().getColumn(3).setCellRenderer(cellCenter);

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(780, 200));
	}

//댓글 목록
	public void showMemo() {

		con = clickedPage.boardMain.con;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("memoMain.showMemo()로 넘어온 board_id?" + board_id);

		// ======================================================

		String sql = "select memo_id,no,member_id,contents from memo where board_id=?";

		// ======================================================

		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			pstmt.setInt(1, board_id);
			rs = pstmt.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();

			String[] columnName = new String[columnCount];

			for (int i = 0; i < columnCount; i++) {
				columnName[i] = meta.getColumnName(i + 1);
			}

			rs.last();
			int total = rs.getRow();

			Object[][] data = new Object[total][columnCount];

			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();

				data[i][0] = rs.getInt("memo_id");
				data[i][1] = i;
				data[i][2] = rs.getString("member_id");
				data[i][3] = rs.getString("contents");
			}

			memoTableModel.columnName = columnName;
			memoTableModel.data = data;

			memo_table.setModel(memoTableModel);
			memo_table.updateUI();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		clickedPage.boardMain.connectManager.closeDB(pstmt, rs);
	}

	// 메모 등록
	public void insertMemo() {
		con = clickedPage.boardMain.con;
		if (con != null) {
			System.out.println("==============insertMemo 정상 실행 ===============");
		} else {
			System.out.println("==============insertMemo Not Connect ===============");
		}
		PreparedStatement pstmt = null;
		// =======================================
		String member_id = clickedPage.boardMain.id;// 로그인한 회원의 이름을 받아와서 대입할 것!!!!!!!!!.
		// =======================================

		String contents = t_memo.getText();
		System.out.println("insertMemo()로 넘어온 board_id는? " + board_id);
		// int board_id = clickedPage.select;
		System.out.println("insertMemo로 넘어온 board_id는?" + board_id);
		String sql = "insert into memo(memo_id,board_id,member_id,contents) values(seq_memo.nextval,'" + board_id
				+ "','" + member_id + "','" + contents + "')";

		try {
			pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();

			if (result == 0) {
				System.out.println("=============### 댓 글 작 성 실 패 ###======================");
			} else {
				System.out.println("=============### 댓 글 작 성 성 공 ###======================");
				JOptionPane.showMessageDialog(null, "댓글이 작성되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		clickedPage.boardMain.connectManager.closeDB(pstmt);
	}

	public void delMemo() {
		con = clickedPage.boardMain.con;
		PreparedStatement pstmt = null;
		int memo_id = select_memo;
		String sql = "delete from memo where memo_id ='" + memo_id + "'";
		System.out.println(memo_id);
		int result;
		try {
			pstmt = con.prepareStatement(sql);
			result = pstmt.executeUpdate();
			if (result == 0) {
				System.out.println("댓글 삭제 실패~~");
			} else {
				JOptionPane.showConfirmDialog(this, "정말 해당 댓글을 삭제하시겠습니까?");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
