package com.mp3.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JProgressBar;

import com.mp3.playlist.PlayerMain;
import com.mp3.playlist.*;
public class MyProgressBar extends JProgressBar{
	public Thread thread;
	public double n = 0;
	public int sec = 0;
	public double velX = 0;
	public int length = 0;
	public int min;
	public int second;
	public int start_min = 0;
	public int start_sec = 0;
	PlayerMain playerMain;
	Color COLOR;
	public Boolean flag = false;

	public MyProgressBar(Color COLOR, PlayerMain playerMain) {
		this.playerMain = playerMain;
		this.COLOR = COLOR;
		
		thread = new Thread() {
			public void run() {
				try {
					while (true) {
						if (flag) {
							n += velX;
							setValue((int) n);
							if (sec >= 60) {
								start_min = sec / 60;
							}
							if(start_sec == 60) {
								start_sec = 0;
							}
							if (start_sec < 10) {
								if (second < 10) {
									setString(start_min + ":0" + start_sec + " / " + min + ":0" + second);
								} else {
									setString(start_min + ":0" + start_sec + " / " + min + ":" + second);
								}

							} else {
								if (second < 10) {
									setString(start_min + ":" + start_sec + " / " + min + ":0" + second);
								} else {
									setString(start_min + ":" + start_sec + " / " + min + ":" + second);
								}
							}
							sec++;
							start_sec++;
							Thread.sleep(1000);
						} else {
							System.out.print("");
							Thread.sleep(1000);
							continue;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		setPreferredSize(new Dimension(350, 50));
		setStringPainted(true);
		setBackground(Color.BLACK);
		setForeground(COLOR);
		setFont(new Font("Arial Black", Font.BOLD, 35));
	}
}
