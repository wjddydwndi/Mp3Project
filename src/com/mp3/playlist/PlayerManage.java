package com.mp3.playlist;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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

import com.mp3.util.listModel;

public class PlayerManage {
	PlayerMain playerMain;
	File file;
	AudioFile audioFile;
	MP3File mp3;
	String login_id;
	int login_member_id;
	Connection con;
	ConnectionManager connectionManager;
	listModel listModel;
	int maxrow;

	public PlayerManage(PlayerMain playerMain) {
		this.playerMain = playerMain;
		connectionManager = new ConnectionManager();
		con = connectionManager.getConnection();
	}
	  public void selectList(StringBuffer sb) {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      ResultSetMetaData meta = null;
	      try {
	         if (con != null) {
	            pstmt = con.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
	                  ResultSet.CONCUR_READ_ONLY);
	            rs = pstmt.executeQuery();
	            meta = rs.getMetaData();
	            String[] columnName = new String[meta.getColumnCount()];
	            for (int i = 0; i < 2; i++) {
	               columnName[i] = meta.getColumnName(i + 1);
	            }
	            rs.last();
	            int total = rs.getRow();
	            String[][] data = null;
	            if (total > 0) {
	               data = new String[total][columnName.length];
	               rs.beforeFirst();
	               for (int i = 0; i < total; i++) {
	                  rs.next();
	                  for (int j = 0; j < columnName.length; j++) {
	                     data[i][j] = rs.getString(j + 1);
	                  }
	               }
	            } else {
	               JOptionPane.showMessageDialog(playerMain, "재생목록에 존재하는 파일이 없습니다.");
	               return;
	            }
	            listModel = new listModel();
	            listModel.columnName = columnName;
	            listModel.data = data;
	            playerMain.edit.table.setModel(listModel);
	            playerMain.edit.table.updateUI();
	            maxrow = playerMain.edit.table.getRowCount();
				System.out.println(maxrow);
	         } else {
	            System.out.println("con이 문젠거냐?");
	         }
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } finally {
	         if (rs != null) {
	            try {
	               rs.close();
	            } catch (SQLException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            }
	         }
	         if (pstmt != null) {
	            try {
	               pstmt.close();
	            } catch (SQLException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            }
	         }
	      }
	   }
	// ===============================================================[추가 관련 메서드 start] =========================================
	public void checkDB(String filepath) {
		String checkpath = filepath;
		getLength();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from songdb where filepath = '" + checkpath + "'";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int songdb_id = rs.getInt("songdb_id");
				insertList(songdb_id);
			} else {
				insertDB(filepath);
				checkDB(filepath);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt, rs);
		}
	}

	public void insertDB(String filepath) {
		String path2 = filepath;
		getLength();
		PreparedStatement pstmt = null;
		String songname = playerMain.v2Tag.getFirst(FieldKey.TITLE);
		String singer = playerMain.v2Tag.getFirst(FieldKey.ARTIST);

		StringBuffer sb = new StringBuffer();
		sb.append("insert into songdb (songdb_id, songname, singer, filepath)");
		sb.append(" values (songdb_seq.nextval,?,?,?)");
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, songname);
			pstmt.setString(2, singer);
			pstmt.setString(3, filepath);
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void insertList(int songdb_id) {
		playerMain.getInformation();
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into playlist (playlist_id, member_id, songdb_id)");
		sb.append(" values (playlist_seq.nextval, ?, ?)");
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, playerMain.login_member_id);
			pstmt.setInt(2, songdb_id);
			int result = pstmt.executeUpdate();
			if (result != 0) {
				JOptionPane.showMessageDialog(playerMain, "리스트에 추가되었습니다.");
			} else {
				JOptionPane.showMessageDialog(playerMain, "오류가 발생했습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt);
		}

	}

	// ===============================================================[추가 관련 메서드 end] =========================================

	// ===============================================================[재생목록 수정 관련 메서드 start] =========================================
	// 음원 정보 출력. (텍스트 상자에 출력)
	public void getInfo() {
		int row = playerMain.edit.row;
		int title = 0;
		int singer = 1;
		playerMain.edit.txt_title.setText((String) playerMain.edit.table.getValueAt(row, title));
		playerMain.edit.txt_singer.setText((String) playerMain.edit.table.getValueAt(row, singer));
		playerMain.l_songname.setText((String) playerMain.edit.table.getValueAt(row, title));
		playerMain.l_singer.setText((String) playerMain.edit.table.getValueAt(row, singer));
		// System.out.println("*경로: " + p_main.path); // 선택한 음원의 경로 출력.
		System.out.println("*SONGDB_ID: " + playerMain.songdb_id); // 선택한 음원의 songdb_id 출력.
	}

	// 음원 수정.
	public void modify() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("update songdb set songname='" + playerMain.edit.txt_title.getText() + "'"); // 1. songdb table의
																								// songname값을 수정.
		sb.append(",singer='" + playerMain.edit.txt_singer.getText() + "'"); // 2. songdb table의 singer 값을 수정.
		sb.append(" where songdb_id=" + playerMain.songdb_id); // 3. 조건은, SONGDB_ID가 일치하는 항목으로 함.
		System.out.println(sb.toString());
		try {
			pstmt = playerMain.playerManage.con.prepareStatement(sb.toString());
			// Connection은, PlayerMain 내 PlayerManage에 있는 con을 차용함.
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt, rs);
		}
	}

	// 음원 삭제.
	public void delete() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("delete from playlist");
		sb.append(" where member_id=" + playerMain.login_member_id);
		sb.append(" and songdb_id=" + playerMain.songdb_id);
		System.out.println(sb.toString());
		try {
			pstmt = playerMain.playerManage.con.prepareStatement(sb.toString());
			// Connection은, PlayerMain 내 PlayerManage에 있는 con을 차용함.
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt, rs);
			// playerMain.edit.table.updateUI();
		}
	}
	// ===============================================================[재생목록 수정 관련 메서드 end] =========================================

	public Object getDB(int flag) { // 재생목록 클릭시 해당 파일의 path와 songdb_id를 추출하는 메서드
		Object returnValue = null;
		String path = null;
		int songdb_id = 0;
		int row = playerMain.edit.row;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select filepath, songdb_id from songdb where songname = ? and singer = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) playerMain.edit.table.getValueAt(row, 0));
			pstmt.setString(2, (String) playerMain.edit.table.getValueAt(row, 1));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				path = rs.getString("filepath");
				songdb_id = rs.getInt("songdb_id");
			} else {
				System.out.println("실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt, rs);
		}
		if (flag == 1) {
			returnValue = path;
		} else if (flag == 2) {
			returnValue = songdb_id;
		}
		return returnValue;
	}

	// ===============================================================[MP3 File Tag 관련 메서드 start] =========================================
	public int getLength() {
		if (playerMain.path != "") {
			file = new File(playerMain.path);
			try {
				audioFile = AudioFileIO.read(file);
				mp3 = (MP3File) AudioFileIO.read(file);
			} catch (CannotReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReadOnlyFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAudioFrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 음원의 길이 추출
		int length = audioFile.getAudioHeader().getTrackLength();
		return length;
	}

	public void getImage() {
		Artwork artwork = playerMain.v2Tag.getFirstArtwork();
		BufferedImage m = null;
		try {
			m = (BufferedImage) artwork.getImage();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// m = ImageIO.read(file); // 이미지 파일을 읽어와서 BufferedImage 에 넣음
			playerMain.albumicon = new ImageIcon(m);
			playerMain.image = playerMain.albumicon.getImage();
			// l.setIcon(new ImageIcon(m)); // 레이블에 이미지 표시
		} catch (Exception e) {

		}
	}

	public AbstractID3v2Tag getTag() {
		if (playerMain.path != "") {
			file = new File(playerMain.path);
			try {
				audioFile = AudioFileIO.read(file);
				mp3 = (MP3File) AudioFileIO.read(file);
			} catch (CannotReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReadOnlyFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAudioFrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 음원 이미지 추출
		AbstractID3v2Tag v2Tag = mp3.getID3v2Tag();
		return v2Tag;
	}
	
	// ===============================================================[MP3 File Tag 관련 메서드 end] =========================================
}
