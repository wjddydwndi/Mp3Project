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
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WriteBoard extends JPanel {
	BoardMain boardMain;
	NoticeBoard noticeBoard;

	JPanel p_title, p_title_n, p_title_s;
	JLabel la_top;
	JLabel la_title;
	JTextField t_title;

	JPanel p_content;
	// 폰트설정
	JPanel p_content_n;
	Choice ch_style;
	Choice ch_size;
	Choice ch_color;
	JTextArea area;

	JPanel p_bt;
	JButton bt_regist, bt_cancel;

	// db관련
	Connection con;

	// Font & Color
	String[] font = { "돋움", };
	String[] size = new String[20];
	Color[] color = { Color.RED, Color.BLUE, Color.BLACK };

	

	public WriteBoard(BoardMain boardMain) {
		this.boardMain = boardMain;
		p_title = new JPanel();
		p_title_n = new JPanel();
		la_top = new JLabel("WRITE BOARD");
		p_title_s = new JPanel();
		la_title = new JLabel("제목");
		t_title = new JTextField();
		p_content = new JPanel();
		p_content_n = new JPanel();
		ch_style = new Choice();
		ch_size = new Choice();
		ch_color = new Choice();
		// 폰트설정
		ch_style.add("돋움");
		ch_style.add("");
		ch_size.add("11");
		ch_size.add("");
		ch_color.add("black");
		ch_color.add("");

		area = new JTextArea();
		p_bt = new JPanel();
		bt_regist = new JButton("등록");
		bt_cancel = new JButton("취소");

		// panel setting

		p_title.setPreferredSize(new Dimension(800, 100));
		p_content.setPreferredSize(new Dimension(800, 400));
		p_bt.setPreferredSize(new Dimension(800, 80));

		p_title.setBackground(Color.BLACK);
		p_content.setBackground(Color.BLACK);
		p_bt.setBackground(Color.BLACK);

		// p_title setting
		// 폰트설정
		p_title_n.setPreferredSize(new Dimension(800, 60));
		p_title_n.setBackground(Color.BLACK);
		p_title_s.setPreferredSize(new Dimension(800, 38));
		p_title_s.setBackground(Color.BLACK);
		la_top.setFont(new Font("돋움", Font.BOLD, 30));
		la_top.setForeground(Color.GREEN);
		la_title.setFont(new Font("돋움", Font.BOLD, 20));
		la_title.setForeground(Color.GREEN);
		t_title.setPreferredSize(new Dimension(700, 28));

		// p_content setting
		// 폰트 설정
		p_content_n.setPreferredSize(new Dimension(800, 50));
		p_content_n.setBackground(Color.BLACK);
		ch_style.setPreferredSize(new Dimension(70, 20));
		ch_size.setPreferredSize(new Dimension(70, 20));
		ch_color.setPreferredSize(new Dimension(70, 20));

		area.setPreferredSize(new Dimension(800, 350));
		area.setFont(new Font("돋움", Font.BOLD, 20));

		// p_bt setting
		bt_regist.setPreferredSize(new Dimension(80, 30));
		bt_cancel.setPreferredSize(new Dimension(80, 30));

		bt_regist.setBackground(Color.GREEN);
		bt_cancel.setBackground(Color.GREEN);

		// add.panel
		setLayout(new BorderLayout());
		add(p_title, BorderLayout.NORTH);
		add(p_content);
		add(p_bt, BorderLayout.SOUTH);

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

		p_content.add(area);

		// add.p_bt
		p_bt.add(bt_regist);
		p_bt.add(bt_cancel);
		updateUI();

		// Button regist
		bt_regist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registBoard();
				NoticeBoard nb = (NoticeBoard) (boardMain.pages[0]);
				nb.showBoard();
			}
		});

		// Button cancel
		bt_cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WriteBoard.this.setVisible(false);
				boardMain.showPage(0);

			}
		});

		this.setPreferredSize(new Dimension(780, 800));
		setVisible(false);
	}

	public void registBoard() {
		con = boardMain.con;
		PreparedStatement pstmt = null;
		String member_id = boardMain.id;
		String subject = t_title.getText();
		String contents = area.getText();
		String sql = "insert into notice(board_id,member_id,subject,contents,time)";
		sql += " values(seq_notice.nextval,'" + member_id + "','" + subject + "','" + contents + "',sysdate)";
		
		try {
			pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if (result == 0) {
				System.out.println("등록실패");
			} else {
				System.out.println("등록성공");
				WriteBoard.this.setVisible(false);
				boardMain.showPage(0);
				JOptionPane.showMessageDialog(this, "게시글이 등록되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boardMain.connectManager.closeDB(pstmt);
	}

}
