package com.mp3.playlist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mp3.db.ConnectionManager;
import com.mp3.main.Mp3Main;
import com.mp3.util.listModel;

public class Edit extends JFrame {
	Mp3Main mp3Main;
	PlayerMain playerMain;
	// listModel listModel;

	JPanel p_north; // 테이블 영역.

	JPanel p_center; // p_info, p_search를 예속시킨다.

	JPanel p_title; // 제목 라벨 & 텍스트상자 영역.
	JPanel p_singer; // 가수 라벨 & 텍스트상자 영역.
	JPanel p_info; // p_title, p_singer를 예속시킬 영역. p_search와 동격.

	JPanel p_search; // 검색 단추 영역.

	JPanel p_south; // 단추 영역.

	JTable table;

	JScrollPane scroll;
	JLabel la_singer, la_title;
	JTextField txt_singer, txt_title;
	JButton bt_modify, bt_del, bt_add, bt_close, bt_search;
	JFileChooser fc;

	ConnectionManager connectionManager;

	boolean flag = false;
	int row;
	String keyword;

	// public Edit() {
	public Edit(Mp3Main mp3Main, PlayerMain playerMain) {
		super("재생목록");
		this.mp3Main = mp3Main;
		this.playerMain = playerMain;

		fc = new JFileChooser();
		fc.setCurrentDirectory(new File("C:/Users/wkdtj/java_developer/javaSE/MP3/res/music"));

		p_north = new JPanel();
		p_center = new JPanel();
		p_title = new JPanel();
		p_singer = new JPanel();
		p_info = new JPanel();
		p_search = new JPanel();
		p_south = new JPanel();
		table = new JTable();
		scroll = new JScrollPane(table);
		la_title = new JLabel("제목");
		la_singer = new JLabel("가수");
		txt_title = new JTextField(10);
		txt_singer = new JTextField(10);
		bt_modify = new JButton("수정");
		bt_del = new JButton("삭제");
		bt_add = new JButton("추가");
		bt_close = new JButton("닫기");

		bt_search = new JButton("찿기");
		// =====================[디자인 영역]========================

		// ======[테이블]======
		Font font_tblHeader = new Font("Verdana", Font.BOLD, 15);
		table.getTableHeader().setBackground(Color.GRAY);
		table.getTableHeader().setForeground(Color.GREEN);
		table.getTableHeader().setFont(font_tblHeader);
		// ======[라벨]======
		Font font_la = new Font("HY헤드라인M", Font.BOLD, 20);

		la_title.setForeground(Color.GREEN);
		la_title.setFont(font_la);

		la_singer.setForeground(Color.GREEN);
		la_singer.setFont(font_la);

		// =====[텍스트 상자]=====
		txt_title.setLayout(new GridLayout());
		txt_singer.setLayout(new GridLayout());

		// =======[단추]=======
		Dimension d_bt = new Dimension(80, 30);
		Font font_bt = new Font("HY헤드라인M", Font.BOLD, 17);

		bt_modify.setPreferredSize(d_bt);
		bt_modify.setBackground(Color.BLACK);
		bt_modify.setForeground(Color.GREEN);
		bt_modify.setBorderPainted(false);
		bt_modify.setFont(font_bt);

		bt_del.setPreferredSize(d_bt);
		bt_del.setBackground(Color.BLACK);
		bt_del.setForeground(Color.GREEN);
		bt_del.setBorderPainted(false);
		bt_del.setFont(font_bt);

		bt_add.setPreferredSize(d_bt);
		bt_add.setBackground(Color.BLACK);
		bt_add.setForeground(Color.GREEN);
		bt_add.setBorderPainted(false);
		bt_add.setFont(font_bt);

		bt_close.setPreferredSize(d_bt);
		bt_close.setBackground(Color.BLACK);
		bt_close.setForeground(Color.GREEN);
		bt_close.setBorderPainted(false);
		bt_close.setFont(font_bt);

		bt_search.setPreferredSize(new Dimension(80, 50));
		bt_search.setBackground(Color.BLACK);
		bt_search.setForeground(Color.GREEN);
		bt_search.setBorderPainted(false);
		bt_search.setFont(new Font("HY헤드라인M", Font.BOLD, 20));

		// =======[판넬]=======
		p_north.setBackground(Color.BLACK);

		p_title.setBackground(Color.BLACK);
		p_singer.setBackground(Color.BLACK);
		p_info.setLayout(new GridLayout(2, 1));
		p_search.setBackground(Color.BLACK);
		p_center.setBackground(Color.BLACK);

		p_south.setBackground(Color.BLACK);

		// ============================================================

		p_north.add(scroll);

		p_title.add(la_title);
		p_title.add(txt_title);
		p_singer.add(la_singer);
		p_singer.add(txt_singer);
		p_info.add(p_title);
		p_info.add(p_singer);
		p_search.add(bt_search);

		p_center.add(p_info, BorderLayout.WEST);
		p_center.add(p_search, BorderLayout.EAST);

		p_south.add(bt_modify);
		p_south.add(bt_del);
		p_south.add(bt_add);
		p_south.add(bt_close);

		this.add(p_north, BorderLayout.NORTH);
		this.add(p_center);
		this.add(p_south, BorderLayout.SOUTH);
		this.setVisible(true);

		this.setSize(400, 600);
		this.setLocation(mp3Main.getX() + mp3Main.getWidth(), mp3Main.getY());

		// =======[테이블 영역.]=========
		table.setPreferredScrollableViewportSize(new Dimension(300, 400));

		table.setPreferredScrollableViewportSize(new Dimension(300, 400));
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				row = table.getSelectedRow();
				playMethod();
				if (e.getClickCount() == 2) {
					row = table.getSelectedRow();
					playMethod();
					playerMain.playerFunction.pause_resume();
				}
			}
		});

		// =========[BUTTON_ACTION]=============
		bt_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txt_singer.getText().equals("") || txt_title.getText().equals("")) {
					JOptionPane.showMessageDialog(table, "입력된 값이 없습니다.", "오류", 0);
				} else {
					int result = JOptionPane.showConfirmDialog(table, "수정하시겠습니까?");
					if (result == JOptionPane.OK_OPTION) {
						// JOptionPane.showMessageDialog(table, "재생목록을 갱신합니다.", "알림", 1);
						playerMain.playerManage.modify(); // 1. 수정 쿼리문.
						playerMain.playerManage.selectList(playerMain.sb); // 2. 테이블 갱신.
					}
				}
			}
		});
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txt_singer.getText().equals("") || txt_title.getText().equals("")) {
					JOptionPane.showMessageDialog(table, "입력된 값이 없습니다.", "오류", 0);
				} else {
					int result = JOptionPane.showConfirmDialog(table, "삭제하시겠습니까?", "곡 삭제", 2);
					if (result == JOptionPane.OK_OPTION) {
						// JOptionPane.showMessageDialog(table, "재생목록을 갱신합니다.", "알림", 1);
						playerMain.playerManage.delete(); // 1. 삭제 쿼리문.
						playerMain.playerManage.selectList(playerMain.sb); // 2. 테이블 갱신.
					}
				}
			}
		});
		bt_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerMain.path = filechoose();
				if (playerMain.path.length() != 0) {
					playerMain.v2Tag = playerMain.playerManage.getTag();
					playerMain.playerManage.checkDB(playerMain.path);
					playerMain.playerManage.selectList(playerMain.sb);
				}
			}
		});
		bt_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		bt_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				keyword = getKeyword();
				if (keyword == null) { // '취소'를 눌러 검색창을 빠져나왔을 경우.
					System.out.println("**검색 취소.**");
					return;
				} else {
					StringBuffer sb_search = new StringBuffer();
					sb_search.append("select songname,singer from memberlist m,playlist p,songdb s");
					sb_search.append(" where s.singer like '%" + keyword + "%'");
					sb_search.append(" and m.member_id=p.member_id");
					sb_search.append(" and p.songdb_id=s.songdb_id");
					sb_search.append(" and m.member_id=" + playerMain.login_member_id);
					playerMain.playerManage.selectList(sb_search);
					System.out.println(table.getRowCount());
					System.out.println(playerMain.playerManage.maxrow);
				}
			}
		});
	}

	public String getKeyword() {
		String keyword = JOptionPane.showInputDialog(this, "검색어를 입력하십시오.", "가수 찾기", 3);
		return keyword;
	}

	public String filechoose() {
		int result = fc.showOpenDialog(null);
		String choosepath = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			choosepath = fc.getSelectedFile().getAbsolutePath();
		} else if (result == JFileChooser.CANCEL_OPTION) {
			choosepath = "";
		}
		return choosepath;
	}

	public void playMethod() {
		playerMain.playerManage.getInfo();
		// 재생관련 /////////////////////////////////////////
		if (playerMain.playerFunction.player != null) {
			playerMain.playerFunction.player.close();
		}
		playerMain.playerFunction.total = 0;
		playerMain.playerFunction.paused = true;
		///////////////////////////////////////////////////
		playerMain.path = (String) playerMain.playerManage.getDB(1); // list에서 클릭한 노래의 경로 추출
		playerMain.songdb_id = (int) playerMain.playerManage.getDB(2); // list에서 클릭한 노래의 primary key 추출
		playerMain.bt_play.setIcon(playerMain.play_icon); // 아이콘 변경
		playerMain.can.repaint(); // 앨범 이미지 변경
		playerMain.reloadInfo(); // 앨범 이미지 변경
		playerMain.bar.flag = false; // progressbar
		playerMain.bar.n = 0;
		playerMain.bar.start_min = 0;
		playerMain.bar.start_sec = 0;
		playerMain.bar.sec = 0;
	}
}
