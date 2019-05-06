package com.mp3.playlist;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.mp3.util.MyProgressBar;

import javazoom.jl.player.Player;

public class PlayerFunction {
	PlayerMain playerMain;
	Player player;
	FileInputStream FIS;
	BufferedInputStream BIS;
	boolean canResume;
	int total;
	int stopped;
	int playing;
	boolean valid;
	boolean paused = true; // 일시정지 여부 변수. 일시정지 시 true.
	int sec = 0;
	boolean threadFlag = true;
	Thread songThread;
	boolean randomFlag = false;
	public PlayerFunction(PlayerMain playerMain) {
		player = null;
		FIS = null;
		valid = false;
		BIS = null;
		total = 0;
		stopped = 0;
		canResume = false;
		this.playerMain = playerMain;	
		
		songThread = new Thread() { // 시간이 다 지나면 다음곡을 진행시키기 위한 쓰레드
			public void run() {
				try {
					while (true) {
						if (threadFlag) {
							if (sec >= playerMain.bar.length) {
								player.close();
								sec = 0;
								nextSongstart();
							}
							sec++;
							Thread.sleep(1000);
						} else {
							System.out.println("");
							Thread.sleep(1000);
							continue;
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	
	public void pause_resume() { //정지, 시작 플래그를 설정하는 메서드 
		if (paused == false) { // false이면 일시정지 발동.
			playerMain.bar.flag = false; // ProgressBar도 정지시킨다.
			threadFlag = false; // songthread도 정지
			pause(); // 정지 메서드 실행 
			playerMain.bt_play.setIcon(playerMain.play_icon);
		} else { // true이면 일시정지 해제, 나머지 스트림 재생.
			if (total > 0) { // total이 0 이상이라면 먼저 실행된 노래가 있는것이기 때문에 resume이 실행되어야 한다.
				playerMain.bar.flag = true;
				threadFlag = true;
				resume();
				playerMain.bt_play.setIcon(playerMain.pause_icon);
			} else { // total이 0이라면 먼저 실행된 노래가 없기 때문에 처음부터 시작이므로 play가 실행된다.
				paused = false;
				barResetting();
				threadFlag = true;
				play(-1);
				if (songThread.isAlive()) { // songthread가 살아있는지 여부를 체크하여 
					threadFlag = true;
				} else {
					songThread.start();
				}
			}
		}
	}

	public void pause() { // pause_resume 메서드로 인해 정지조건이 만족되는 경우 실행되는 메서드
		try {
			stopped = FIS.available(); // 노래파일이 지금 구현이 가능한 정도를 계산 --> 전체 stream - 정지되기 전까지 stream
			playInitialize();//player 초기화.
			// 재생에 필요한 파일 처리를 다 null 처리 해준다.
			if (valid) // canresume
				canResume = true; // pause와 resume을 오갈 수 있게 해주는 flag
			paused = !paused;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resume() { // canresume이 true일때 작동
		if (!canResume) // canresume이 false이면 당연히 작동되면 안되기 때문에 return
			return;
		if (play(total - stopped)) // 전체에서 여태까지 재생하고 남은양을 뺀 양을 실행해서 오류가 안나면 재생이 된다는 의미이므로..
			canResume = false; // 다시 canresume을 false로 만든다.
		paused = !paused; // 최종적으로 paused의 flag를 반전시켜 pause가 가능한 상태로 만든다.
	}

	public boolean play(int pos) {
		valid = true; // 오류가 발생했는지의 유무를 알려주는 값 play method의 반환값
		canResume = false; // pause -> Resume으로 변경되는 flag를 false로 만들어 play중에는 resume이 불가능하게 만든다.
		try {
			FIS = new FileInputStream(playerMain.path); // 파일 경로에 해당하는 파일을 읽어오고
			total = FIS.available(); // 그 파일의 전체 stream을 구한다.
			if (pos > -1) // pos가 -1 초과 즉 resume 메서드에서 total -stopped의 값이 -1 초과라면...
				FIS.skip(pos); // 그만큼을 skip 즉 정지했던 부분까지 스킵시킨다. 그럼 다시 시작하면 정지했던 부분부터 시작된다.
			BIS = new BufferedInputStream(FIS); // buffer로 다시 읽어오고
			player = new Player(BIS); // player는 그 buffer를 재생시킨다.
			new Thread(new Runnable() {
				public void run() {
					try {
						player.play(); // play 메서드를 실행시켜 노래를 재생시킨다.
					} catch (Exception e) { // 만약 오류가 발생하면
						JOptionPane.showMessageDialog(null, "Error playing mp3 file 1");
						valid = false; // valid를 false로 반환시켜 오류가 발생했다는걸 알려준다.
					}
				}
			}).start(); // thread start!!!
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error playing mp3 file 2");
			valid = false; // 이건 파일을 찾지 못한경우이고 파일을 찾지 못하면 똑같이 valid를 false로 반환시켜 재생이 안된다는 것을 알려준다.
		}
		return valid; // valid값을 반환시켜 정상적으로 재생되었는지 되지 않았는지를 반환한다.
	}

	public void nextSong() { // bt_next를 누르거나 시간이 다되어 다음곡으로 넘어갈 때 발생하는 method
		sec = 0; // sec를 0으로 초기화시켜 새로운 곡이 재생될 때 songthread가 처음부터 다시 시작되게 한다.
		int random;
		if(randomFlag == true) { // 렌덤 플래그가 true인 경우 랜덤 기능 가동
			random = (int) (Math.random() * playerMain.playerManage.maxrow);
			if(playerMain.edit.row == random) {
				nextSong();
			}
			playerMain.edit.row = random;
		}else {
			if (playerMain.edit.row < playerMain.playerManage.maxrow - 1) { 
				playerMain.edit.row++;
			} else { // 재생목록 테이블이 끝까지 간 경우 
				playerMain.edit.row = 0; // 처음으로 돌아간다
			}
		}
		playerMain.edit.playMethod();
		pause_resume();
	}

	public void prevSong() { // bt_prev를 누르는 경우 이전곡으로 넘어갈 때 발생하는 method
		sec = 0; // nextSong과 마찬가지로 새로운 곡이 실행되는 것이기 때문에 sec를 0으로 초기화 시켜 다시 songThread가 돌아가도록 한다.
		if (playerMain.edit.row > 0) { 
			playerMain.edit.row--;
		} else { // playlist의 처음에서 이전버튼을 누르는 경우 
			playerMain.edit.row = playerMain.playerManage.maxrow - 1; // 리스트의 마지막으로 되돌아간다.
		}
		playerMain.edit.playMethod();
		pause_resume();
	}

	public void nextSongstart() { // 다음곡 재생될 때 발생하는 method
		playInitialize();
		nextSong();
		playerMain.can.repaint();
		paused = false;
		barResetting();
	}
	
	public void barResetting() { // 새로운 곡이 재생될 때 마다 progressbar가 reset되는 메서드
		double x = 100;
		playerMain.bar.length = playerMain.playerManage.getLength();
		playerMain.bar.velX = x / playerMain.playerManage.getLength();
		playerMain.bar.min = playerMain.bar.length / 60;
		playerMain.bar.second = playerMain.bar.length % 60;
		playerMain.bt_play.setIcon(playerMain.pause_icon);
		playerMain.bar.flag = true;
	}
	
	public void playInitialize() { // player 초기화 메서드
		player.close();
		FIS = null;
		BIS = null;
		player = null;
		// 새로 재생될거라 다 다시 null로 돌아간다.
	}
	
	
	public void mouseclick(double x) {
		double realx = (x / 350) * 100; // 1~99까지의 숫자 반환
		pause_resume();
		stopped = (int) (total - (total * (realx / 100)));
		pause_resume();
		playerMain.bar.sec = 0;
		playerMain.bar.start_min = 0;
		playerMain.bar.sec =  (int) (((double)playerMain.bar.length / 100) * realx);
		playerMain.bar.start_sec = playerMain.bar.sec % 60;
		sec = playerMain.bar.sec;
		playerMain.bar.n = realx;
		playerMain.bar.setValue((int) realx);
	}
}
