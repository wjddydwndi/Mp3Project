package com.mp3.noticeboard;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.mp3.db.ConnectionManager;


public class NoticeBoard extends JPanel {
	// pages1.add()
	JPanel p_top, p_center, p_bottom;
	JLabel la_title;

	// p_top.add()
	JScrollPane scroll;

	// p_bottom.add()
	JPanel p_bottom_1, p_bottom_2, p_bottom_3;

	// p_bottom_2.add()
	Choice choice;
	JTextField t_search;
	JButton bt_search;
	JButton bt_reset;
	JButton bt_write;
	JButton bt_set;

	BoardMain boardMain;

	// db관련

	JTable table;
	TableModel tableModel;
	TableColumnModel columnModel;

	ClickedPage clickedPage;
	WriteBoard writeBoard;
	ConnectionManager connectManager;

	Connection con;

	int ch;	// 검색
	int columnCount;// 게시글 번호를 위한.
	String selected_id;//선택된 페이지를 쓴 member_id

	public NoticeBoard(BoardMain boardMain) {
		// create
		this.boardMain = boardMain;

		p_top = new JPanel();
		p_center = new JPanel();
		p_bottom = new JPanel();
		la_title = new JLabel("NOTICE BOARD");

		tableModel = new TableModel(this);
		table = new JTable();
		scroll = new JScrollPane(table);

		p_bottom_1 = new JPanel();// empty panel
		p_bottom_2 = new JPanel();// search/write
		p_bottom_3 = new JPanel();// empty panel
		choice = new Choice();
		choice.add("select");
		choice.add("member_id");
		choice.add("subject");

		t_search = new JTextField();
		bt_search = new JButton("검색");
		bt_reset = new JButton("새로고침");
		bt_write = new JButton("글쓰기");
		bt_set = new JButton("게시글 관리");

		//writeBoard = new WriteBoard(this);

		// pages1 Size & Color
		p_top.setPreferredSize(new Dimension(800, 70));
		p_center.setPreferredSize(new Dimension(800, 570));
		p_bottom.setPreferredSize(new Dimension(800, 150));

		// pages1.setBackground(Color.BLACK);
		p_top.setBackground(Color.BLACK);
		// p_center.setBackground(Color.GRAY);
		p_bottom.setBackground(Color.RED);

		// title setting
		la_title.setFont(new Font("돋움", Font.BOLD, 50));
		la_title.setForeground(Color.white);
		la_title.setForeground(Color.GREEN);
		// p_center size & color
		scroll.setPreferredSize(new Dimension(780, 570));

		// p_bottom size & color
		p_bottom_1.setPreferredSize(new Dimension(800, 50));
		p_bottom_2.setPreferredSize(new Dimension(800, 100));
		p_bottom_3.setPreferredSize(new Dimension(800, 50));

		p_bottom_1.setBackground(Color.BLACK);
		p_bottom_2.setBackground(Color.BLACK);
		p_bottom_3.setBackground(Color.BLACK);

		// p_bottom_2 size & color
		choice.setPreferredSize(new Dimension(80, 24));
		t_search.setPreferredSize(new Dimension(300, 24));
		bt_search.setPreferredSize(new Dimension(60, 24));
		bt_reset.setPreferredSize(new Dimension(100, 24));
		bt_write.setPreferredSize(new Dimension(80, 24));
		bt_set.setPreferredSize(new Dimension(150, 24));

		// wrapper
		setLayout(new BorderLayout());

		add(p_top, BorderLayout.NORTH);
		add(p_center);
		add(p_bottom, BorderLayout.SOUTH);

		// add(la_title)
		p_top.add(la_title);

		// p_top.add
		p_center.add(scroll);

		// p_bottom.add
		p_bottom.setLayout(new BorderLayout());
		p_bottom.add(p_bottom_1, BorderLayout.NORTH);
		p_bottom.add(p_bottom_2);
		p_bottom.add(p_bottom_3, BorderLayout.SOUTH);

		// p_bottom_2.add
		p_bottom_2.add(choice);
		p_bottom_2.add(t_search);
		p_bottom_2.add(bt_search);
		p_bottom_2.add(bt_reset);
		p_bottom_3.add(bt_write);
		p_bottom_3.add(bt_set);
//=============================================================
		// table Click
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int col = 0;
				// System.out.println(table.getValueAt(row, col));
				int select = (int) table.getValueAt(row, col);
				selected_id=(String)table.getValueAt(row, 3);
				 System.out.println("초기 select 값"+select);
				// System.out.println("보드메인"+boardMain.con);
				boardMain.showPage(2);
				ClickedPage cp = (ClickedPage) (boardMain.pages[2]);
				cp.selectBoard(select);
				cp.select = select;
				cp.updateNum();

				//cp.memoMain.output(select);
				showBoard();
				//clickedPage.memoMain.memo_table.updateUI();
			}
		});

		// bt_write Event
		bt_write.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("bt_write");
				boardMain.showPage(1);
			}
		});

		// 선택한 choice의 인덱스를 알기위한 이벤트.
		choice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ch = choice.getSelectedIndex();
				System.out.println("선택한 ch first=="+ch);
			}
		});

		// search Event
		bt_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("choice index" + ch);
				String all = t_search.getText();
				
				//String subject = t_search.getText();
				System.out.println("선택한 choice second=="+ch);
				if (ch == 0) {
					search(all,ch);
				} else if (ch == 1) {
					search(all,ch);
				} else if (ch == 2) {
					search(all,ch);
				}
			}
		});
		// 새로고침
		bt_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBoard();

			}
		});

		// 게시글 관리
		bt_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardMain.showPage(4);
				SetBoard sb = (SetBoard) (boardMain.pages[3]);
				sb.showSetList();
			}
		});

		showBoard();
		// ====table size=======================

		// table size
		table.setRowHeight(50);
		DefaultTableCellRenderer cellCenter = new DefaultTableCellRenderer();
		cellCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer cellRight = new DefaultTableCellRenderer();
		cellRight.setHorizontalAlignment(JLabel.RIGHT);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);

		table.getColumnModel().getColumn(0).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(1).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(2).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(3).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(4).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(5).setCellRenderer(cellCenter);
		// -------------------------------------------------------------

		table.updateUI();
		setVisible(false);
		setSize(800, 800);
	}

	// =============================================================
	public void showBoard() {
		con = boardMain.con;
		// System.out.println("noticeBoard con" + con);
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select board_id,no,subject,member_id,time,num from notice order by board_id asc";

		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			columnCount = meta.getColumnCount();

			String[] columnName = new String[columnCount];

			for (int i = 0; i < columnCount; i++) {
				columnName[i] = meta.getColumnName(i + 1);
			}
			tableModel.columnName = columnName;

			// -----------------------------------------------------------
			rs.last();
			int total = rs.getRow();

			Object[][] data = new Object[total][columnCount];

			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = rs.getInt("board_id");
				data[i][1] = i;
				data[i][2] = rs.getString("subject");
				data[i][3] = rs.getString("member_id");
				data[i][4] = rs.getString("time");
				data[i][5] = rs.getInt("num");

			}
			tableModel.data = data;
			table.setModel(tableModel);
			table.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boardMain.connectManager.closeDB(pstmt, rs);
	}

