
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

import game.Guessing;
import game.numnumnum;
import game.rps_game;
import game.snake_game;
import member.Login;

public class main extends JFrame {

	String userid;

	JMenu rank;
	JMenu memberin;
	JMenu logout;
	JMenu end;
	JMenuBar menuBar;
	JMenuItem Rps, Num, Sn, Guess, Total;
	JMenuItem memberma, withdrawl;

	JPanel jp = new JPanel();
	JButton jb1, jb2, jb3, jb4;

	ImageIcon img = new ImageIcon("." + "/Button_Image/rps_image.PNG");
	ImageIcon img2 = new ImageIcon("./Button_Image/num_image.PNG");
	ImageIcon img3 = new ImageIcon("." + "/Button_Image/snake_image.PNG");
	ImageIcon img4 = new ImageIcon("./Button_Image/tetris_image.PNG");

	public main(String id) {
		userid=id;
		setTitle("Mini Arcade"); // 프레임 타이틀바 텍스트 지정
		setSize(800, 700); // 프레임 크기 조정(픽셀)
		// 프레임바 우측상단에 X버튼에 대한 강제종료 기능 지정
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jp = new JPanel(new GridLayout(4, 1)); // 패널 객체화 / 기본배치관리자 FlowLayout
		jp.setBackground(Color.WHITE); // 패널 배경색 하얀색으로 설정

		// rps
		jb1 = new JButton(img);
		jb1.setRolloverIcon(img); // 버튼에 마우스가 올라갈떄 이미지 변환
		jb1.setBorderPainted(false); // 버튼 테두리 설정해제
//		jb1.setPreferredSize(new Dimension(590, 100)); // 버튼 크기 지정
		jp.add(jb1); // 패널에 버튼을 붙여준다
		add(jp); // 메인 프레임에 메인패널을 붙여주는 작업
		// setVisible(true);

		// number hunter
		jb2 = new JButton(img2);
		jb2.setRolloverIcon(img2); // 버튼에 마우스가 올라갈떄 이미지 변환
		jb2.setBorderPainted(false); // 버튼 테두리 설정해제
//		jb2.setPreferredSize(new Dimension(490, 90)); // 버튼 크기 지정
		jp.add(jb2); // 패널에 버튼을 붙여준다
		add(jp); // 메인 프레임에 메인패널을 붙여주는 작업
		// setVisible(true);

		// snake
		jb3 = new JButton(img3);
		jb3.setRolloverIcon(img3); // 버튼에 마우스가 올라갈떄 이미지 변환
		jb3.setBorderPainted(false); // 버튼 테두리 설정해제
//		jb3.setPreferredSize(new Dimension(320, 100)); // 버튼 크기 지정
		jp.add(jb3); // 패널에 버튼을 붙여준다
		add(jp); // 메인 프레임에 메인패널을 붙여주는 작업
		// setVisible(true);

		// tetris
		jb4 = new JButton(img4);
		jb4.setRolloverIcon(img4); // 버튼에 마우스가 올라갈떄 이미지 변환
		jb4.setBorderPainted(false); // 버튼 테두리 설정해제
//		jb4.setPreferredSize(new Dimension(380, 100)); // 버튼 크기 지정
		jp.add(jb4); // 패널에 버튼을 붙여준다
		add(jp); // 메인 프레임에 메인패널을 붙여주는 작업
		jp.setVisible(true);

		// this.setUndecorated(true);
		CreateMenu(); // 메뉴바를 추가한다.
		setVisible(true);

	}

	private void CreateMenu() {
		menuBar = new JMenuBar(); // 메뉴바를 생성

		rank = new JMenu("랭킹");
		menuBar.setBackground(Color.WHITE);
		menuBar.add(rank);

		memberin = new JMenu("회원정보");
		menuBar.add(memberin);

		logout = new JMenu("로그아웃");
		menuBar.add(logout);

		end = new JMenu("종료");
		menuBar.add(end);

		// 랭킹의 메뉴아이템
		Rps = new JMenuItem("가위바위보 랭킹");
		rank.add(Rps);
		Num = new JMenuItem("숫자두더지 랭킹");
		rank.add(Num);
		Sn = new JMenuItem("스네이크 랭킹");
		rank.add(Sn);
		Guess = new JMenuItem("숫자 맞추기  랭킹");
		rank.add(Guess);
		Total = new JMenuItem("총 랭킹");
		rank.add(Total);

		// 회원정보의 메뉴아이템
		memberma = new JMenuItem("회원정보수정");
		memberin.add(memberma);
		withdrawl = new JMenuItem("회원탈퇴");
		memberin.add(withdrawl);

	//	menuBar.setBorder(BorderFactory.createLineBorder(Color.gray)); // 메뉴바 색상

		TestListenr listener = new TestListenr(); // 아래의 컴포넌트 리스너 클래스 생성
		MyMouse mouse = new MyMouse();
		
		Rps.addActionListener(listener);
		Num.addActionListener(listener);
		Sn.addActionListener(listener);
		Guess.addActionListener(listener);
		Total.addActionListener(listener);

		memberma.addActionListener(listener);
		withdrawl.addActionListener(listener);

		logout.addMouseListener(mouse);
		end.addMouseListener(mouse);

		setJMenuBar(menuBar);
	}

	class TestListenr implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == Rps) { // 이벤트가 Rps이라면
				new rps_game(userid);
				System.out.println("Rps Ranking");
				// System.exit(1); //'종료' 선택시 프로그램 종료
			} else if (event.getSource() == Num) {
				new numnumnum(userid);
				System.out.println("Num Ranking");
			} else if (event.getSource() == Sn) {
				new snake_game(userid);
				System.out.println("Snake Ranking");
			} else if (event.getSource() == Guess) {
				new Guessing(userid);
				System.out.println("Tetris Ranking");
			} else if (event.getSource() == memberma){
				System.out.println("회원 정보 수정 성공");
			} else if (event.getSource() == withdrawl){
				System.out.println("회원 탈퇴 성공");
			}
		}
	}

	class MyMouse extends MouseAdapter {

		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() == end) {
				System.out.println("exit ok");
				System.exit(0);
			} else if (arg0.getSource() == logout){
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
