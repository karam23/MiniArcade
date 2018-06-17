package game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import member.Login;
import member.MemberProc;

public class GameMain extends JFrame {

	String userid;

	JMenu rank;
	JMenu memberin;
	JMenu logout;
	JMenu end;
	JMenuBar menuBar;
	JMenuItem Rps, Num, Sn, Guess;
	JMenuItem memberma, withdrawl;

	JPanel jp = new JPanel();
	JButton jb1, jb2, jb3, jb4;

	String combo="ENG";
	ImageIcon img, img2, img3, img4;
	
	public void setImage(String combo) {
		switch (combo) {
		case "ENG":
			img = new ImageIcon("./imagefile/rps_image.PNG");
			img2 = new ImageIcon("./imagefile/num_image.PNG");
			img3 = new ImageIcon("./imagefile/snake_image.PNG");
			img4 = new ImageIcon("./imagefile/guessing_image.PNG");
			break;
		case "KOR":
			img = new ImageIcon("./imagefile/rps_kor.PNG");
			img2 = new ImageIcon("./imagefile/num_kor.PNG");
			img3 = new ImageIcon("./imagefile/snake_kor.PNG");
			img4 = new ImageIcon("./imagefile/guessing_kor.PNG");
			break;
		case "CHI":
			img = new ImageIcon("./imagefile/rps_chi.PNG");
			img2 = new ImageIcon("./imagefile/num_chi.PNG");
			img3 = new ImageIcon("./imagefile/snake_chi.PNG");
			img4 = new ImageIcon("./imagefile/guessing_chi.PNG");
			break;
		}
		
	}

	public GameMain(String id, String combo) {
		userid=id;
		setImage(combo);
		setTitle("Mini Arcade"); // 프레임 타이틀바 텍스트 지정
		setSize(800, 620); // 프레임 크기 조정(픽셀)
		this.setLocationRelativeTo(null);
		this.setUndecorated(true); //상단바 없애기
		// 프레임바 우측상단에 X버튼에 대한 강제종료 기능 지정
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jp = new JPanel(new GridLayout(4, 1)); // 패널 객체화 / 기본배치관리자 FlowLayout
		jp.setBackground(Color.WHITE); // 패널 배경색 하얀색으로 설정

		// rps
		jb1 = new JButton(img);
		jb1.setRolloverIcon(img); 
		jb1.setBorderPainted(false); 
//		jb1.setPreferredSize(new Dimension(590, 100)); // 버튼 크기 지정
		jp.add(jb1); 
		add(jp); 

		// number hunter
		jb2 = new JButton(img2);
		jb2.setRolloverIcon(img2);
		jb2.setBorderPainted(false); 
//		jb2.setPreferredSize(new Dimension(490, 90)); // 버튼 크기 지정
		jp.add(jb2); 
		add(jp);

		// snake
		jb3 = new JButton(img3);
		jb3.setRolloverIcon(img3); 
		jb3.setBorderPainted(false); 
//		jb3.setPreferredSize(new Dimension(320, 100)); // 버튼 크기 지정
		jp.add(jb3); 
		add(jp);

		// guessing
		jb4 = new JButton(img4);
		jb4.setRolloverIcon(img4);
		jb4.setBorderPainted(false);
//		jb4.setPreferredSize(new Dimension(380, 100)); // 버튼 크기 지정
		jp.add(jb4); 
		add(jp); 
		jp.setVisible(true);

		MyAction action = new MyAction();
		jb1.addActionListener(action);
		jb2.addActionListener(action);
		jb3.addActionListener(action);
		jb4.addActionListener(action);
		
		// this.setUndecorated(true);
		CreateMenu(); // 메뉴바를 추가한다.
		setVisible(true);

	}

	class MyAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj==jb1) {
				new rps_game(userid);
			}else if(obj==jb2) {
				new numnumnum(userid);
			}else if(obj==jb3) {
				new snake_game(userid);
			}else if(obj==jb4) {
				new Guessing(userid);
			}
			
		}
		
	}
	private void CreateMenu() {
		menuBar = new JMenuBar(); // 메뉴바를 생성

		rank = new JMenu("Ranking");
		menuBar.setBackground(new Color(240, 248, 255));
		menuBar.add(rank);

		memberin = new JMenu("User Info");
		menuBar.add(memberin);

		logout = new JMenu("Logout");
		menuBar.add(logout);

		end = new JMenu("End");
		menuBar.add(end);

		// 랭킹의 메뉴아이템
		Rps = new JMenuItem("RockPaperScissors Ranking");
		rank.add(Rps);
		Num = new JMenuItem("Number Hunter Ranking");
		rank.add(Num);
		Sn = new JMenuItem("Snake Ranking");
		rank.add(Sn);
		Guess = new JMenuItem("Guessing Ranking");
		rank.add(Guess);

		// 회원정보의 메뉴아이템
		memberma = new JMenuItem("Revise");
		memberin.add(memberma);
		//withdrawl = new JMenuItem("회원탈퇴");
		//memberin.add(withdrawl);

	//	menuBar.setBorder(BorderFactory.createLineBorder(Color.gray)); // 메뉴바 색상

		TestListenr listener = new TestListenr(); // 아래의 컴포넌트 리스너 클래스 생성
		MyMouse mouse = new MyMouse();
		
		Rps.addActionListener(listener);
		Num.addActionListener(listener);
		Sn.addActionListener(listener);
		Guess.addActionListener(listener);

		memberma.addActionListener(listener);
		//withdrawl.addActionListener(listener);

		logout.addMouseListener(mouse);
		end.addMouseListener(mouse);

		setJMenuBar(menuBar);
	}

	class TestListenr implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == Rps) { // 이벤트가 Rps이라면
				new gameLeaderboard(1);
				System.out.println("Rps Ranking");
				// System.exit(1); //'종료' 선택시 프로그램 종료
			} else if (event.getSource() == Num) {
				new gameLeaderboard(2);
				System.out.println("Num Ranking");
			} else if (event.getSource() == Sn) {
				new gameLeaderboard(3);
				System.out.println("Snake Ranking");
			} else if (event.getSource() == Guess) {
				new gameLeaderboard(4);
				System.out.println("Guessing Ranking");
			} else if (event.getSource() == memberma){
				MemberProc mem = new MemberProc(userid); 
				System.out.println("User Info Revise");
			}
			//else if (event.getSource() == withdrawl){
			//	System.out.println("회원 탈퇴 성공");}
		}
	}

	class MyMouse extends MouseAdapter {

		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() == end) {
				System.out.println("exit ok");
				System.exit(0);
			} else if (arg0.getSource() == logout){
				dispose();
				new Login();
				System.out.println("You've successfully logout");
			}

		}

	}
	/*
	public static void main(String[] args) {
		new main();

	}
	*/
}
