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
		setLayout(null); // Layout�� NULL�� ���� (������Ʈ�� ��ġ�� ����ڰ� �������־�� ��)
		CreateMenu(); // �޴��ٸ� �߰��Ѵ�.
		setTitle("Mini Arcade"); // Frame�� Ÿ��Ʋ �̸� �ֱ�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Frame�� X�� ������� ����
		setSize(400, 500); // Frame�� ũ�� ����
		setVisible(true); // ������ Frame�� �����쿡 �Ѹ���
	}

	private void CreateMenu() {
		menuBar = new JMenuBar(); // �޴��ٸ� ����
		rank = new JMenu("��ŷ");
		menuBar.add(rank);
		Rps = new JMenuItem("���������� ��ŷ");
		rank.add(Rps);
		Num = new JMenuItem("���ڵδ��� ��ŷ");
		rank.add(Num);

		menuBar.setBorder(BorderFactory.createLineBorder(Color.gray)); // �޴��� ���� ����

		TestListenr listener = new TestListenr(); // �Ʒ��� ������Ʈ ������ Ŭ���� ����
		Rps.addActionListener(listener);
		Num.addActionListener(listener);

		setJMenuBar(menuBar);
	}

	class TestListenr implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == Rps) { // �̺�Ʈ�� Rps�̶��
				System.out.println("Rps Ranking");
				// System.exit(1); //'����' ���ý� ���α׷� ����
			} else if (event.getSource() == Num) {
				System.out.println("Num Ranking");

			}
		}
	}

	public static void main(String[] args) {
		new main();

	}

}
