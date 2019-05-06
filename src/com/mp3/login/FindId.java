package com.mp3.login;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindId extends JPanel{
	FindIdPassword fip;
	JPanel p_center, p_south;
	JLabel l_name, l_birth;
	JTextField t_name, t_birth;
	JButton bt_find, bt_cancel;
	Choice ch_year, ch_month, ch_date;
	Font font = new Font("굴림", Font.BOLD, 20);
	Calendar calendar = Calendar.getInstance();
	Dimension d = new Dimension(150, 150);
	Dimension ld = new Dimension(100, 30);
	Dimension d2 = new Dimension(70, 40);
	public FindId(FindIdPassword fip) {
		this.fip = fip;
		this.setLayout(new BorderLayout());
		
		p_center = new JPanel();
		p_south = new JPanel();
		l_name = new JLabel("이름 : ");
		l_birth = new JLabel("생년월일 : ");
		t_name = new JTextField(10);
		t_birth = new JTextField(10);
		bt_find = new JButton("찾기");
		bt_cancel = new JButton("취소");
		ch_year = new Choice();
		ch_month = new Choice();
		ch_date = new Choice();
		
		
		l_name.setPreferredSize(d);
		l_name.setFont(font);
		l_name.setBackground(Color.BLACK);
		l_name.setForeground(Color.GREEN);
		t_name.setPreferredSize(ld);
		t_name.setFont(font);
		t_name.setBackground(Color.BLACK);
		t_name.setForeground(Color.GREEN);
		l_birth.setPreferredSize(d);
		l_birth.setFont(font);
		l_birth.setBackground(Color.BLACK);
		l_birth.setForeground(Color.GREEN);
		t_birth.setPreferredSize(ld);
		t_birth.setFont(font);
		t_birth.setBackground(Color.BLACK);
		t_birth.setForeground(Color.GREEN);
		ch_year.setFont(font);
		ch_year.setBackground(Color.BLACK);
		ch_year.setForeground(Color.GREEN);
		ch_year.setPreferredSize(d2);
		ch_month.setFont(font);
		ch_month.setBackground(Color.BLACK);
		ch_month.setForeground(Color.GREEN);
		ch_month.setPreferredSize(d2);
		ch_date.setFont(font);
		ch_date.setBackground(Color.BLACK);
		ch_date.setForeground(Color.GREEN);
		ch_date.setPreferredSize(d2);
		p_center.setBackground(Color.black);
		p_center.add(l_name);
		p_center.add(t_name);
		p_center.add(l_birth);
		//p_center.add(t_birth);
		p_center.add(ch_year);
		p_center.add(ch_month);
		p_center.add(ch_date);
		////////////////////////////////////////
		bt_find.setBackground(Color.BLACK);
		bt_find.setForeground(Color.GREEN);
		bt_find.setBorderPainted(false);
		bt_find.setFont(font);
		bt_cancel.setBackground(Color.BLACK);
		bt_cancel.setForeground(Color.GREEN);
		bt_cancel.setBorderPainted(false);
		bt_cancel.setFont(font);
		p_south.setLayout(new GridLayout(1,2));
		p_south.setPreferredSize(new Dimension(400, 50));
		p_south.add(bt_find);
		p_south.add(bt_cancel);
		
		bt_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findId();
			}
		});
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(fip.mp3_Main, "로그인 화면으로 돌아가시겠습니까?");
				if(result == JOptionPane.OK_OPTION) {
					fip.mp3_Main.showpage(0);
				}
			}
		});
		setYear();
		ch_year.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ch_month.removeAll();
				setMonth();
			}
		});
		ch_month.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ch_date.removeAll();
				setDate();
			}
		});
		
		this.add(p_center);
		this.add(p_south,BorderLayout.SOUTH);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(400, 500));
		this.setBackground(Color.RED);
	}
	
	public void findId() {
		Connection con = fip.mp3_Main.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String year = ch_year.getSelectedItem();
		String month = ch_month.getSelectedItem();
		String date = ch_date.getSelectedItem();
		String birth = year + month + date;
		String name =  t_name.getText();
		String sql = "select id from memberlist where name = '" + name + "' and birth = '" + birth +"'";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				JOptionPane.showMessageDialog(fip.mp3_Main, "찾으신 id는 " + rs.getString("id") + " 입니다.");
			}else {
				JOptionPane.showMessageDialog(fip.mp3_Main, "해당 조건에 충족되는 아이디는 없습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void setYear() {
		for(int i = 1950 ; i <= 2018 ; i++) {
			String year = Integer.toString(i);
			ch_year.add(year);
		}
	}
	public void setMonth() {
		int year = Integer.parseInt(ch_year.getSelectedItem());
		calendar.set(Calendar.YEAR,year);		
		for(int i = 1; i <= 12; i++) {
			String month = Integer.toString(i);
			ch_month.add(month);
		}
	}
	public void setDate() {
		int month = Integer.parseInt(ch_month.getSelectedItem()) - 1;
		calendar.set(Calendar.MONTH,month);
		int enddate =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i = 1; i <= enddate; i++) {
			String date = Integer.toString(i);
			ch_date.add(date);
		}
	}
	
	
}
