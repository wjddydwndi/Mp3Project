package com.mp3.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.mp3.main.Mp3Main;

public class FindIdPassword extends JPanel{
	Mp3Main mp3_Main;
	JPanel bt_panel;
	JButton bt_id, bt_pw, bt_box;
	JPanel container;
	JPanel[] pages = new JPanel[2];
	Font font = new Font("굴림", Font.BOLD, 15);
	public FindIdPassword(Mp3Main mp3_Main) {
		this.mp3_Main = mp3_Main;
		container = new JPanel();
		bt_panel = new JPanel();
		pages[0] = new FindId(this);
		pages[1] = new FindPassword(this);
		bt_id = new JButton("아이디 찾기");
		bt_pw = new JButton("비밀번호 찾기");
		bt_box = new JButton("");
		bt_panel.setLayout(new GridLayout(1,3));
	
		bt_id.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showpage(0);
			}
		});
		bt_pw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showpage(1);
			}
		});
	
		bt_id.setFont(font);
		bt_id.setBackground(Color.BLACK);
		bt_id.setForeground(Color.green);
		bt_id.setBorderPainted(false);
		bt_box.setBackground(Color.black);
		bt_box.setBorderPainted(false);
		bt_pw.setFont(font);
		bt_pw.setBackground(Color.BLACK);
		bt_pw.setForeground(Color.green);
		bt_pw.setBorderPainted(false);
		container.add(pages[0]);
		container.add(pages[1]);
		container.setBackground(Color.BLACK);
		bt_panel.setPreferredSize(new Dimension(400, 50));
		bt_panel.add(bt_id);
		bt_panel.add(bt_box);
		bt_panel.add(bt_pw);
		
		
		
		this.add(bt_panel, BorderLayout.NORTH);
		this.add(container);
		this.setPreferredSize(new Dimension(400,600));
		this.setBackground(Color.BLACK);
	}
	public void showpage(int page) {
		//	pages[page].setVisible(true);
		for(int i = 0; i < pages.length; i++) {
			if(i == page) {
				pages[i].setVisible(true);
			}else {
				pages[i].setVisible(false);
			}
		}
	}
}
