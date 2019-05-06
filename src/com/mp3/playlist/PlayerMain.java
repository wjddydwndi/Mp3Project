package com.mp3.playlist;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.images.Artwork;

import com.mp3.db.ConnectionManager;
import com.mp3.login.LoginMain;
import com.mp3.main.Mp3Main;
import com.mp3.noticeboard.BoardMain;
import com.mp3.util.MyButton;
import com.mp3.util.MyProgressBar;

import com.mp3.util.listModel;
import javazoom.jl.player.Player;

public class PlayerMain extends JPanel{
	String path = "./res/music/056. 저스디스 (JUSTHIS), Kid Milli, NOEL, 영비 (Young B) - IndiGO.mp3";
	Mp3Main mp3_main;
	PlayerFunction playerFunction;
	PlayerManage playerManage;
	PlayerMain playerMain = this;
	Edit edit;
	Lyrics lyrics;
	BoardMain boardMain;

	StringBuffer sb;
	int songdb_id;
	public String login_id;
	public int login_member_id;
	String login_name;
	AbstractID3v2Tag v2Tag;
	ImageIcon next_icon, prev_icon, play_icon, pause_icon;
	ImageIcon albumicon;
	Canvas can;
	Image image;
	JPanel p_canvas, p_info, p_bar, p_etc, p_button; 
	JLabel l_songname, l_singer;
	MyProgressBar bar;
	MyButton bt_prev, bt_play, bt_next, bt_random, bt_lyrics, bt_playlist, bt_board;
	public PlayerMain(Mp3Main mp3_main) {
		this.mp3_main = mp3_main;
		playerFunction = new PlayerFunction(this);
		playerManage = new PlayerManage(this);
		this.setLayout(new FlowLayout());
		next_icon = new ImageIcon("./res/player_fwd.png");
		play_icon = new ImageIcon("./res/player_play.png");
		prev_icon = new ImageIcon("./res/player_rew.png");
		pause_icon = new ImageIcon("./res/player_pause.png");

		p_canvas = new JPanel();
		p_info = new JPanel();
		p_bar = new JPanel();
		p_button = new JPanel();
		p_etc = new JPanel();
		l_songname = new JLabel("선택된 파일이 없습니다.");
		l_singer = new JLabel();
		bar = new MyProgressBar(Color.GREEN,this);
		bt_prev = new MyButton(false, true, false, Color.BLACK, Color.GREEN, 60, 50);
		bt_play = new MyButton(false, true, false, Color.BLACK, Color.GREEN, 60, 50);
		bt_next = new MyButton(false, true, false, Color.BLACK, Color.GREEN, 60, 50);
		bt_random = new MyButton(false, true, false, Color.BLACK, Color.GREEN, 150, 50);
		bt_lyrics = new MyButton(false, true, false, Color.BLACK, Color.GREEN, 100, 50);
		bt_playlist = new MyButton(false, true, false, Color.BLACK, Color.GREEN, 100, 50);
		bt_board = new MyButton(false, true, false, Color.BLACK, Color.GREEN, 100, 50);
		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 400, 250, can);		
			}
		};
	
		can.setPreferredSize(new Dimension(400, 250));
		
		p_canvas.setPreferredSize(new Dimension(400, 250));
		p_canvas.setBackground(Color.BLACK);
		p_canvas.add(can);
		
		p_info.setPreferredSize(new Dimension(400, 70));
		p_info.setBackground(Color.BLACK);
		l_songname.setPreferredSize(new Dimension(350, 25));
		l_songname.setVerticalAlignment(SwingConstants.CENTER);
		l_songname.setHorizontalAlignment(SwingConstants.CENTER);
		l_songname.setForeground(Color.GREEN);
		l_songname.setFont(new Font("굴림", Font.BOLD, 15));
		l_singer.setPreferredSize(new Dimension(350, 25));
		l_singer.setVerticalAlignment(SwingConstants.CENTER);
		l_singer.setHorizontalAlignment(SwingConstants.CENTER);
		l_singer.setForeground(Color.GREEN);
		l_singer.setFont(new Font("굴림", Font.BOLD, 15));
		
		p_bar.setPreferredSize(new Dimension(400, 70));
		p_bar.setBackground(Color.BLACK);
		
		p_button.setPreferredSize(new Dimension(400, 70));
		p_button.setBackground(Color.BLACK);
		bt_prev.setIcon(prev_icon);
		bt_play.setIcon(play_icon);
		bt_next.setIcon(next_icon);
		bt_random.setText("Random");
		
		p_etc.setPreferredSize(new Dimension(400, 50));
		p_etc.setBackground(Color.BLACK);
		bt_lyrics.setText("가사");
		bt_playlist.setText("재생목록");
		bt_board.setText("게시판");
		
		p_info.add(l_songname);
		p_info.add(l_singer);
		
		p_bar.add(bar);
		p_button.add(bt_prev);
		p_button.add(bt_play);
		p_button.add(bt_next);
		p_button.add(bt_random);
		
		p_etc.add(bt_lyrics);
		p_etc.add(bt_playlist);
		p_etc.add(bt_board);
		
		this.add(p_canvas);
		this.add(p_info);
		this.add(p_bar);
		this.add(p_button);
		this.add(p_etc);
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(400, 600));
		can.repaint();
		bar.thread.start();
		
		bar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				double x = e.getX();
				playerFunction.mouseclick(x);
			}
		});
		bt_play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerFunction.pause_resume();
			}
		});
		
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerFunction.nextSong();
			}
		});
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerFunction.prevSong();
			}
		});
		
		bt_lyrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lyrics = new Lyrics(playerMain);
				lyrics.area.append(v2Tag.getFirst(FieldKey.LYRICS));
			}
		});
		
		bt_playlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getInformation(); //로그인 정보 획득.
	            edit = new Edit(mp3_main,playerMain);
	            sb = new StringBuffer();
	            sb.append("select s.songname, s.singer from memberlist m, playlist p, songdb s");
	            sb.append(" where p.member_id = m.member_id and p.songdb_id = s.songdb_id and m.member_id ="+login_member_id);
	            playerManage.selectList(sb);
			}
		});
		bt_board.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardMain = new BoardMain(playerMain);
			}
		});
		bt_random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerFunction.randomFlag = !playerFunction.randomFlag;
				if(playerFunction.randomFlag == true) {
					bt_random.setText("Random On");
				}else {
					bt_random.setText("Random Off");
				}
			}
		});
	}
	
	public void reloadInfo() {
		v2Tag = playerManage.getTag();
		playerManage.getImage();
		repaint();
	}
	
	public void getInformation() {
		login_id = mp3_main.getId();
		login_member_id = mp3_main.getMemberId();
		login_name = mp3_main.getName();
	}
}


