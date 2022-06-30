package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBCPUtil;
import vo.CareerVO;
import vo.MembersVO;
import vo.TeacherVO;

public class MembersDAOImpl implements MembersDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	@Override
	public boolean memberJoin(MembersVO member) {
		conn = DBCPUtil.getConnection();
		boolean isJoin = false;
		try {
			// 아이디 중복체크 SQL 문
			String sql = "SELECT * FROM members WHERE member_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMember_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				isJoin = false;
				return isJoin;
			}
				

			// DB에 정보 저장하는 SQL 문
			sql = "INSERT INTO members "
				+ " VALUES(?,?,?,?,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMember_id());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getTs_check());
			pstmt.setString(5, member.getBirth());
			pstmt.setString(6, member.getHope_sub());
			pstmt.setString(7, member.getProfile_img());

			int result = pstmt.executeUpdate();
			if (result <= 0) {
				isJoin = false;
				return isJoin;
			}
			else {
				isJoin = true;
			}
			
		} catch (SQLException e) {
			System.out.println("회원 가입 실패 : " + e.getMessage());
			return isJoin = false;
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return isJoin;
	}
		
	@Override
	public boolean teacherJoin(CareerVO cv) {
		
		boolean isSuccess = false;
		
		String sql = "INSERT INTO teacher VALUES(?,?,?,?)";
		conn = DBCPUtil.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cv.getTeacher_id());
			pstmt.setString(2, cv.getName());
			pstmt.setString(3, cv.getCareer());
			pstmt.setString(4, cv.getSubject());
			if(pstmt.executeUpdate() > 0) isSuccess = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(pstmt,conn);
		}
		
		return isSuccess;
	}

	@Override
	public MembersVO memberLogin(String member_id, String password) {

		MembersVO member = null;

		conn = DBCPUtil.getConnection();

		try {
			String sql = "SELECT * FROM members WHERE member_id = ? AND password = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if(rs.next()) { 
				member = new MembersVO( 
						rs.getString("member_id"), 
						rs.getString("name"),
						rs.getString("ts_check"),
						rs.getString("birth"),
						rs.getString("hope_sub"),
						rs.getString("profile_img")
						); 
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return member;
	}
	
	// 강사 정보
	@Override
	public TeacherVO getTeacherInfo(String member_id) {
		TeacherVO teacher = null;
		
		String sql = "SELECT * FROM teacher WHERE teacher_id = ?";
		conn = DBCPUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				teacher = new TeacherVO();
				teacher.setTeacher_id(rs.getString("teacher_id"));
				teacher.setName(rs.getString("name"));
				teacher.setCareer(rs.getString("career"));
				teacher.setSubject(rs.getString("subject"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}

	@Override
	public MembersVO getMemberById(String member_id) {
		
		MembersVO member = null;

		conn = DBCPUtil.getConnection();

		try {
			String sql = "SELECT * FROM members WHERE member_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			if(rs.next()) { 
				member = new MembersVO( 
						rs.getString("member_id"), 
						rs.getString("name"),
						rs.getString("ts_check"),
						rs.getString("brith"),
						rs.getString("hope_sub"),
						rs.getString("profile_img") 
						); 
//				System.out.println("getMemberByID : "+ member);
			}
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return member;
	}
	
	@Override
	public boolean memberUpdate(MembersVO member) {

		boolean isUpdate = false;

		conn = DBCPUtil.getConnection();

		String sql = "UPDATE members SET " 
				   + " password = ?, " 
				   + " ts_check = ?, " 
				   + " profile_img = ?, "
				   + " hope_sub = ? "
				   + " WHERE member_id = ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getTs_check());
			pstmt.setString(3, member.getProfile_img());
			pstmt.setString(4, member.getHope_sub());
			pstmt.setString(5, member.getMember_id());
			
			int result = pstmt.executeUpdate();

			if (result > 0)isUpdate = true;
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(pstmt, conn);
		}
		return isUpdate;
	}

	@Override
	public boolean memberUpdate(MembersVO member, String subject, String career) {

		boolean isUpdate = false;

		conn = DBCPUtil.getConnection();

		String sql = "UPDATE members SET " 
				   + " password = ?, " 
				   + " ts_check = ?, " 
				   + " profile_img = ?, "
				   + " hope_sub = ? "
				   + " WHERE member_id = ? ";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getTs_check());
			pstmt.setString(3, member.getProfile_img());
			pstmt.setString(4, member.getHope_sub());
			pstmt.setString(5, member.getMember_id());
			
			int result = pstmt.executeUpdate();

			if (result > 0) {
				sql = "UPDATE teacher SET "
					+ " subject = ?, "
					+ " career = ? "
					+ " WHERE teacher_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, subject);
				pstmt.setString(2, career);
				pstmt.setString(3, member.getMember_id());
				pstmt.executeUpdate();
				isUpdate = true;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(pstmt, conn);
		}
		return isUpdate;
	}
	// 회원탈퇴
		@Override
		public boolean deleteMember(String member_id) {
			
			boolean isDeleteMember = false;
			conn = DBCPUtil.getConnection();
			String sql;
			try {
				conn.setAutoCommit(false);
				sql= "UPDATE lecture SET reg_count = reg_count - 1 WHERE lecture_name IN (SELECT lecture_name FROM my_lecture WHERE member_id = ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member_id);
				pstmt.executeUpdate();
				sql = "DELETE FROM my_lecture WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,member_id);
				pstmt.executeUpdate();
				sql = "DELETE FROM comment WHERE writer_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,member_id);
				pstmt.executeUpdate();
				sql = "DELETE FROM lecture_info WHERE lecture_num IN (SELECT lecture_num FROM lecture WHERE teacher_id = ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,member_id);
				pstmt.executeUpdate();
				sql = "DELETE FROM lecture WHERE teacher_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,member_id);
				pstmt.executeUpdate();
				sql = "DELETE FROM teacher WHERE teacher_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,member_id);
				pstmt.executeUpdate();
				sql = "DELETE FROM members WHERE member_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member_id);
				pstmt.executeUpdate();
				conn.commit();
				isDeleteMember = true;
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {}
				isDeleteMember = false;
			} finally {
				DBCPUtil.close(pstmt,conn);
			}
			return isDeleteMember;
		}

}