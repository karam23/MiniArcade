/*
 *Member 패키지에 있는 것들을 DB로 연결하는 곳입니다. 
 *이름 규칙 : 테이블명DAO , 테이블명DTO
 *CRUD : Create;insert , Read;Select, Update, delete
 *주석 처리 된것은 필요 없는 데이터들입니다. 
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

//DB 처리
public class MemberDAO {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://rds-mysql.co5xhdkdttkm.ap-northeast-2.rds.amazonaws.com:3306/mini_arcade_db";

	private static final String USER = "useruser"; // DB ID
	private static final String PASS = "123useruser"; // DB 패스워드
	Member_List mList;

	public MemberDAO() {
	}

	/*
	 * public MemberDAO(Member_List mList) { this.mList = mList;
	 * System.out.println("DAO=>" + mList); }
	 */
	// DB연결
	public Connection getConn() {
		Connection con = null;

		try {
			Class.forName(DRIVER); // 1. 드라이버 로딩
			con = DriverManager.getConnection(URL, USER, PASS); // 2. 드라이버 연결

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	/** 한사람의 회원 정보를 얻는 메소드 */
	public boolean getMemberDTO(String id, String pwd) {

		MemberDTO dto = new MemberDTO();

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

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

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

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

	/** 멤버리스트 출력 */
	public Vector getMemberList() {

		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과

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

	/** 회원 등록 */
	public boolean insertMember(MemberDTO dto) {

		boolean ok = false;

		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령

		try {

			con = getConn();
			String sql = "insert into user(id,pwd,name,gender,email) values(?,?,?,?,?)";
			String strName = Util.toUnicode(dto.getName());
			// String strName = new String(dto.getName().getBytes("8859_1"), "UTF-8");
			// 위에랑 순서 맞추세요!! by라운
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPwd());
			ps.setString(3, strName);
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getEmail());

			int r = ps.executeUpdate(); // 실행 -> 저장
			if (r > 0 && insertScore(dto) == true) {
				System.out.println("가입 성공");
				ok = true;
			} else {
				System.out.println("가입 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}// insertMmeber

	public boolean insertScore(MemberDTO dto) {

		boolean ok = false;

		Connection con = null; // 연결
		PreparedStatement ps1 = null; // 명령
		PreparedStatement ps2 = null; // 명령
		PreparedStatement ps3 = null; // 명령
		PreparedStatement ps4 = null; // 명령
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

			int r1 = ps1.executeUpdate(); // 실행 -> 저장
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

	/** 회원정보 수정 */
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
			// 이것도 위에 sql이랑 순서 맞추세요
			ps.setString(1, strName);
			ps.setString(2, vMem.getGender());
			ps.setString(3, vMem.getEmail());
			ps.setString(4, vMem.getId());
			// ps.setString(5, vMem.getPwd());

			int r = ps.executeUpdate(); // 실행 -> 수정
			// 1~n: 성공 , 0 : 실패

			if (r > 0)
				ok = true; // 수정이 성공되면 ok값을 true로 변경

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ok;
	}

	/**
	 * 회원정보 삭제 : tip: 실무에서는 회원정보를 Delete 하지 않고 탈퇴여부만 체크한다.
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
			int r = ps.executeUpdate(); // 실행 -> 삭제

			if (r > 0)
				ok = true; // 삭제됨;

		} catch (Exception e) {
			System.out.println(e + "-> 오류발생");
		}
		return ok;
	}

	/** DB데이터 다시 불러오기 */
	public void userSelectAll(DefaultTableModel model) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select * from user order by name asc"; // name을 score로 바꾸면 Leaderboard구현 가능 합니다.by라운
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			// DefaultTableModel에 있는 데이터 지우기
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