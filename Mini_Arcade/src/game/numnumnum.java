package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
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
	JLabel[] la = new JLabel[30];
	JPanel pangame = new JPanel();
	JPanel panbtn = new JPanel(new GridLayout(1, 6));
	JButton btnstart1, btnend;

	public numnumnum(String id) {
		userid = id;
		setTitle("0부터 " + lastnum + "까지 숫자 맞추기");
		this.setContentPane(contentPane);
		this.setLayout(new BorderLayout());
		pangame.setBackground(Color.ORANGE);
		pangame.setLayout(null);

		// 버튼 생성 및 액션 리스너 등록
		btnstart1 = new JButton("Start");

		btnend = new JButton("Exit");

		/*
		 * btnend.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { dispose(); } });
		 */
		panbtn.add(new JLabel(""));
		panbtn.add(btnstart1);
		panbtn.add(btnend);
		panbtn.add(new JLabel(""));
		
		btnstart1.setEnabled(true);
		btnend.setEnabled(true);
		
		MyActionListener MyAction = new MyActionListener();
		btnstart1.addActionListener(MyAction);
		btnend.addActionListener(MyAction);

		this.add(panbtn, BorderLayout.SOUTH);
		this.setUndecorated(true); // 상단바 없애기
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(700, 700);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void gameSetup(int lastN) {

		start = System.currentTimeMillis(); // 시작하는 시점

		Font f1 = new Font("Serif", Font.BOLD, 18);

		for (int i = 0; i < lastN + 1; i++) {
			la[i] = new JLabel("");
			la[i].setFont(f1);
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
		} catch (Exception e) {
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
			pstmtUpdate.setInt(2, 02);
			pstmtUpdate.setString(3, strUserID);

			int r = pstmtUpdate.executeUpdate(); // 실행 -> 저장
			if (r > 0) {
				System.out.println("숫자 두더지 게임 SCORE 추가 성공");
			} else {
				System.out.println("FAIL");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set_score(int runtime) {
		numscore = (int) numscore - (int) runtime;
		System.out.println(numscore);
	}

	class MyActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == btnstart1) {
				btnstart1.setEnabled(false);
				lastnum = "15";
				gameSetup(15);
				
			} else if (obj == btnend) {
				dispose();
			}
		}

	}


	class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JLabel a = (JLabel) e.getSource();
			if (la[c] == a && !a.getText().equals(lastnum)) {
				a.setVisible(false);
				c++;
			} else if (a.getText().equals(lastnum)) {
				a.setVisible(false);
				int runtime;
				end = System.currentTimeMillis();
				runtime = (int) ((end - start) / 1000.0);
				set_score(runtime);
				System.out.println("실행시간" + (end - start) / 1000.0 + "초");
				add();
				dispose();
				JOptionPane.showMessageDialog(null, "GAME OVER, your score " + numscore);
			}
		}
	}
}
