package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBCPUtil {
	
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			DataSource ds 
			= (DataSource)new InitialContext().lookup("java:comp/env/jdbc/MysqlDB");
			conn = ds.getConnection();
		} catch (NamingException e) {
			System.out.println("context 설정정보를 확인할 수 없음");
		} catch (SQLException e) {
			System.out.println("context DB 설정정보 오류");
		}
		return conn;
	}
	
	// db 연결 정보 자원 해제
	public static void close(AutoCloseable closer) {
		try {
			if(closer != null) {
				closer.close();
			}
		} catch (Exception e) {}
	}
	
	public static void close(AutoCloseable... closer) {
		for(AutoCloseable c : closer) {
			try {
				if(c != null) {
					c.close();
				}
			} catch (Exception e) {}
		}
	}
	
	
	
	
	
	

}
