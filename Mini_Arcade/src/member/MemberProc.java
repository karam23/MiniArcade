/*
 *ȸ�� ���� â�Դϴ�. Login.java���� Join�� �׼� �����ʸ� �ް� ����� ���Ե˴ϴ�.
 *by���
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
	JPasswordField pfPwd; // ��й�ȣ
	JRadioButton rbMan, rbWoman; // ��, ��
	JButton btnInsert, btnCancel, btnUpdate, btnDelete; // ����, ���, ���� , Ż�� ��ư

	GridBagLayout gb;
	GridBagConstraints gbc;
	Member_List mList;

	public MemberProc() { // ���Կ� ������
		createUI(); // UI�ۼ����ִ� �޼ҵ�
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);

	}// ������
/*
	public MemberProc(Login join_btn) { //���Կ� ������
		createUI(); // UI�ۼ����ִ� �޼ҵ�
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
	}// ������
*/
	public MemberProc(Member_List mList) { // ���Կ� ������
		createUI(); // UI�ۼ����ִ� �޼ҵ�
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.mList = mList;
	}// ������

	public MemberProc(String id, Member_List mList) { // ����/������ ������
		createUI();
		btnInsert.setEnabled(false);
		btnInsert.setVisible(false);
		//this.mList = mList;

		System.out.println("id=" + id);

		MemberDAO dao = new MemberDAO();
		MemberDTO vMem = dao.getMemberDTO(id);
		viewData(vMem);

	}// id�� ������ ����

	// MemberDTO �� ȸ�� ������ ������ ȭ�鿡 �������ִ� �޼ҵ�
	private void viewData(MemberDTO vMem) {

		String id = vMem.getId();
		String pwd = vMem.getPwd();
		String name = vMem.getName();
		String gender = vMem.getGender();
		String email = vMem.getEmail();		// ȭ�鿡 ����
		tfId.setText(id);
		tfId.setEditable(false); // ���� �ȵǰ�
		pfPwd.setText(""); // ��й�ȣ�� �Ⱥ����ش�.
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
		this.setTitle("ȸ������");
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2.0;
		gbc.weighty = 2.0;

		// ���̵�
		JLabel bId = new JLabel("���̵� : ");
		tfId = new JTextField(20);
		// �׸���鿡 ���̱�
		gbAdd(bId, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 3, 1);

		// ��й�ȣ
		JLabel bPwd = new JLabel("��й�ȣ : ");
		pfPwd = new JPasswordField(20);
		gbAdd(bPwd, 0, 1, 1, 1);
		gbAdd(pfPwd, 1, 1, 3, 1);

		// �̸�
		JLabel bName = new JLabel("�̸� :");
		tfName = new JTextField(20);
		gbAdd(bName, 0, 2, 1, 1);
		gbAdd(tfName, 1, 2, 3, 1);

		// ����
		JLabel bGender = new JLabel("���� : ");
		JPanel pGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbMan = new JRadioButton("��", true);
		rbWoman = new JRadioButton("��", true);
		ButtonGroup group = new ButtonGroup();
		group.add(rbMan);
		group.add(rbWoman);
		pGender.add(rbMan);
		pGender.add(rbWoman);
		gbAdd(bGender, 0, 3, 1, 1);
		gbAdd(pGender, 1, 3, 3, 1);

		// �̸���
		JLabel bEmail = new JLabel("�̸��� : ");
		tfEmail = new JTextField(20);
		gbAdd(bEmail, 0, 4, 1, 1);
		gbAdd(tfEmail, 1, 4, 3, 1);

		// ��ư
		JPanel pButton = new JPanel();
		btnInsert = new JButton("����");
		btnUpdate = new JButton("����");
		btnDelete = new JButton("Ż��");
		btnCancel = new JButton("���");
		pButton.add(btnInsert);
		pButton.add(btnUpdate);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		gbAdd(pButton, 0, 5, 4, 1);

		// ��ư�� �����⸦ ������
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);

		setSize(300, 250);
		setLocationRelativeTo(null);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //���α׷�����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //����â�� �ݴ´�.

	}// createUI

	// �׸���鷹�̾ƿ��� ���̴� �޼ҵ�
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
			System.out.println("insertMember() ȣ�� ����");
		} else if (obj == btnCancel) {
			this.dispose(); // â�ݱ� (����â�� ����)
			// system.exit(0)=> ���� ��� ��� â�� �� ����
		} else if (obj == btnUpdate) {
			UpdateMember();
		} else if (obj == btnDelete) {
			// int x = JOptionPane.showConfirmDialog(this,"���� �����Ͻðڽ��ϱ�?");
			int x = JOptionPane.showConfirmDialog(this, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);

			if (x == JOptionPane.OK_OPTION) {
				deleteMember();
			} else {
				JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�.");
			}
		}

		// jTable���� ���� �޼ҵ� ȣ��
		//Member_List mList = new Member_List();
		//mList.jTableRefresh();

	}// actionPerformed

	private void deleteMember() {
		String id = tfId.getText();
		String pwd = pfPwd.getText();
		if (pwd.length() == 0) { // ���̰� 0�̸�

			JOptionPane.showMessageDialog(this, "��й�ȣ�� �� �Է��ϼ���!");
			return; // �޼ҵ� ��
		}
		// System.out.println(mList);
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.deleteMember(id, pwd);

		if (ok) {
			JOptionPane.showMessageDialog(this, "�����Ϸ�");
			//mList.jTableRefresh();
			this.dispose();

		} else {
			JOptionPane.showMessageDialog(this, "��������");

		}

	}// deleteMember

	private void UpdateMember() {

		// 1. ȭ���� ������ ��´�.
		MemberDTO dto = getViewData();
		// 2. �������� DB�� ����
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.updateMember(dto);

		if (ok) {
			JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
			//mList.jTableRefresh();
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "��������: ���� Ȯ���ϼ���");
		}
	}

	private void insertMember() {

		// ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
		MemberDTO dto = getViewData();
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.insertMember(dto);

		if (ok) {
			JOptionPane.showMessageDialog(this, "������ �Ϸ�Ǿ����ϴ�.");
			//mList.jTableRefresh();
			dispose();

		} else {

			JOptionPane.showMessageDialog(this, "������ ���������� ó������ �ʾҽ��ϴ�.");
		}

	}// insertMember

	public MemberDTO getViewData() {
		// ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
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
		// dto�� ��´�.
		dto.setId(id);
		dto.setPwd(pwd);
		dto.setName(Util.toUnicode(name));
		dto.setGender(gender);
		dto.setEmail(email);

		return dto;
	}

}// end
