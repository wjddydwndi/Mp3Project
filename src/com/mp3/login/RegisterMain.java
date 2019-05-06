package com.mp3.login;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.swing.*;

import com.mp3.main.Mp3Main;
public class RegisterMain extends JPanel{
	JPanel p_img, p_register, p_label, p_input, p_button;
	Mp3Main mp3_main;
	LoginMain l_main;
	JLabel l_id, l_pw, l_pw2, l_name, l_birth;
	JTextField t_id, t_pw, t_pw2, t_name;
	Choice ch_year, ch_month, ch_date;
	JButton bt_register, bt_cancel;
	JButton bt_check;
	Canvas can;
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image image = kit.getImage("./res/register.png");
	Calendar calendar = Calendar.getInstance();
	Dimension ld = new Dimension(70, 40);
	Dimension td = new Dimension(150, 40);
	Dimension d2 = new Dimension(70, 40);
	Font font = new Font("굴림", Font.BOLD, 15);
	
	RegisterAction registerAction;
	public RegisterMain(Mp3Main mp3_main) {
		registerAction = new RegisterAction(this);
		this.mp3_main = mp3_main;
		p_img = new JPanel();
		p_register = new JPanel();
		p_label = new JPanel();
		p_input = new JPanel();
		p_button = new JPanel();
		l_id = new JLabel("ID :");
		l_pw = new JLabel("pw :");
		l_name = new JLabel("name :");
		l_birth = new JLabel("birth :");
		t_id = new JTextField(13);
		t_pw = new JTextField(20);
		t_name = new JTextField(20);
		ch_year = new Choice();
		ch_month = new Choice();
		ch_date = new Choice();
		bt_register = new JButton("Register");
		bt_cancel = new JButton("Cancel");
		bt_check = new JButton("Check");
		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 400, 200, can);			
			}
		};
		
		p_img.setPreferredSize(new Dimension(400,200));
		p_img.setBackground(Color.BLACK);
		p_register.setLayout(new BorderLayout());
		p_register.setPreferredSize(new Dimension(400, 400));
		p_register.setBackground(Color.BLACK);
		p_label.setPreferredSize(new Dimension(100, 250));
		p_label.setBackground(Color.BLACK);
		p_input.setPreferredSize(new Dimension(300, 250));
		p_button.setPreferredSize(new Dimension(400, 150));
		p_button.setBackground(Color.BLACK);
		p_input.setBackground(Color.BLACK);
		l_id.setFont(font);
		l_id.setPreferredSize(ld);
		l_id.setForeground(Color.GREEN);
		l_pw.setFont(font);
		l_pw.setPreferredSize(ld);
		l_pw.setForeground(Color.GREEN);
		l_name.setFont(font);
		l_name.setPreferredSize(ld);
		l_name.setForeground(Color.GREEN);
		l_birth.setFont(font);
		l_birth.setPreferredSize(ld);
		l_birth.setForeground(Color.GREEN);
		t_id.setFont(font);
		t_id.setPreferredSize(td);
		t_id.setBackground(Color.BLACK);
		t_id.setForeground(Color.GREEN);
		t_pw.setFont(font);
		t_pw.setPreferredSize(td);
		t_pw.setBackground(Color.BLACK);
		t_pw.setForeground(Color.GREEN);
		t_name.setFont(font);
		t_name.setPreferredSize(td);
		t_name.setBackground(Color.BLACK);
		t_name.setForeground(Color.GREEN);
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
		bt_register.setPreferredSize(new Dimension(170,50));
		bt_register.setBackground(Color.BLACK);
		bt_register.setForeground(Color.GREEN);
		bt_cancel.setPreferredSize(new Dimension(170,50));
		bt_cancel.setBackground(Color.BLACK);
		bt_cancel.setForeground(Color.GREEN);
		bt_check.setPreferredSize(new Dimension(80, 40));
		bt_check.setBackground(Color.BLACK);
		bt_check.setForeground(Color.GREEN);
		
		can.setPreferredSize(new Dimension(400, 200));

		bt_cancel.addActionListener(new RegisterCancel(this));
		bt_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerAction.register();
			}
		});
		bt_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerAction.idCheck();
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
		
		
		p_img.add(can);
		p_label.add(l_id);
		p_label.add(l_pw);
		p_label.add(l_name);
		p_label.add(l_birth);
		p_input.add(t_id);
		p_input.add(bt_check);
		p_input.add(t_pw);
		p_input.add(t_name);
		p_input.add(ch_year);
		p_input.add(ch_month);
		p_input.add(ch_date);
		p_button.add(bt_register);
		p_button.add(bt_cancel);
		
		p_register.add(p_label, BorderLayout.WEST);
		p_register.add(p_input);
		p_register.add(p_button, BorderLayout.SOUTH);
		this.add(p_img);
		this.add(p_register);		
		this.setPreferredSize(new Dimension(400,600));
		this.setBackground(Color.BLACK);
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










