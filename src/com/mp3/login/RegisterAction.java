package com.mp3.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mp3.db.ConnectionManager;

public class RegisterAction{
	RegisterMain r_main;
	String id;
	String pw;
	String name;
	String birth;
	String year;
	String month;
	String date;
	Boolean idChecked = false;
	public RegisterAction (RegisterMain r_main) {
		this.r_main = r_main;
	}
	public void register() {
		Connection con = r_main.mp3_main.getCon();
		PreparedStatement pstmt = null;
		id = r_main.t_id.getText();
		pw = r_main.t_pw.getText();
		name = r_main.t_name.getText();
		year = r_main.ch_year.getSelectedItem();
		month = r_main.ch_month.getSelectedItem();
		date = r_main.ch_date.getSelectedItem();
		birth = year + month + date;
		
		if(id.length() == 0) {
			JOptionPane.showMessageDialog(r_main, "id를 입력하세요");
			r_main.t_id.requestFocus();
			return;
		}
		if(idChecked == false) {
			JOptionPane.showMessageDialog(r_main, "id 중복체크 해주세요");
			return;
		}
		if(pw.length() == 0) {
			JOptionPane.showMessageDialog(r_main, "password룰 입력하세요");
			r_main.t_pw.requestFocus();
			return;
		}
		if(name.length() == 0) {
			JOptionPane.showMessageDialog(r_main, "이름을 입력하세요");
			r_main.t_name.requestFocus();
			return;
		}
		if(year.length() == 0 || month.length() ==0 || date.length() == 0) {
			JOptionPane.showMessageDialog(r_main, "생년월일을 입력하세요");
			return;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("Insert into memberlist (member_id, id, password, name, birth)");
		sb.append(" values(member_seq.nextval,'" + id + "','" + pw + "','" + name + "','" + birth + "')");
		try {
			pstmt = con.prepareStatement(sb.toString());
			int result = pstmt.executeUpdate();
			if(result > 0) {
				JOptionPane.showMessageDialog(r_main.mp3_main, "회원 가입 성공");
				r_main.t_id.setText("");
				r_main.t_pw.setText("");
				r_main.t_name.setText("");
				r_main.mp3_main.showpage(0);
			}else {
				JOptionPane.showMessageDialog(r_main.mp3_main, "회원 가입 실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	public void idCheck() {
		Connection con = r_main.mp3_main.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		id = r_main.t_id.getText();
		String sql = "select * from memberlist where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				JOptionPane.showMessageDialog(r_main, "중복되는 아이디 입니다. 다시 입력해 주세요");
				r_main.t_id.requestFocus();
			}else {
				JOptionPane.showMessageDialog(r_main, "사용가능한 아이디 입니다.");
				idChecked = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
