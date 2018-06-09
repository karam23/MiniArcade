
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.event.*;

class guess extends JFrame {
	int c = 0;
	int numscore=100;
	double start;
	double end;
	
	JLabel[] la = new JLabel[30];
	JPanel pangame = new JPanel();
	JPanel panbtn = new JPanel();
	//DB 연동
		Connection con;
		Statement stmt;
		PreparedStatement pstmtInsert;
		
		private String driver = "org.gjt.mm.mysql.Driver";
		private String url = "jdbc:mysql://localhost:3306/test";
		private String user = "root";
		private String pwd = "0823";
		
		private String sqlInsert = "insert into gamescore values(?,?,?,?,?,?)";

	guess() {
		
		pangame.setBackground(Color.ORANGE);
		this.setTitle("0부터 30까지 숫자 맞추기");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		pangame.setLayout(null);
		
		// 버튼 생성 및 액션 리스너 등록
		JButton btnstart = new JButton("Start");
		JButton btnend = new JButton("End");
		btnstart.setEnabled(true);//디폴트
		btnend.setEnabled(false);//디폴트
		
		btnstart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start = System.currentTimeMillis(); // 시작하는 시점
				btnstart.setEnabled(false); // 버튼 비활성화
				btnend.setEnabled(true);
			}
		});
		panbtn.add(btnstart);
		
		btnend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int runtime;
				end = System.currentTimeMillis();
				runtime = (int) ((end-start)/1000.0);
				set_score(runtime);
				System.out.println("실행시간"+ (end - start) / 1000.0 + "초");
				btnend.setEnabled(false); // 버튼 비활성화
				btnstart.setEnabled(true);
				add();
			}
		});
		panbtn.add(btnend);	
		this.add(panbtn, BorderLayout.SOUTH);
		
		for (int i = 0; i < 10; i++) {
			la[i] = new JLabel("");
			la[i].setText("" + i);
			la[i].setSize(30, 30);
			int x = (int) (Math.random() * 600);
			int y = (int) (Math.random() * 600);
			la[i].setLocation(x, y);
			la[i].addMouseListener(new MyMouseListener());
			pangame.add(la[i]);
		}
		
		this.add(pangame, BorderLayout.CENTER);
		
		this.setSize(700, 700);
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
			 * 3 UserID (VARCHAR)
			 * 4 Nickname(VARCHAR)
			 * 5 GameScore (INT)
			 * 6 TotalScore (INT)*/
			
			String strGameID = "03"; //01: 가위바위보, 02: 숫자두더지, 03: 스네이크, 04: 테트리스
			String strGameName = "NumMole";
			String strGameScore = Integer.toString(numscore);
			String strUserID = "rr";
			String strNick = Util.toLatin("ㄹㄹㄹ");
			
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
	
	public void set_score(int runtime) {
		numscore = numscore-runtime;
		System.out.println(numscore);
	}

	class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JLabel a = (JLabel) e.getSource();
			if (la[c] == a) {
				a.setVisible(false);
				c++;
			} 
		}
	}
}

public class numnumnum {
	public static void main(String[] args) {
		new guess();
	}
}