//=============================================================
	public void search(String choice,int ch) {
		con = boardMain.con;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		System.out.println("선택한 choice는?" + ch);
		if (ch == 0) {
		//	sql = "select board_id,subject,member_id,time,num from notice where member_id='" + choice + "'or subject='"+ choice + "'";
			sql = "select notice.board_id, notice.no, notice.subject, notice.member_id, notice.time, notice.num from notice, memberlist where notice.member_id = memberlist.id and memberlist.id = '"+choice+"'";
			sql+= " or notice.member_id = memberlist.id and notice.subject = '"+choice+"'";
		} else if (ch == 1) {
			sql = "select notice.board_id, notice.no, notice.subject, notice.member_id, notice.time, notice.num from notice, memberlist where notice.member_id = memberlist.id and memberlist.id = '"+choice+"'";
		} else if (ch == 2) {
			sql = "select  notice.board_id, notice.no, notice.subject, notice.member_id, notice.time, notice.num from notice, memberlist where notice.member_id = memberlist.id and notice.subject= '"+choice+"'";
		}

		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();

			String[] columnName = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnName[i] = meta.getColumnName(i + 1);
			}
//--------------------------------------
			rs.last();
			int total = rs.getRow();

			Object[][] data = new Object[total][columnCount];

			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = rs.getInt("board_id");
				data[i][1] = i+1;
				data[i][2] = rs.getString("subject");
				data[i][3] = rs.getString("member_id");
				data[i][4] = rs.getString("time");
				data[i][5] = rs.getInt("num");
			}
			tableModel.columnName = columnName;
			tableModel.data = data;
			table.updateUI();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boardMain.connectManager.closeDB(pstmt, rs);
	}

	public void test() {
		con = boardMain.con;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select board_id from notice;";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// ResultSetMetaData meta=rs.getMetaData();
			// int columnCount = meta.getColumnCount();

			int row = rs.getRow();
			ArrayList list = new ArrayList();
			for (int i = 0; i < row; i++) {
				list.add(rs.getInt("board_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
