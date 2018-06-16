import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class main extends JFrame {
	JMenu rank;
	JMenuBar menuBar;
	JMenuItem Rps, Num;
	JButton btnRps, btnNum, btnSnake, btnTetris;
	
	public main() {
		setLayout(null); // Layout을 NULL로 설정 (컴포넌트의 위치를 사용자가 설정해주어야 함)
		CreateMenu(); // 메뉴바를 추가한다.
		setTitle("Mini Arcade"); // Frame의 타이틀 이름 주기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Frame의 X를 누를경우 종료
		setSize(400, 500); // Frame의 크기 설정
		setVisible(true); // 생성한 Frame을 윈도우에 뿌리기
	}

	private void CreateMenu() {
		menuBar = new JMenuBar(); // 메뉴바를 생성
		rank = new JMenu("랭킹");
		menuBar.add(rank);
		Rps = new JMenuItem("가위바위보 랭킹");
		rank.add(Rps);
		Num = new JMenuItem("숫자두더지 랭킹");
		rank.add(Num);

		menuBar.setBorder(BorderFactory.createLineBorder(Color.gray)); // 메뉴바 색상 지정

		TestListenr listener = new TestListenr(); // 아래의 컴포넌트 리스너 클래스 생성
		Rps.addActionListener(listener);
		Num.addActionListener(listener);

		setJMenuBar(menuBar);
	}

	class TestListenr implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == Rps) { // 이벤트가 Rps이라면
				System.out.println("Rps Ranking");
				// System.exit(1); //'종료' 선택시 프로그램 종료
			} else if (event.getSource() == Num) {
				System.out.println("Num Ranking");

			}
		}
	}

	public static void main(String[] args) {
		new main();

	}

}
