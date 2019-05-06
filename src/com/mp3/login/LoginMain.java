package com.mp3.login;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.mp3.main.Mp3Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginMain extends JPanel{
	Mp3Main mp3_main;
	RegisterMain r_main;
	JPanel p_img, p_info;
	JLabel l_id, l_pw;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_login, bt_register;
	JLabel blank_h, blank_v;
	JButton bt_findIdPw;
	Canvas can;

	Toolkit kit = Toolkit.getDefaultToolkit();
	Image image = kit.getImage("./res/melon.png");
	Font font = new Font("굴림", Font.BOLD, 20);
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	Dimension l_d = new Dimension (150,60);
	Dimension bt_d = new Dimension (150,70);
	Dimension l_d2 = new Dimension (250,30);
	
	
	public LoginMain(Mp3Main mp3_main) {
		this.mp3_main = mp3_main;
		r_main = new RegisterMain(mp3_main);
		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 400, 200, can);			
			}
		};
		p_img = new JPanel();
		p_info = new JPanel();
		l_id = new JLabel("ID");
		l_pw = new JLabel("Password");
		t_id = new JTextField(10);
		t_pw = new JPasswordField(10);
		bt_login = new JButton("Login");
		bt_register = new JButton("Register");
		bt_findIdPw = new JButton("아이디/비밀번호 찾기");
		blank_h = new JLabel();
		blank_v = new JLabel();
		
		can.setPreferredSize(new Dimension(400,300));
		p_img.setPreferredSize(new Dimension(400, 250));
		p_info.setPreferredSize(new Dimension(400, 350));
		p_img.setBackground(Color.black);
		p_info.setBackground(Color.BLACK);
		l_id.setForeground(Color.GREEN);
		l_id.setFont(font);
		l_id.setPreferredSize(l_d);
		l_pw.setForeground(Color.GREEN);
		l_pw.setFont(font);
		l_pw.setPreferredSize(l_d);
		t_id.setFont(font);
		t_id.setBackground(Color.BLACK);
		t_id.setForeground(Color.GREEN);
		t_pw.setFont(font);
		t_pw.setBackground(Color.BLACK);
		t_pw.setForeground(Color.GREEN);
		bt_login.setPreferredSize(bt_d);
		bt_login.setFont(font);
		bt_login.setBackground(Color.BLACK);
		bt_login.setForeground(Color.green);
		bt_login.setBorderPainted(false);
		bt_register.setPreferredSize(bt_d);
		bt_register.setFont(font);
		bt_register.setBackground(Color.BLACK);
		bt_register.setForeground(Color.green);
		bt_register.setBorderPainted(false);
		bt_findIdPw.setFont(font);
		bt_findIdPw.setPreferredSize(l_d2);
		bt_findIdPw.setForeground(Color.GREEN);
		bt_findIdPw.setBackground(Color.BLACK);
		bt_findIdPw.setBorderPainted(false);
		
		bt_login.addActionListener(new LoginAction(this));
		
		bt_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp3_main.showpage(1);
			}
		});
		
		bt_findIdPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mp3_main.showpage(2);
			}
		});
		p_img.add(can);
		p_info.add(l_id);
		p_info.add(l_id);
		p_info.add(l_id);
		p_info.add(t_id);
		p_info.add(l_pw);
		p_info.add(t_pw);
		p_info.add(bt_login);
		p_info.add(bt_register);
		p_info.add(bt_findIdPw);
		
		this.add(p_img);
		this.add(p_info);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(400,600));
		this.setBackground(Color.BLACK);
		can.repaint();
	}
}
