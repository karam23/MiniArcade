/*
 *회원 가입 창입니다. Login.java에서 Join의 액션 리스너를 받고 여기로 오게됩니다.
 *by라운
 */

package member;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import game.Util;

public class MemberProc extends JFrame implements ActionListener {

	JPanel p;
	JTextField tfId, tfName, tfEmail;
	JPasswordField pfPwd; // 비밀번호
	JRadioButton rbMan, rbWoman; // 남, 여
	JButton btnInsert, btnCancel, btnUpdate, btnDelete; // 가입, 취소, 수정 , 탈퇴 버튼

	GridBagLayout gb;
	GridBagConstraints gbc;
	Member_List mList;

	public MemberProc() { // 가입용 생성자
		createUI(); // UI작성해주는 메소드
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);

	}// 생성자
/*
	public MemberProc(Login join_btn) { //가입용 생성자
		createUI(); // UI작성해주는 메소드
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
	}// 생성자
*/
	public MemberProc(Member_List mList) { // 가입용 생성자
		createUI(); // UI작성해주는 메소드
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.mList = mList;
	}// 생성자

	public MemberProc(String id) { // 가입자의 수정/삭제용 생성자
		createUI();
		btnInsert.setEnabled(false);
		btnInsert.setVisible(false);
		//this.mList = mList;

		System.out.println("id=" + id);

		MemberDAO dao = new MemberDAO();
		MemberDTO vMem = dao.getMemberDTO(id);
		viewData(vMem);

	}// id를 가지고 생성
	
	public MemberProc(String id, Member_List mList) { // 수정/삭제용 생성자
		createUI();
		btnInsert.setEnabled(false);
		btnInsert.setVisible(false);
		//this.mList = mList;

		System.out.println("id=" + id);

		MemberDAO dao = new MemberDAO();
		MemberDTO vMem = dao.getMemberDTO(id);
		viewData(vMem);

	}// id를 가지고 생성

	// MemberDTO 의 회원 정보를 가지고 화면에 셋팅해주는 메소드
	private void viewData(MemberDTO vMem) {

		String id = vMem.getId();
		String pwd = vMem.getPwd();
		String name = vMem.getName();
		String gender = vMem.getGender();
		String email = vMem.getEmail();		// 화면에 세팅
		tfId.setText(id);
		tfId.setEditable(false); // 편집 안되게
		pfPwd.setText(""); // 비밀번호는 안보여준다.
		tfName.setText(name);

		if (gender.equals("M")) {
			rbMan.setSelected(true);
		} else if (gender.equals("W")) {
			rbWoman.setSelected(true);
		}

		tfEmail.setText(email);

	}// viewData

	private void createUI() {
		//mList = new Member_List();
		this.setTitle("User Info");
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2.0;
		gbc.weighty = 2.0;

		// 아이디
		JLabel bId = new JLabel("ID : ");
		tfId = new JTextField(20);
		// 그리드백에 붙이기
		gbAdd(bId, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 3, 1);

		// 비밀번호
		JLabel bPwd = new JLabel("Password : ");
		pfPwd = new JPasswordField(20);
		gbAdd(bPwd, 0, 1, 1, 1);
		gbAdd(pfPwd, 1, 1, 3, 1);

		// 이름
		JLabel bName = new JLabel("Name :");
		tfName = new JTextField(20);
		gbAdd(bName, 0, 2, 1, 1);
		gbAdd(tfName, 1, 2, 3, 1);

		// 성별
		JLabel bGender = new JLabel("Gender : ");
		JPanel pGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbMan = new JRadioButton("Man", true);
		rbWoman = new JRadioButton("Woman", true);
		ButtonGroup group = new ButtonGroup();
		group.add(rbMan);
		group.add(rbWoman);
		pGender.add(rbMan);
		pGender.add(rbWoman);
		gbAdd(bGender, 0, 3, 1, 1);
		gbAdd(pGender, 1, 3, 3, 1);

		// 이메일
		JLabel bEmail = new JLabel("Email : ");
		tfEmail = new JTextField(20);
		gbAdd(bEmail, 0, 4, 1, 1);
		gbAdd(tfEmail, 1, 4, 3, 1);

		// 버튼
		JPanel pButton = new JPanel();
		btnInsert = new JButton("JOIN");
		btnUpdate = new JButton("REVISE");
		btnDelete = new JButton("LEAVE");
		btnCancel = new JButton("CANCEL");
		pButton.add(btnInsert);
		pButton.add(btnUpdate);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 5, 4, 1);

		// 버튼에 감지기를 붙이자
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);

		setSize(300, 250);
		setLocationRelativeTo(null);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //프로그램종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //현재창만 닫는다.

	}// createUI

	// 그리드백레이아웃에 붙이는 메소드
	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);
		add(c, gbc);
	}// gbAdd

	public static void main(String[] args) {
		new MemberProc();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if (obj == btnInsert) {
			insertMember();
			System.out.println("insertMember() 호출 종료");
		} else if (obj == btnCancel) {
			this.dispose(); // 창닫기 (현재창만 닫힘)
			// system.exit(0)=> 내가 띄운 모든 창이 다 닫힘
		} else if (obj == btnUpdate) {
			UpdateMember();
		} else if (obj == btnDelete) {
			// int x = JOptionPane.showConfirmDialog(this,"정말 삭제하시겠습니까?");
			int x = JOptionPane.showConfirmDialog(this, "Do you want to delete it?", "Delete", JOptionPane.YES_NO_OPTION);

			if (x == JOptionPane.OK_OPTION) {
				deleteMember();
			} else {
				JOptionPane.showMessageDialog(this, "Cancel");
			}
		}

		// jTable내용 갱신 메소드 호출
		//Member_List mList = new Member_List();
		//mList.jTableRefresh();

	}// actionPerformed

	private void deleteMember() {
		String id = tfId.getText();
		String pwd = pfPwd.getText();
		if (pwd.length() == 0) { // 길이가 0이면
			JOptionPane.showMessageDialog(this, "You've entered your Password");
			return; // 메소드 끝
		}
		// System.out.println(mList);
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.deleteMember(id, pwd);

		if (ok) {
			JOptionPane.showMessageDialog(this, "Delete Success");
			//mList.jTableRefresh();
			this.dispose();

		} else {
			JOptionPane.showMessageDialog(this, "Delete Failed");

		}

	}// deleteMember

	private void UpdateMember() {

		// 1. 화면의 정보를 얻는다.
		MemberDTO dto = getViewData();
		// 2. 그정보로 DB를 수정
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.updateMember(dto);

		if (ok) {
			JOptionPane.showMessageDialog(this, "Revise");
			//mList.jTableRefresh();
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Revise Failed");
		}
	}

	private void insertMember() {
		// 화면에서 사용자가 입력한 내용을 얻는다.
		MemberDTO dto = getViewData();
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.insertMember(dto);
		
		if (ok) {
			JOptionPane.showMessageDialog(this, "Welcome to Mini Arcade");
			//mList.jTableRefresh();
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Join Fail");
		}

	}// insertMember

	public MemberDTO getViewData() {
		// 화면에서 사용자가 입력한 내용을 얻는다.
		MemberDTO dto = new MemberDTO();
		String id = tfId.getText();
		String pwd = pfPwd.getText();
		String name = tfName.getText();
		String gender = "";
		if (rbMan.isSelected()) {
			gender = "M";
		} else if (rbWoman.isSelected()) {
			gender = "W";
		}

		String email = tfEmail.getText();
		// String intro = taIntro.getText();
		// dto에 담는다.
		dto.setId(id);
		dto.setPwd(pwd);
		dto.setName(Util.toUnicode(name));
		dto.setGender(gender);
		dto.setEmail(email);

		return dto;
	}

}// end
