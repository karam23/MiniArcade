/*
 * ������� ����Ʈ�� �����ִ� â�Դϴ�. �Ŀ� Leader board�� Ȱ���� ���� �ְ�
 * root �������� �α��� �ϸ� �� â�� �����ֵ��� �����ص� �ɰ� ���� ���ܵӴϴ�.
 * by ���
 */

package member;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

import game.gameLeaderboard;

public class Member_List extends JFrame implements ActionListener, MouseListener {

	Vector v;
	Vector cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane pane;
	JPanel pbtn;
	JButton btnrefresh, btnRank1,btnRank2,btnRank3, btnRank4;

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

		pbtn = new JPanel(new GridLayout(1, 5));
		btnrefresh = new JButton("Refresh");
		btnRank1 = new JButton("RPS Rank");
		btnRank2 = new JButton("NUM Rank");
		btnRank3 = new JButton("SNAKE Rank");
		btnRank4 = new JButton("GUESSING Rank");
		
		pbtn.add(btnrefresh);
		pbtn.add(btnRank1);
		pbtn.add(btnRank2);
		pbtn.add(btnRank3);
		pbtn.add(btnRank4);
		
		add(pbtn, BorderLayout.NORTH);

		jTable.addMouseListener(this); // ������ ���
		btnrefresh.addActionListener(this); // ȸ�����Թ�ư ������ ���
		btnRank1.addActionListener(this);
		btnRank2.addActionListener(this);
		btnRank3.addActionListener(this);
		btnRank4.addActionListener(this);

		setSize(800, 200);
		this.setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// end ������

	// JTable�� �÷�
	public Vector getColumn() {
		Vector col = new Vector();
		col.add("id");
		col.add("password");
		col.add("name");
		col.add("gender");
		col.add("email");
		//col.add("score");

		return col;
	}// getColumn

	// Jtable ���� ���� �޼���
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
		// mouseClicked �� ���
		int r = jTable.getSelectedRow();
		String id = (String) jTable.getValueAt(r, 0);
		// System.out.println("id="+id);
		MemberProc mem = new MemberProc(id, this); // ���̵� ���ڷ� ����â ����

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if (obj == btnrefresh) {
			this.jTableRefresh();
		} else if(obj == btnRank1) {
			new gameLeaderboard(1);
		}else if(obj == btnRank2) {
			new gameLeaderboard(2);
		}else if(obj == btnRank3) {
			new gameLeaderboard(3);
		}else if(obj == btnRank4) {
			new gameLeaderboard(4);
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
