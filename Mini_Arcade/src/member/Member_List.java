/*
 * 멤버들의 리스트를 보여주는 창입니다. 후에 Leader board에 활용할 수도 있고
 * root 계정으로 로그인 하면 이 창을 보여주도록 구현해도 될거 같아 남겨둡니다.
 * by 라운
 */

package member;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Member_List extends JFrame implements ActionListener, MouseListener {

	Vector v;
	Vector cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane pane;
	JPanel pbtn;
	JButton btnrefresh;

	public Member_List() {
		super("Administrator LeaderBoard");
		// v=getMemberList();
		// MemberDAO
		MemberDAO dao = new MemberDAO();
		v = dao.getMemberList();
		System.out.println("v=" + v);
		cols = getColumn();

		model = new DefaultTableModel(v, cols);

		jTable = new JTable(model);
		pane = new JScrollPane(jTable);
		add(pane);

		pbtn = new JPanel();
		btnrefresh = new JButton("Refresh");
		pbtn.add(btnrefresh);
		add(pbtn, BorderLayout.NORTH);

		jTable.addMouseListener(this); // 리스너 등록
		btnrefresh.addActionListener(this); // 회원가입버튼 리스너 등록

		setSize(600, 200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end 생성자

	// JTable의 컬럼
	public Vector getColumn() {
		Vector col = new Vector();
		col.add("id");
		col.add("password");
		col.add("name");
		col.add("gender");
		col.add("email");
		col.add("score");

		return col;
	}// getColumn

	// Jtable 내용 갱신 메서드
	public void jTableRefresh() {

		MemberDAO dao = new MemberDAO();
		DefaultTableModel model = new DefaultTableModel(dao.getMemberList(), getColumn());
		jTable.setModel(model);
		jTable.updateUI();
	}

	public static void main(String[] args) {
		new Member_List();
	}// main

	@Override
	public void mouseClicked(MouseEvent e) {
		// mouseClicked 만 사용
		int r = jTable.getSelectedRow();
		String id = (String) jTable.getValueAt(r, 0);
		// System.out.println("id="+id);
		MemberProc mem = new MemberProc(id, this); // 아이디를 인자로 수정창 생성

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 버튼을 클릭하면
		if (e.getSource() == btnrefresh) {
			this.jTableRefresh();
			//new MemberProc(this); //?

			/* 테스트 */
			// dao = new MemberDAO();
			// dao.userSelectAll(model);
			// model.fireTableDataChanged();
			// jTable.updateUI();
			// jTable.requestFocusInWindow();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
