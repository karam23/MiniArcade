/*
 * 로그인 창입니다. 아직 언어 선택 combobox 값을 입력받는건 구현을 못했어요
 * by 라운
 */

package member;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import game.GameMain;

public class Login extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField id_field;
	private JPasswordField pwd_field;
	private JPanel paneltxt, panelbtn;
	private JButton login_btn, join_btn;
	
	String combo="ENG";

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
		// UI 부분
		setTitle("Mini Arcade Login");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// TXT
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

		// 콤보박스
		String CBlang[] = { "English", "한국어", "中文" };
		JComboBox comboBox = new JComboBox(CBlang);
		comboBox.setPreferredSize(new Dimension(100, 20));
		paneltxt.add(comboBox);

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				Object obj = ev.getItem();
				if (obj.equals("English")) {
					combo="ENG";
				} else if (obj.equals("한국어")) {
					combo="KOR";
				} else if (obj.equals("中文")) {
					combo="CHI";
				}
			}
		});
		// 버튼
		// 하단 panel2생성
		panelbtn = new JPanel();
		contentPane.add(panelbtn, BorderLayout.SOUTH);

		// 로그인 버튼
		login_btn = new JButton("Login");
		panelbtn.add(login_btn);

		login_btn.addActionListener(this);

		// 회원가입 버튼
		join_btn = new JButton("Sign Up");
		panelbtn.add(join_btn);
		join_btn.addActionListener(this);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		// 입력한 ID랑 PW 콘솔에서 확인
		System.out.println(id);
		System.out.println(pwd);

		if (id.length() < 1 || pwd.length() < 1) {
			JOptionPane.showMessageDialog(null, "You must input ID and Password.");
			return;
		}
		// 데이터 받아오기
		MemberDAO member = new MemberDAO();
		boolean loginTF = member.getMemberDTO(id, pwd);
		if (loginTF == true) {
			if (id.equalsIgnoreCase("root") && pwd.equalsIgnoreCase("1234")) {
				System.out.println("Root Login");
				JOptionPane.showMessageDialog(login_btn, "Loged-in as an admin!");
				this.dispose();// System.exit(0);
				new Member_List();
			} else {
				JOptionPane.showMessageDialog(null, "Login success");
				new GameMain(id, combo);
				this.setVisible(false);// System.exit(0);
				/*if(id.equals(getid) && pwd.equals(getpwd)) {
				 System.out.println("Member Login"); JOptionPane.showMessageDialog(login_btn, "Successful Login!"); //new 페키지.들어갈 페이지();
				 }
				 */
				 
			}
		} else {
			JOptionPane.showMessageDialog(null, "Plz enter vaild ID or Password");
			System.out.println("Invalid Login");
			//JOptionPane.showMessageDialog(login_btn, "Plz enter vaild ID or Password");
		}

	}

	public void memberjoin() {
		new MemberProc();
	}

}