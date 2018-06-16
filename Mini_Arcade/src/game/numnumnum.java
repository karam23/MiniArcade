package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class numnumnum extends JFrame {
	int c = 0;
	int numscore = 100;
	double start;
	double end;
	String userid;
	String lastnum = "15";

	private String driver = "org.gjt.mm.mysql.Driver";
	private String url = "jdbc:mysql://rds-mysql.co5xhdkdttkm.ap-northeast-2.rds.amazonaws.com:3306/mini_arcade_db";
	private String user = "useruser";
	private String pwd = "123useruser";

	JPanel contentPane = new JPanel();
	JLabel[] la = new JLabel[20];
	JPanel pangame = new JPanel();
	JPanel panbtn = new JPanel();
	JButton btnstart, btnend;
	
	public numnumnum(String id) {
		userid=id;
		setTitle("0부터 "+lastnum+"까지 숫자 맞추기");
		this.setContentPane(contentPane);
		this.setLayout(new BorderLayout());
		pangame.setBackground(Color.ORANGE);
		pangame.setLayout(null);

		// 버튼 생성 및 액션 리스너 등록
		btnstart = new JButton("Start");
		btnend = new JButton("End");
		btnstart.setEnabled(true);// 디폴트
		btnend.setEnabled(false);// 디폴트

		
		btnstart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start = System.currentTimeMillis(); // 시작하는 시점
				gameSetup();
				btnstart.setEnabled(false); // 버튼 비활성화
				btnend.setEnabled(true);
			}
		});
		panbtn.add(btnstart);

		btnend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int runtime;
				end = System.currentTimeMillis();
				runtime = (int) ((end - start) / 1000.0);
				set_score(runtime);
				System.out.println("실행시간" + (end - start) / 1000.0 + "초");
				btnend.setEnabled(false); // 버튼 비활성화
				btnstart.setEnabled(true);
				add();
			}
		});
		panbtn.add(btnend);
		this.add(panbtn, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(700, 700);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public void gameSetup() {
		int lastInt = Integer.parseInt(lastnum);
		for (int i = 0; i < lastInt+1; i++) {
			la[i] = new JLabel("");
			la[i].setText("" + i);
			la[i].setSize(30, 30);
			int x = (int) (Math.random() * 600);
			int y = (int) (Math.random() * 600);
			la[i].setLocation(x, y);
			la[i].addMouseListener(new MyMouseListener());
			pangame.add(la[i]);
		}
		
		pangame.setSize(650, 650);
		this.add(pangame, BorderLayout.CENTER);
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
			pstmtUpdate.setInt(1, numscore);
			pstmtUpdate.setInt(2, 2);
			pstmtUpdate.setString(3, strUserID);

			int r = pstmtUpdate.executeUpdate(); // 실행 -> 저장
			if (r > 0) {
				System.out.println("숫자 두더지 게임 SCORE 추가 성공");
			} else {
				System.out.println("실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set_score(int runtime) {
		numscore = (int)numscore - (int)runtime;
		System.out.println(numscore);
	}

	class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JLabel a = (JLabel) e.getSource();
			if (la[c] == a && !a.getText().equals(lastnum)) {
				a.setVisible(false);
				c++;
			} else if (a.getText().equals(lastnum)){
				a.setVisible(false);
				int runtime;
				end = System.currentTimeMillis();
				runtime = (int) ((end - start) / 1000.0);
				set_score(runtime);
				System.out.println("실행시간" + (end - start) / 1000.0 + "초");
				btnend.setEnabled(false); // 버튼 비활성화
				btnstart.setEnabled(true);
				add();
			}
		}
	}
}
