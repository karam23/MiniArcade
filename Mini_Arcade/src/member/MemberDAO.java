/*
 *Member ��Ű���� �ִ� �͵��� DB�� �����ϴ� ���Դϴ�. 
 *�̸� ��Ģ : ���̺��DAO , ���̺��DTO
 *CRUD : Create;insert , Read;Select, Update, delete
 *�ּ� ó�� �Ȱ��� �ʿ� ���� �����͵��Դϴ�. 
 */

package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import game.Util;

//DB ó��
public class MemberDAO {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://rds-mysql.co5xhdkdttkm.ap-northeast-2.rds.amazonaws.com:3306/mini_arcade_db";

	private static final String USER = "useruser"; // DB ID
	private static final String PASS = "123useruser"; // DB �н�����
	Member_List mList;

	public MemberDAO() {
	}

	/*
	 * public MemberDAO(Member_List mList) { this.mList = mList;
	 * System.out.println("DAO=>" + mList); }
	 */
	// DB����
	public Connection getConn() {
		Connection con = null;

		try {
			Class.forName(DRIVER); // 1. ����̹� �ε�
			con = DriverManager.getConnection(URL, USER, PASS); // 2. ����̹� ����

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	/** �ѻ���� ȸ�� ������ ��� �޼ҵ� */
	public boolean getMemberDTO(String id, String pwd) {

		MemberDTO dto = new MemberDTO();

		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from user where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String dbid = rs.getString("id");
				String dbpwd = rs.getString("pwd");
				if (dbid.equals(id)) {
					if (dbpwd.equals(pwd)) {
						return true;
					} else {
						JOptionPane.showMessageDialog(null, "Password is incorrect. Please try again.");
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Plz enter vaild ID.");
					return false;
				}
				/*
				 * dto.setId(rs.getString("id")); dto.setPwd(rs.getString("pwd"));
				 * dto.setName(rs.getString("name")); dto.setGender(rs.getString("gender"));
				 * dto.setEmail(rs.getString("email"));
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public MemberDTO getMemberDTO(String id) {

		MemberDTO dto = new MemberDTO();

		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���

		try {

			con = getConn();
			String sql = "select * from user where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	/** �������Ʈ ��� */
	public Vector getMemberList() {

		Vector data = new Vector(); // Jtable�� ���� ���� �ִ� ��� 1. 2�����迭 2. Vector �� vector�߰�

		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���

		try {
			con = getConn();
			String sql = "select * from user order by score asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String email = rs.getString("email");
				String score = rs.getString("score");

				Vector row = new Vector();
				row.add(id);
				row.add(pwd);
				row.add(name);
				row.add(gender);
				row.add(email);
				row.add(score);
				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList()

	/** ȸ�� ��� */
	public boolean insertMember(MemberDTO dto) {

		boolean ok = false;

		Connection con = null; // ����
		PreparedStatement ps = null; // ���

		try {

			con = getConn();
			String sql = "insert into user(id,pwd,name,gender,email) values(?,?,?,?,?)";
			String strName = Util.toUnicode(dto.getName());
			// String strName = new String(dto.getName().getBytes("8859_1"), "UTF-8");
			// ������ ���� ���߼���!! by���
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPwd());
			ps.setString(3, strName);
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getEmail());

			int r = ps.executeUpdate(); // ���� -> ����
			if (r > 0 && insertScore(dto) == true) {
				System.out.println("���� ����");
				ok = true;
			} else {
				System.out.println("���� ����");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}// insertMmeber

	public boolean insertScore(MemberDTO dto) {

		boolean ok = false;

		Connection con = null; // ����
		PreparedStatement ps1 = null; // ���
		PreparedStatement ps2 = null; // ���
		PreparedStatement ps3 = null; // ���
		PreparedStatement ps4 = null; // ���
		try {

			con = getConn();
			// 01, RockPaperScissors
			String sqlscore1 = "insert into Game_Score(gameID, gameName, UserID, gameScore) values(?, ?, ?, ?)";
			ps1 = con.prepareStatement(sqlscore1);
			ps1.setInt(1, 01);
			ps1.setString(2, "RockPaperScissors");
			ps1.setString(3, dto.getId());
			ps1.setInt(4, 0);
			
			// 02, NumberMole
			String sqlscore2 = "insert into Game_Score(gameID, gameName, UserID, gameScore) values(?, ?, ?, ?)";
			ps2 = con.prepareStatement(sqlscore2);
			ps2.setInt(1, 02);
			ps2.setString(2, "NumberMole");
			ps2.setString(3, dto.getId());
			ps2.setInt(4, 0);
			
			// 03, Snake
			String sqlscore3 = "insert into Game_Score(gameID, gameName, UserID, gameScore) values(?, ?, ?, ?)";
			ps3 = con.prepareStatement(sqlscore3);
			ps3.setInt(1, 03);
			ps3.setString(2, "Snake");
			ps3.setString(3, dto.getId());
			ps3.setInt(4, 0);
			
			// 04, Tetris
			String sqlscore4 = "insert into Game_Score(gameID, gameName, UserID, gameScore) values(?, ?, ?, ?)";
			ps4 = con.prepareStatement(sqlscore4);
			ps4.setInt(1, 04);
			ps4.setString(2, "Tetris");
			ps4.setString(3, dto.getId());
			ps4.setInt(4, 0);

			int r1 = ps1.executeUpdate(); // ���� -> ����
			int r2 = ps2.executeUpdate();
			int r3 = ps3.executeUpdate();
			int r4 = ps4.executeUpdate();
			
			if (r1 > 0 && r2 > 0) {
				if(r3 > 0 && r4 > 0)
					ok = true;
				else
					ok = false;
			} else {
				ok = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;

	}

	/** ȸ������ ���� */
	public boolean updateMember(MemberDTO vMem) {
		// System.out.println("dto=" + vMem.toString());
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {

			con = getConn();
			String sql = "update user set name=?,gender=?,email=? where id=?"; // and pwd=?";

			ps = con.prepareStatement(sql);

			String strName = Util.toUnicode(vMem.getName());
			// �̰͵� ���� sql�̶� ���� ���߼���
			ps.setString(1, strName);
			ps.setString(2, vMem.getGender());
			ps.setString(3, vMem.getEmail());
			ps.setString(4, vMem.getId());
			// ps.setString(5, vMem.getPwd());

			int r = ps.executeUpdate(); // ���� -> ����
			// 1~n: ���� , 0 : ����

			if (r > 0)
				ok = true; // ������ �����Ǹ� ok���� true�� ����

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	/**
	 * ȸ������ ���� : tip: �ǹ������� ȸ�������� Delete ���� �ʰ� Ż�𿩺θ� üũ�Ѵ�.
	 */
	public boolean deleteMember(String id, String pwd) {

		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConn();
			String sql = "delete from user where id=? and pwd=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pwd);
			int r = ps.executeUpdate(); // ���� -> ����

			if (r > 0)
				ok = true; // ������;

		} catch (Exception e) {
			System.out.println(e + "-> �����߻�");
		}
		return ok;
	}

	/** DB������ �ٽ� �ҷ����� */
	public void userSelectAll(DefaultTableModel model) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from user order by name asc"; // name�� score�� �ٲٸ� Leaderboard���� ���� �մϴ�.by���
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			// DefaultTableModel�� �ִ� ������ �����
			for (int i = 0; i < model.getRowCount();) {
				model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5) };

				model.addRow(data);
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelectAll fail");
		} finally {

			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}