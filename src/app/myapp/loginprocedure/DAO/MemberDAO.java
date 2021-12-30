package app.myapp.loginprocedure.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.myapp.loginprocedure.Util.JDBCUtil;
import app.myapp.loginprocedure.VO.MemberVO;

public class MemberDAO {
	Connection conn = null;
	
	private final String MEMBER_INSERT = "INSERT INTO member (UserId, UserPassword, Name, Birth, Sex, Email) values (?,?,?,?,?,?)";
	private final String MEMBER_UPDATE = "UPDATE member SET UserPassword=?, Name=?, Birth=?, Sex=?, Email=? where UserId=?";
	private final String MEMBER_DELETE = "DELETE FROM member WHERE UserID = ?";
	private final String MEMBER_GETMEMBER = "SELECT * FROM member WHERE UserID = ?";
	private final String MEMBER_GETLIST = "SELECT * FROM member order by UserID desc";
	private final String MEMBER_PASSWORD = "SELECT UserPassword FROM member WHERE UserID = ?";
	
	public MemberDAO() {
		this.conn = JDBCUtil.getConnection();
	}
	
	public void insertMember (MemberVO vo) {
		System.out.println("==> JDBC로 insertMember() 기능 처리");
		try {
			PreparedStatement stmt = conn.prepareStatement(MEMBER_INSERT);
			stmt.setString(1, vo.getUserID());
			stmt.setString(2, vo.getUserPassword());
			stmt.setString(3, vo.getName());
			stmt.setDate(4, vo.getBirth());
			stmt.setString(5, vo.getSex());
			stmt.setString(6, vo.getEmail());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateMember(MemberVO vo) {
		System.out.println("==> JDBC로 updateMember() 기능 처리");
		try {
			PreparedStatement stmt = conn.prepareStatement(MEMBER_UPDATE);
			stmt.setString(1, vo.getUserPassword());
			stmt.setString(2, vo.getName());
			stmt.setDate(3, vo.getBirth());
			stmt.setString(4, vo.getSex());
			stmt.setString(5, vo.getEmail());
			stmt.setString(6, vo.getUserID());
			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMember(MemberVO vo) {
		System.out.println("==> JDBC로 deleteMember() 기능 처리");
		try {
			PreparedStatement stmt = conn.prepareStatement(MEMBER_DELETE);
			stmt.setString(1,vo.getUserID());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public MemberVO getMember (String userName) {
		System.out.println("===> JDBC로 getMember() 기능 처리");
		MemberVO vo = new MemberVO();
		try {
			PreparedStatement stmt = conn.prepareStatement(MEMBER_GETMEMBER);
			stmt.setString(1,userName);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				vo.setUserID(rs.getString("UserID"));
				vo.setUserPassword(rs.getString("UserPassword"));
				vo.setName(rs.getString("Name"));
				vo.setBirth(rs.getDate("Birth"));
				vo.setSex(rs.getString("Sex"));
				vo.setEmail(rs.getString("Email"));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	public List<MemberVO> getBoardList(){
		List<MemberVO> list = new ArrayList<MemberVO>();
		System.out.println("===> JDBC로 getBoardList() 기능 처리");
		try {
			PreparedStatement stmt = conn.prepareStatement(MEMBER_GETLIST);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setUserID(rs.getString("UserID"));
				vo.setUserPassword(rs.getString("UserPassword"));
				vo.setName(rs.getString("Name"));
				vo.setBirth(rs.getDate("Birth"));
				vo.setSex(rs.getString("Sex"));
				vo.setEmail(rs.getString("Email"));
				list.add(vo);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	public String getPassword(String UserId) {
		System.out.println("==> JDBC로 getPassword() 기능 처리");
		try {
			PreparedStatement stmt = conn.prepareStatement(MEMBER_PASSWORD);
			stmt.setString(1, UserId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return rs.getString("UserPassword");
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
} 
