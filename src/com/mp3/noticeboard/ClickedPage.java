package com.mp3.noticeboard;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClickedPage extends JPanel {
	BoardMain boardMain;
	NoticeBoard nb;

	JPanel p_title, p_title_n, p_title_s;
	JLabel la_top;
	JLabel la_title;
	JTextField t_title;

	// p_content
	JPanel p_content;
	JPanel p_content_n;
	Choice ch_style;
	Choice ch_size;
	Choice ch_color;
	JScrollPane scroll;
	JTextArea area;

	// p_memo
	JPanel p_memo;
	MemoMain memoMain;

	// p_bt
	JPanel p_bt;
	JButton bt_edit, bt_back;

	// 방문자 버튼
	JPanel p_visitor;
	JButton bt_return;

	// db관련
	Connection con;

	int select;// 선택한 테이블 게시글

	public ClickedPage(BoardMain boardMain) {
		this.boardMain = boardMain;
		nb = (NoticeBoard) (boardMain.pages[0]);
		memoMain= new MemoMain(this);
		
		con = boardMain.con;
		p_title = new JPanel();
		p_title_n = new JPanel();
		la_top = new JLabel();
		p_title_s = new JPanel();
		la_title = new JLabel("제목");
		t_title = new JTextField();
		p_content = new JPanel();
		p_content_n = new JPanel();
		ch_style = new Choice();
		ch_size = new Choice();
		ch_color = new Choice();
		// 임시
		ch_style.add("돋움");
		ch_size.add("11");
		ch_color.add("black");

		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_bt = new JPanel();
		bt_edit = new JButton("등록");
		bt_back = new JButton("취소");

		// 방문자
		p_visitor = new JPanel();
		bt_return = new JButton("뒤로가기");

		// p_memo
		p_memo = new JPanel();

		// panel setting
		p_title.setPreferredSize(new Dimension(780, 100));
		p_content.setPreferredSize(new Dimension(780, 400));
		p_bt.setPreferredSize(new Dimension(780, 50));
		p_visitor.setPreferredSize(new Dimension(780, 50));

		p_title.setBackground(Color.BLACK);
		p_content.setBackground(Color.BLACK);
		p_bt.setBackground(Color.BLACK);
		p_visitor.setBackground(Color.BLACK);

		// p_title setting
		p_title_n.setPreferredSize(new Dimension(780, 60));
		p_title_n.setBackground(Color.BLACK);
		p_title_s.setPreferredSize(new Dimension(780, 38));
		p_title_s.setBackground(Color.BLACK);
		la_top.setFont(new Font("돋움", Font.BOLD, 30));
		la_top.setForeground(Color.WHITE);
		la_title.setFont(new Font("돋움", Font.BOLD, 20));
		la_title.setForeground(Color.GREEN);
		t_title.setPreferredSize(new Dimension(700, 28));


		// p_content setting
		p_content_n.setPreferredSize(new Dimension(780, 50));
		p_content_n.setBackground(Color.BLACK);
		ch_style.setPreferredSize(new Dimension(70, 20));
		ch_size.setPreferredSize(new Dimension(70, 20));
		ch_color.setPreferredSize(new Dimension(70, 20));

		scroll.setPreferredSize(new Dimension(780, 400));
		area.setFont(new Font("돋움", Font.BOLD, 20));

		// p_memo
		p_memo.setPreferredSize(new Dimension(780, 200));
		p_memo.setBackground(Color.BLACK);

		// p_bt setting

		// add.panel
		setLayout(new BorderLayout());
		add(p_title, BorderLayout.NORTH);
		add(p_content);

		// add.p_title
		p_title.setLayout(new BorderLayout());
		p_title.add(p_title_n, BorderLayout.NORTH);
		p_title.add(p_title_s);
		p_title_n.add(la_top);
		p_title_s.add(la_title);
		p_title_s.add(t_title);

		// add.p_content
		p_content.setLayout(new BorderLayout());
		p_content.add(p_content_n, BorderLayout.NORTH);
		p_content_n.add(ch_style);
		p_content_n.add(ch_size);
		p_content_n.add(ch_color);

		p_content.add(scroll);

		// p_memo
		p_content.add(p_memo, BorderLayout.SOUTH);
		

		// add.p_bt

		updateUI();

		// Button edit
		bt_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		// Button cancel
		bt_back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClickedPage.this.setVisible(false);
				boardMain.showPage(0);
				area.setText("");

			}
		});

		bt_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClickedPage.this.setVisible(false);
				boardMain.showPage(0);
				area.setText("");
			}
		});
		userInterface();
		/*
		  =================================================
		  
		  
		  member_id 받으면 적용해 볼 것!! if (nb.selected_id == member_id) { //
		  userInterface(); } else { visitor(); }
		  
		  
		  =============================================
	*/
		
		area.setText("");
		this.setPreferredSize(new Dimension(780, 800));
		setVisible(false);
	}

	public void selectBoard(int select) {
		 System.out.println("selectPage 왔는가??!!!!" + boardMain);
		con = boardMain.con;
		//===================================
		
		memoMain.board_id = select;
		memoMain.showMemo();
		p_memo.add(memoMain);
		memoMain.memo_table.updateUI();
		
		//=======================================
		
		nb.showBoard();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("------------------선택된 board_id는?" + select);

		String sql = "select subject,contents from notice where board_id=" + select;

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				la_top.setText(rs.getString("subject"));
				t_title.setText(rs.getString("subject"));
				area.append(rs.getString("contents"));
			}
			memoMain.showMemo();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		boardMain.connectManager.closeDB(pstmt, rs);
	}

	public void userInterface() {
		bt_edit.setPreferredSize(new Dimension(80, 30));
		bt_back.setPreferredSize(new Dimension(80, 30));

		bt_edit.setBackground(Color.GREEN);
		bt_back.setBackground(Color.GREEN);

		p_bt.add(bt_edit);
		p_bt.add(bt_back);
		add(p_bt, BorderLayout.SOUTH);

	}

	public void visitor() {
		bt_return.setPreferredSize(new Dimension(100, 30));
		bt_return.setBackground(Color.GREEN);
		p_visitor.add(bt_return);
		add(p_visitor, BorderLayout.SOUTH);

	}

	public void edit() {
		con = boardMain.con;
		PreparedStatement pstmt = null;
		String subject = t_title.getText();
		String contents = area.getText();
		StringBuffer sb = new StringBuffer();
		sb.append("update notice set contents='" + contents + "',subject='" + subject + "',time=sysdate where board_id= " + select + " and member_id = '" + boardMain.id + "'");
		// System.out.println("edit으로 넘어온 select값" + select);
		// System.out.println(sb.toString());
		try {
			pstmt = con.prepareStatement(sb.toString());
			int result = pstmt.executeUpdate();
			if (result != 0) {
				System.out.println("-----------------------------------수정완료---------------------------------");
				JOptionPane.showMessageDialog(nb, "변경된 내용을 등록하였습니다.");
				nb.showBoard();
				boardMain.showPage(3);
				area.setText("");
			} else {
				System.out.println("------------------------------------수정실패--------------------------------");
				JOptionPane.showMessageDialog(nb, "해당 게시글의 게시자가 아니면 수정이 불가능합니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boardMain.connectManager.closeDB(pstmt);
	}

	// 조회수
	public void updateNum() {
		con = boardMain.con;
		PreparedStatement pstmt = null;

		String sql = "update notice set num=NVL(num,0)+1 where board_id=" + select;

		try {
			pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if (result != 0) {
				System.out.println("------------------조회수 1증가");
			} else {
				System.out.println("조회수 증가 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boardMain.connectManager.closeDB(pstmt);
	}

}
