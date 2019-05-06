package com.mp3.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LoginAction implements ActionListener{
	LoginMain l_main;
	public LoginAction(LoginMain l_main) {
		this.l_main = l_main;
	}
	public void showlist() {
		Connection con = l_main.mp3_main.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String input_id = l_main.t_id.getText();
		String input_pw = new String(l_main.t_pw.getPassword());
		StringBuffer sb = new StringBuffer();
		sb.append("select * from memberlist where id = '" + input_id + "' and password = '" + input_pw + "'");
		try {
			pstmt = con.prepareStatement(sb.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				l_main.mp3_main.setId(rs.getString("id"));
				l_main.mp3_main.setMemberId(rs.getInt("member_id"));
				l_main.mp3_main.showPlayList();
			}else {
				JOptionPane.showMessageDialog(l_main, "로그인 실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e) {
		showlist();
	}
}
