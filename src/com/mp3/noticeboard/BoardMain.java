package com.mp3.noticeboard;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mp3.db.ConnectionManager;
import com.mp3.playlist.PlayerMain;


public class BoardMain extends JFrame {
	JPanel container;
	PlayerMain playerMain;
	JPanel[] pages = new JPanel[4];
	int number = 0;
	
	String id; 
	ConnectionManager connectManager;
	Connection con;
	ClickedPage clickedPage;

	public BoardMain(PlayerMain playerMain) {
		this.playerMain = playerMain;
		connectManager = new ConnectionManager();
		con = connectManager.getConnection();
		playerMain.getInformation();
		id = playerMain.login_id;
		container = new JPanel();
		pages[0] = new NoticeBoard(this);
		pages[1] = new WriteBoard(this);
		pages[2] = new ClickedPage(this);
		pages[3] = new SetBoard(this);
		
		add(container);
		container.add(pages[0]);
		container.add(pages[1]);
		container.add(pages[2]);
		container.add(pages[3]);
		
		container.setPreferredSize(new Dimension(800, 800));

		showPage(0);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setSize(800, 830);
		setLocationRelativeTo(null);
		setVisible(true);
		container.updateUI();
		System.out.println(id);
	}

	public void showPage(int number) {
		if (number == 0) {
			pages[0].setVisible(true);

		} else if (number == 1) {
			pages[0].setVisible(false);
			pages[1].setVisible(true);

		} else if (number == 2) {
			pages[0].setVisible(false);
			pages[2].setVisible(true);
		}else if(number ==3) {
			pages[2].setVisible(false);
			pages[0].setVisible(true);
		}else if(number==4) {
			pages[0].setVisible(false);
			pages[3].setVisible(true);
		}else if(number==5) {
			pages[3].setVisible(false);
			pages[0].setVisible(true);
		}
	}
}
