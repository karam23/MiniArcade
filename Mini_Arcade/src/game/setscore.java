package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import member.Login;

public class setscore {
/*	Login loginUser;
	String strUserID;
	// DB ����
	Connection con;
	Statement stmt;
	PreparedStatement pstmtInsert;

	private String driver = "org.gjt.mm.mysql.Driver";
	private String url = "jdbc:mysql://rds-mysql.co5xhdkdttkm.ap-northeast-2.rds.amazonaws.com:3306/mini_arcade_db";
	private String user = "useruser";
	private String pwd = "123useruser";

	private String sqlInsert = "insert into Game_Score values(?,?,?,?)";

//	public void setUserID(String id) {
//		strUserID=id;
//	}	
 
	public setscore(int gameid, int score) {
		dbConnect();
		add(gameid, score);
	}

	private void dbConnect() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pwd);
			stmt = con.createStatement();
			pstmtInsert = con.prepareStatement(sqlInsert);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add(int gameid, int score) {
		Boolean tf;
		String strGameName="no game";

		try {
			// 1: ����������, 2: ���ڵδ���, 3: ������ũ, 4: ��Ʈ����
			if (gameid == 1) {
				strGameName = "RockPaperScissors";
			} else if (gameid == 2) {
				strGameName = "Num Mole";
			} else if (gameid == 3) {
				strGameName = "Snake";
			} else if (gameid == 4) {
				strGameName = "Tetris";
			}
			strUserID=loginUser.getID();
			// String strNick = Util.toLatin("����");

			pstmtInsert.setInt(1, gameid);
			pstmtInsert.setString(2, strGameName);
			pstmtInsert.setString(3, strUserID);
			// pstmtInsert.setString(4, strNick);
			pstmtInsert.setInt(4, score);
			// pstmtInsert.setInt(6, Integer.parseInt(strGameScore));
			pstmtInsert.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(strGameName+"SCORE �߰� ����");
	}
*/
}
