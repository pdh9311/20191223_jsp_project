package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBCPUtil;
import vo.MembersVO;

public class CommentDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public boolean commentUpload(String member_id, HttpServletRequest request, HttpServletResponse response) {
		
		String sql = "";
		conn = DBCPUtil.getConnection();
		
		String id = member_id;
		int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
		String comment_content = request.getParameter("comment_content");
		
		boolean upload = false;
		
		try {
			// DB에 정보 저장하는 SQL 문
			sql = "INSERT INTO comment VALUES (?, ?, ?, null)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, lecture_num);
			pstmt.setString(3, comment_content);

			int result = pstmt.executeUpdate();
			
			if (result <= 0) {
				upload = false;
			}
			else {
				upload = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBCPUtil.close(pstmt, conn);
		}
		return upload;
	}
	
}
