package com.mp3.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class RegisterCancel implements ActionListener{
	RegisterMain r_main;
	public RegisterCancel(RegisterMain r_main) {
		this.r_main = r_main;
	}
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(r_main.mp3_main, "로그인 화면으로 돌아갑니다.");
		r_main.mp3_main.showpage(0);
	}

}
