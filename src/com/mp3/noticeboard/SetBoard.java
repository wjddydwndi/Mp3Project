package com.mp3.noticeboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class SetBoard extends JPanel {
	BoardMain boardMain;

	// wrapper.add()
	JPanel p_top, p_center, p_bottom;
	JLabel la_title;

	// p_top.add()
	JScrollPane scroll;

	// p_bottom.add()
	JPanel p_bottom_1, p_bottom_2, p_bottom_3;

	// p_bottom_2.add()

	JButton bt_del;
	JButton bt_back;
	JTable table;
	SetBoardModel setBoardModel;
	TableColumnModel columnModel;

	Connection con;

	int select;// 삭제할 리스트의 값

	public SetBoard(BoardMain boardMain) {
		this.boardMain = boardMain;

		p_top = new JPanel();
		p_center = new JPanel();
		p_bottom = new JPanel();
		la_title = new JLabel("SETTING BOARD");

		// 테이블 관련
		setBoardModel = new SetBoardModel(this);
		table = new JTable();
		scroll = new JScrollPane(table);

		p_bottom_1 = new JPanel();// empty panel
		p_bottom_2 = new JPanel();// search/write
		p_bottom_3 = new JPanel();// empty panel

		bt_del = new JButton("삭제");
		bt_back = new JButton("뒤로가기");

		// wrapper Size & Color
		p_top.setPreferredSize(new Dimension(780, 70));
		p_center.setPreferredSize(new Dimension(780, 550));
		p_bottom.setPreferredSize(new Dimension(780, 160));

		p_top.setBackground(Color.BLACK);
		p_center.setBackground(Color.BLACK);
		p_bottom.setBackground(Color.RED);

		// title setting
		la_title.setFont(new Font("돋움", Font.BOLD, 50));
		la_title.setForeground(Color.white);
		la_title.setForeground(Color.GREEN);
		// p_center size & color
		scroll.setPreferredSize(new Dimension(780, 550));

		// p_bottom size & color
		p_bottom_1.setPreferredSize(new Dimension(780, 25));
		p_bottom_2.setPreferredSize(new Dimension(780, 100));
		p_bottom_3.setPreferredSize(new Dimension(780, 25));

		p_bottom_1.setBackground(Color.BLACK);
		p_bottom_2.setBackground(Color.BLACK);
		p_bottom_3.setBackground(Color.BLACK);

		// p_bottom_2 size & color

		bt_del.setPreferredSize(new Dimension(150, 50));
		bt_back.setPreferredSize(new Dimension(150, 50));

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

		p_bottom_2.add(bt_del);
		p_bottom_2.add(bt_back);

		// 테이블 클릭
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int col = 0;
				System.out.println(table.getValueAt(row, col));
				select = (int) table.getValueAt(row, col);

			}
		});
		// 삭제
		bt_del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				deleteBoard();
				showSetList();
			}
		});

		// 뒤로가기
		bt_back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boardMain.showPage(5);
				NoticeBoard nb =(NoticeBoard)(boardMain.pages[0]);
				nb.showBoard();

			}
		});
		showSetList();

		table.setRowHeight(50);
		DefaultTableCellRenderer cellCenter = new DefaultTableCellRenderer();
		cellCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer cellRight = new DefaultTableCellRenderer();
		cellRight.setHorizontalAlignment(JLabel.RIGHT);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(350);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);

		table.getColumnModel().getColumn(0).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(1).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(2).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(3).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(4).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(5).setCellRenderer(cellCenter);

		table.updateUI();

		setVisible(false);
		setPreferredSize(new Dimension(800, 800));
	}

	public void showSetList() {
		con = boardMain.con;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String member_id = boardMain.id;
		// 멤버아이디 넘겨 받아서 처리할 것
		String sql = "select board_id,no,subject,member_id,time,num from notice";// where member_id='"+member_id+"'
																					// order by board_id asc";

		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();

			if (rs == null) {
				System.out.println("showSetList 실패");
			} else {
				System.out.println("showSetList 성공");

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
					data[i][0] = rs.getInt("board_id");
					data[i][1] = i;
					data[i][2] = rs.getString("subject");
					data[i][3] = rs.getString("member_id");
					data[i][4] = rs.getString("time");
					data[i][5] = rs.getInt("num");
				}
				setBoardModel.columnName = columnName;
				setBoardModel.data = data;
				table.setModel(setBoardModel);
				table.updateUI();

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBoard() {
		con = boardMain.con;
		PreparedStatement pstmt = null;
		System.out.println("deleteBoard()로 넘어온 select값은?" + select);
		String sql = "delete from notice where board_id = '" + select + "'";

		int result;
		try {
			pstmt = con.prepareStatement(sql);
			result = pstmt.executeUpdate();
			if (result == 0) {
				System.out.println("=================deleteBoard() 삭제 실패====================");
			} else {
				JOptionPane.showConfirmDialog(this, "정말 no" + select + "번 글을 삭제하시겠습니까?");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
