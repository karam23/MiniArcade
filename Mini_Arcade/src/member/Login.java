/*
 * �α��� â�Դϴ�. ���� ��� ���� combobox ���� �Է¹޴°� ������ ���߾��
 * by ���
 */

package member;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import game.snake_game;

public class Login extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField id_field;
	private JPasswordField pwd_field;
	private JPanel paneltxt, panelbtn;
	private JButton login_btn, join_btn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		// UI �κ�

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		paneltxt = new JPanel();
		contentPane.add(paneltxt, BorderLayout.CENTER);
		GridLayout gbl_panel = new GridLayout(3, 2);
		paneltxt.setLayout(gbl_panel);

		JLabel id_label = new JLabel("ID: ");
		paneltxt.add(id_label);

		id_field = new JTextField();
		paneltxt.add(id_field);
		// textField.setColumns(10);

		JLabel pwd_label = new JLabel("PASSWORD: ");
		paneltxt.add(pwd_label);

		pwd_field = new JPasswordField();
		paneltxt.add(pwd_field);
		// textField_1.setColumns(10);

		JLabel lang_label = new JLabel("LANGUAGE: ");
		paneltxt.add(lang_label);

		String CBlang[] = { "English", "�ѱ���", "����" };
		JComboBox comboBox = new JComboBox(CBlang);
		comboBox.setPreferredSize(new Dimension(100, 20));
		paneltxt.add(comboBox);

		// �ϴ� panel2����
		panelbtn = new JPanel();
		contentPane.add(panelbtn, BorderLayout.SOUTH);

		// �α��� ��ư
		login_btn = new JButton("Login");
		panelbtn.add(login_btn);

		login_btn.addActionListener(this);

		// ȸ������ ��ư
		join_btn = new JButton("Sign Up");
		panelbtn.add(join_btn);
		join_btn.addActionListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 170);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == login_btn) {
			memberlogin();
		} else if (obj == join_btn) {
			memberjoin();
		}
	}

	public void memberlogin() {
		String id = id_field.getText();
		String pwd = pwd_field.getText();

		// �Է��� ID�� PW �ֿܼ��� Ȯ��
		System.out.println(id);
		System.out.println(pwd);

		if (id.length() < 1 || pwd.length() < 1) {
			JOptionPane.showMessageDialog(null, "You must input ID and Password.");
			return;
		}
		// ������ �޾ƿ���
		MemberDAO member = new MemberDAO();
		boolean loginTF = member.getMemberDTO(id, pwd);
		if (loginTF == true) {
			if (id.equalsIgnoreCase("root") && pwd.equalsIgnoreCase("1234")) {
				System.out.println("Root Login");
				JOptionPane.showMessageDialog(login_btn, "Loged-in as an admin!");
				new Member_List();
				this.dispose();//System.exit(0);
			}else {
				JOptionPane.showMessageDialog(null, "Login success");
				//new rps_game(id); //���������� 01
				//new numnumnum(id); //���ڵδ��� 02
				new snake_game(id); //������ũ
				//����â ������ �����ٶ�	
				//main(id);
				this.setVisible(false);//System.exit(0);
				// �̺κп� ���� ����â���� ������ ������ �ּ��� �ϴ��� ��Ʈ ������ Member List�� ������ �߽��ϴ� by���
				/*
				 * if(id.equals(getid) && pwd.equals(getpwd)) {
				 * System.out.println("Member Login"); JOptionPane.showMessageDialog(login_btn,
				 * "Successful Login!"); //new ��Ű��.�� ������(); }
				 */
			}		
		} else {
			JOptionPane.showMessageDialog(null, "Login failed");
		}

		/*
		 * else { System.out.println("Invalid Login");
		 * JOptionPane.showMessageDialog(login_btn, "Plz enter vaild ID or Password"); }
		 */
		
	}

	public void memberjoin() {
		new MemberProc();
	}

}