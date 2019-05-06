package com.mp3.main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.*;

import com.mp3.db.ConnectionManager;
import com.mp3.login.FindIdPassword;
import com.mp3.login.LoginMain;
import com.mp3.login.RegisterMain;
import com.mp3.playlist.PlayerMain;

public class Mp3Main extends JFrame{
	JPanel container;
	JPanel[] pages = new JPanel[3];
	JPanel play_panel;
	public Connection con;
	public ConnectionManager connectionManager;
	
	public String id;
	public int member_id; // 기본키
	public String name;
	public Mp3Main() {
		container = new JPanel();
		pages[0] = new LoginMain(this);
		pages[1] = new RegisterMain(this);
		pages[2] = new FindIdPassword(this);
		play_panel = new PlayerMain(this);
		connectionManager = new ConnectionManager();
		con = connectionManager.getConnection();
		container.add(pages[0]);
		container.add(pages[1]);
		container.add(pages[2]);
		container.add(play_panel);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				connectionManager.disconnect(con);
				System.exit(0);
			}
		});
		
		this.add(container);	
		this.setVisible(true);
		//this.setBounds(100,100,400,600);
		this.setSize(400, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		//this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.BLACK);
	}
	public static void main(String[] args) {
		new Mp3Main();
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
	public void showPlayList() {
		play_panel.setVisible(true);
		for(int i = 0; i < pages.length; i++) {
			pages[i].setVisible(false);
		}
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	public void setMemberId(int member_id) {
		this.member_id = member_id;
	}
	public int getMemberId() {
		return this.member_id;
	}
	public Connection getCon() {
		return con;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
