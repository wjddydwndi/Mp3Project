package com.mp3.playlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jaudiotagger.tag.FieldKey;

import com.mp3.playlist.PlayerMain;

public class Lyrics extends JFrame{
	JTextArea area;
	JLabel label;
	JScrollPane scroll;
	PlayerMain playerMain;
	boolean flag = false;
	public Lyrics(PlayerMain playerMain) {
		this.playerMain = playerMain;
		this.setTitle("가사...");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		area.setBackground(Color.BLACK);
		area.setFont(new Font("굴림", Font.BOLD, 15));
		area.setForeground(Color.WHITE);
		this.add(scroll);
		this.setBounds(100, 100, 400, 400);
		setVisible(true);
	}
}
