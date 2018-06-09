

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.event.*;

class rps extends JFrame {
	ImageIcon[] gbb = { new ImageIcon("./imagefile/scissors.png"), new ImageIcon("./imagefile/rock.png"),
			new ImageIcon("./imagefile/paper.png") };
	JButton[] btn = new JButton[gbb.length];
	JLabel me = new JLabel("me");
	JLabel com = new JLabel("com");
	JLabel win = new JLabel("win");

	int sum;
	int count = 0;

	//DB 연동
	Connection con;
	Statement stmt;
	PreparedStatement pstmtInsert;
	
	private String driver = "org.gjt.mm.mysql.Driver";
	private String url = "jdbc:mysql://localhost:3306/test";
	private String user = "root";
	private String pwd = "0823";
	
	private String sqlInsert = "insert into gamescore values(?,?,?,?,?,?)";

	public rps() {
		this.setTitle("가위 바위 보 게임");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel northPanel = new JPanel();
		
		northPanel.setBackground(Color.ORANGE);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.LIGHT_GRAY);
		win.setForeground(Color.red);
		centerPanel.add(me);
		centerPanel.add(com);
		centerPanel.add(win);

		MyActionListener action = new MyActionListener();
		for (int i = 0; i < gbb.length; i++) {
			btn[i] = new JButton(gbb[i]);
			btn[i].addActionListener(action);
			northPanel.add(btn[i]);
		}

		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.setSize(800, 600);
		this.setVisible(true);
		
		dbConnect();
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
	
	private void add() {
		Boolean tf;
		try {
/* 1 GameID (INT)
 * 2 GameName (VARCHAR)
 * 3 GameScore (INT)
 * 4 TotalScore (INT)
 * 5 UserID (VARCHAR)
 * 6 Nickname(VARCHAR)*/
			
			String strGameID = "01"; //01: 가위바위보, 02: 숫자두더지, 03: 스네이크, 04: 테트리스
			String strGameName = "RockPaperScissors";
			String strGameScore = Integer.toString(sum);
			String strUserID = "karam";
			String strNick = Util.toLatin("가람");
			
			pstmtInsert.setInt(1, Integer.parseInt(strGameID));
			pstmtInsert.setString(2, strGameName);
			pstmtInsert.setString(3, strUserID);
			pstmtInsert.setString(4, strNick);
			pstmtInsert.setInt(5, Integer.parseInt(strGameScore));
			pstmtInsert.setInt(6, Integer.parseInt(strGameScore));
			pstmtInsert.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "추가 성공");
	}
	
	public void draw(Icon m, Icon c, String w) {
		me.setIcon(m);
		com.setIcon(c);
		win.setText(w);
	}

	public void set_score(int score) {
		sum = sum+score;
		System.out.println(sum);
	}
	public void countplus() {
		count++;
	}

	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (count < 10) {
				countplus();
				String w;
				JButton b = (JButton) e.getSource();
				int n = (int) (Math.random() * 3);
				if (btn[0] == b) {
					if (n == 0) {
						w = "Same!!!";
						draw(gbb[0], gbb[n], w);
						set_score(0);
					} else if (n == 1) {
						w = "COMPUTER!!!";
						draw(gbb[0], gbb[n], w);
						set_score(-10);
					} else if (n == 2) {
						w = "ME!!!";
						draw(gbb[0], gbb[n], w);
						set_score(20);
					}
				} else if (btn[1] == b) {
					if (n == 0) {
						w = "ME!!!";
						draw(gbb[1], gbb[n], w);
						set_score(20);
					} else if (n == 1) {
						w = "Same!!!";
						draw(gbb[1], gbb[n], w);
						set_score(10);
					} else if (n == 2) {
						w = "COMPUTER!!!";
						draw(gbb[1], gbb[n], w);
						set_score(0);
					}
				} else if (btn[2] == b) {
					if (n == 0) {
						w = "COMPUTER!!!";
						draw(gbb[2], gbb[n], w);
						set_score(0);
					} else if (n == 1) {
						w = "ME!!!";
						draw(gbb[2], gbb[n], w);
						set_score(20);
					} else if (n == 2) {
						w = "Same!!!";
						draw(gbb[2], gbb[n], w);
						set_score(10);
					} else
						return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "10번의 가위바위보 게임이 끝났습니다.");
				add();
			}
		}
	}
}

class TimerRunnable extends Thread implements Runnable {
	JLabel timerLabel;

	public TimerRunnable(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	boolean flag = false;

	public void finish() {
		flag = true;
	}

	public void run() {
		int n = 0;
		while (true) {
			timerLabel.setText(Integer.toString(n));
			n++;
			try {
				Thread.sleep(1000);
				if (flag == true)
					return;
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}

public class rps_game {
	public static void main(String[] args) {
		new rps();
	}
}