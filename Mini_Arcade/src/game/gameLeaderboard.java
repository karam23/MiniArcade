package game;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class gameLeaderboard extends JFrame {

	private String driver = "org.gjt.mm.mysql.Driver";
	private String url = "jdbc:mysql://rds-mysql.co5xhdkdttkm.ap-northeast-2.rds.amazonaws.com:3306/mini_arcade_db";
	private String user = "useruser";
	private String pwd = "123useruser";

	JPanel contentPane = new JPanel();
	JLabel label;

	JTable table;
	MyModel model;
	Connection con;
	Statement stmt;
	PreparedStatement pstmtSelect, pstmtSelectScroll;
	String sqlSelect = "select g.gameName, u.name, g.userID, g.gameScore from Game_Score as g inner join user as u on g.userID=u.id where gameID=? order by g.gameScore desc"; // and
																																												// UserID=?"
	int gameID; // = 01;

	public gameLeaderboard(int id) {
		gameID = id;
		if (id == 1) {
			setTitle("Rock Paper Scissor LeaderBoard");
		} else if (id == 2) {
			setTitle("Number Hunter LeaderBoard");
		} else if (id == 3) {
			setTitle("Snake LeaderBoard");
		} else if (id == 4) {
			setTitle("Guessing Number LeaderBoard");
		}

		this.setContentPane(contentPane);
		this.setLayout(new BorderLayout());
		this.add(label = new JLabel("RANKING"), "North");

		add(new JScrollPane(table = new JTable()), "Center");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		selectLeaderboard();
	}

	private Connection dbConnect() {
		con = null;
		try {
			Class.forName(driver); // 1. 드라이버 로딩
			con = DriverManager.getConnection(url, user, pwd); // 2. 드라이버 연결
			stmt = con.createStatement();
			pstmtSelectScroll = con.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			pstmtSelect = con.prepareStatement(sqlSelect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private void selectLeaderboard() {
		try {
			con = dbConnect();

			String[] setcol = { "GameName", "User", "UserID", "Score" };

			pstmtSelectScroll.setInt(1, gameID);
			ResultSet rsScroll = pstmtSelectScroll.executeQuery();
			pstmtSelect.setInt(1, gameID);
			ResultSet rs = pstmtSelect.executeQuery();

			if (model == null)
				model = new MyModel();
			model.getRowCount(rsScroll);
			model.setData(rs);
			model.setIndexName(setcol);
			table.setModel(model);
			table.updateUI();

			System.out.println("ok");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAIL!!!");
		}
	}
	/*
	 * private void setTotal() { Connection con = null; // 연결 PreparedStatement
	 * pstmtTotalSet = null; // 명령
	 * 
	 * try { con = dbConnect(); String sqlTotalSet =
	 * "select g.gameID, g.userID, g.gameScore from Game_Score";
	 * 
	 * pstmtTotalSet = con.prepareStatement(sqlTotalSet); int r =
	 * pstmtTotalSet.executeUpdate(); // 실행 -> 저장 if (r > 0) {
	 * System.out.println("게임  SCORE SET 성공"); } else { System.out.println("실패"); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * public static void main(String[] args) { new gameLeaderboard();
	 * 
	 * }
	 */
}
