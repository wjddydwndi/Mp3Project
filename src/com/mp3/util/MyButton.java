package com.mp3.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class MyButton extends JButton {
	boolean flag1 = false;
	boolean flag2 = false;
	boolean flag3 = false;
	Color color;
	Color bg;
	int width;
	int height;


	public MyButton(boolean flag1, boolean flag2, boolean flag3, Color bg, Color color, int width, int height) {
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.flag3 = flag3;
		this.color = color;
		this.width = width;
		this.height = height;

		this.setBorderPainted(flag1);
		this.setContentAreaFilled(flag2);
		this.setFocusPainted(flag3);
		this.setBackground(bg);
		this.setForeground(color);
		this.setPreferredSize(new Dimension(width, height));
		this.setFont(new Font("굴림", Font.BOLD, 15));
	}
}