package app.myapp.loginprocedure.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	
	private static Connection conn = null;
	
	public static void closeConnection() {
		System.out.println("==> JDBC로 closeConnection() 기능 처리");
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		System.out.println("==> JDBC로 getConnection() 기능 처리");
		try {
			//SQLite JDBC 체크 
			Class.forName("org.sqlite.JDBC");
			
			//SQLite 데이터베이스 파일에 연결
			String dbFile = "/Users/hongseokchan/Documents/2021-winter/LoginProcedureDB.db";
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) { 
			System.out.println("DB 접속 실패 :" + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unknown error");
			e.printStackTrace();
		}
		return conn;
	}
	
//	public static void main(String ars[]) {
//		Connection conn = getConnection();
//		if(conn != null)
//			System.out.println("DB 연결됨!");
//		else
//			System.out.println("DB 연결중 오류 !");
//	}
}
