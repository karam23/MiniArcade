package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import member.Login;

public class rps_game extends JFrame {
//	public static void main(String[] args) {
//		new rps_game();
//	}

	ImageIcon[] gbb = { new ImageIcon("./imagefile/scissors.png"), new ImageIcon("./imagefile/rock.png"),
			new ImageIcon("./imagefile/paper.png") };
	JButton[] btn = new JButton[gbb.length];
	JLabel me = new JLabel("me");
	JLabel com = new JLabel("com");
	JLabel win = new JLabel("win");

	int sum;
	int count = 0;
	String userid;

	private String driver = "org.gjt.mm.mysql.Driver";
	private String url = "jdbc:mysql://rds-mysql.co5xhdkdttkm.ap-northeast-2.rds.amazonaws.com:3306/mini_arcade_db";
	private String user = "useruser";
	private String pwd = "123useruser";

	public rps_game(String id) {
		userid=id;
		this.setTitle("가위 바위 보 게임");
		
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

		//this.setUndecorated(true); //상단바 없애기
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	private Connection dbConnect() {
		Connection con = null;

		try {
			Class.forName(driver); // 1. 드라이버 로딩
			con = DriverManager.getConnection(url, user, pwd); // 2. 드라이버 연결
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private void add() {
		Connection con = null; // 연결
		PreparedStatement pstmtUpdate = null; // 명령

		try {
			con = dbConnect();
			String sqlUpdate = "update Game_Score set gameScore=? where gameID=? and UserID=?";
			
			String strUserID = userid;
			pstmtUpdate = con.prepareStatement(sqlUpdate);
			pstmtUpdate.setInt(1, sum);
			pstmtUpdate.setInt(2, 01);
			pstmtUpdate.setString(3, strUserID);

			int r = pstmtUpdate.executeUpdate(); // 실행 -> 저장
			if (r > 0) {
				System.out.println("가위바위보 게임 SCORE 추가 성공");
				
			} else {
				System.out.println("실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Icon m, Icon c, String w) {
		me.setIcon(m);
		com.setIcon(c);
		win.setText(w);
	}

	public void set_score(int score) {
		sum = sum + score;
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
