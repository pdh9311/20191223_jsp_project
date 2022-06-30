package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import util.DBCPUtil;
import vo.LectureInfoVO;
import vo.LectureRegVO;
import vo.SubjectVO;

public class LectureRegDAOImpl implements LectureRegDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	@Override
	public int lectureReg(LectureRegVO lecReg, HttpServletRequest request) {

		String lecture_name = (String) request.getAttribute("lecture_name");
		request.setAttribute("lecture_name", lecture_name);

		conn = DBCPUtil.getConnection();
		int lecture_num = 0;
		String sql = "INSERT INTO lecture VALUES(null,?,?,?,?,?,?,0)";

		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lecReg.getTeacher_id());
			pstmt.setString(2, lecReg.getCategory());
			pstmt.setString(3, lecReg.getLecture_name());
			pstmt.setString(4, lecReg.getPre_subject());
			pstmt.setString(5, lecReg.getIntro());
			pstmt.setString(6, lecReg.getThumbnail());
			pstmt.executeUpdate();

			sql = "SELECT LAST_INSERT_ID()";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				lecture_num = rs.getInt(1);
			System.out.println("lecture_num : " + lecture_num);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return lecture_num;
	}

	// 강의 목차 요청
	@Override
	public ArrayList<LectureInfoVO> getLectureList(int lecture_num) {
		ArrayList<LectureInfoVO> lectureList = new ArrayList<>();
		System.out.println("leture_num : " + lecture_num);
		String sql = "SELECT * FROM lecture_info WHERE lecture_num = ? ORDER BY info_num ASC";

		conn = DBCPUtil.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lecture_num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				LectureInfoVO lecInfo = new LectureInfoVO();
				lecInfo.setLecture_num(rs.getInt("lecture_num"));
				lecInfo.setLecture_name(rs.getString("lecture_name"));
				lecInfo.setLecture_index(rs.getString("lecture_index"));
				lecInfo.setLecture_addr(rs.getString("lecture_addr"));
				lecInfo.setLecture_data(rs.getString("lecture_data"));
				lectureList.add(lecInfo);
				System.out.println("lecInfo : " + lecInfo);
				System.out.println("DAO Array LectureList : " + lectureList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return lectureList;
	}

	// 강의 상세정보 가져오기
	public LectureRegVO getlectureRegVO(int lecture_num) {
		LectureRegVO lrv = null;
		System.out.println("leture_num : " + lecture_num);
		conn = DBCPUtil.getConnection();
		String sql = "SELECT * FROM lecture WHERE lecture_num = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lecture_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				lrv = new LectureRegVO();
				lrv.setLecture_num(rs.getInt(1));
				lrv.setTeacher_id(rs.getString(2));
				lrv.setCategory(rs.getString(3));
				lrv.setLecture_name(rs.getString(4));
				lrv.setPre_subject(rs.getString(5));
				lrv.setIntro(rs.getString(6));
				lrv.setThumbnail(rs.getString(7));
				lrv.setReg_count(rs.getInt(8));
			}
			System.out.println("db lrv : " + lrv);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return lrv;
	}

	// 수강 유무 체크 후 수강신청
	@Override
	public boolean joinMyLecture(String member_id, int lecture_num, String lecture_name) {
		boolean isCheck = false;
		conn = DBCPUtil.getConnection();

		try {
			System.out.println("DAO JOIN MY LECTURE 시작");
			String sql = "SELECT * FROM my_lecture WHERE member_id = ? "
					+ " AND lecture_name = (SELECT lecture_name FROM lecture WHERE lecture_num = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setInt(2, lecture_num);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				sql = "UPDATE lecture SET reg_count = reg_count + 1 WHERE lecture_num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, lecture_num);
				pstmt.executeUpdate();
				sql = "INSERT INTO my_lecture VALUES(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member_id);
				pstmt.setString(2, lecture_name);
				if (pstmt.executeUpdate() > 0)
					isCheck = false;
			} else {
				isCheck = true;
			}
		} catch (SQLException e) {
			System.out.println("JOIN MY LECTURE ERROR : " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return isCheck;
	}

	// DB에 저장된 과목정보 찾기
	@Override
	public ArrayList<SubjectVO> getSubject() {
		ArrayList<SubjectVO> subject = new ArrayList<>();

		String sql = "SELECT subject_name FROM subject";

		conn = DBCPUtil.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectVO sj = new SubjectVO();
				sj.setSubject_name(rs.getString("subject_name"));
				subject.add(sj);
				System.out.println("sj : " + sj);
				System.out.println("DAO Array LectureList : " + subject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}

		return subject;
	}
	
	// 강의 목차 추가
	@Override
	public void addIndex(LectureInfoVO liv) {
		
		String sql = "INSERT INTO lecture_info VALUES(null,?,?,?,?,?)";
		conn = DBCPUtil.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, liv.getLecture_num());
			pstmt.setString(2, liv.getLecture_name());
			pstmt.setString(3, liv.getLecture_index());
			pstmt.setString(4, liv.getLecture_addr());
			pstmt.setString(5, liv.getLecture_data());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(pstmt,conn);
		}
	}

	// 검색 결과에 포함된 강의 리스트
	@Override
	public ArrayList<LectureRegVO> getSearchList(String searchName, String searchValue,int nextNum) {
		System.out.println("LectureRegDAO searchName : " + searchName);
		System.out.println("LectureRegDAO searchValue : " + searchValue);
		ArrayList<LectureRegVO> searchList = new ArrayList<>();
		conn = DBCPUtil.getConnection();
		String sql = "SELECT * FROM lecture";
		try {

			if (searchName.equals("lecture_name")) {
				sql += " WHERE lecture_name LIKE CONCAT('%',?,'%') limit 0,?";
			} else {
				sql += " WHERE category = ? limit 0,? ";
			}
			pstmt = conn.prepareStatement(sql);
			System.out.println("sql문 : " + sql);
			pstmt.setString(1, searchValue);
			pstmt.setInt(2, 4*nextNum+4);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				LectureRegVO lecSearch = new LectureRegVO();
				lecSearch.setLecture_num(rs.getInt("lecture_num"));
				lecSearch.setTeacher_id(rs.getString("teacher_id"));
				lecSearch.setCategory(rs.getString("category"));
				lecSearch.setLecture_name(rs.getString("lecture_name"));
				lecSearch.setPre_subject(rs.getString("pre_subject"));
				lecSearch.setIntro(rs.getString("intro"));
				lecSearch.setThumbnail(rs.getString("thumbnail"));
				lecSearch.setReg_count(rs.getInt("reg_count"));
				searchList.add(lecSearch);
			}
			System.out.println("LectureRegDAOImpl searchList : " + searchList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		return searchList;
	}
	
	// 강의 목차 가져오기
	@Override
	public LectureInfoVO getLectureIndex(int lecture_num, String lecture_name) {

		LectureInfoVO liv = null;
		String sql = "SELECT * FROM lecture_info WHERE lecture_num = ? AND lecture_index = ?";
		conn = DBCPUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lecture_num);
			pstmt.setString(2, lecture_name);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				liv = new LectureInfoVO();
				liv.setInfo_num(rs.getInt("info_num"));
				liv.setLecture_num(rs.getInt("lecture_num"));
				liv.setLecture_name(rs.getString("lecture_name"));
				liv.setLecture_index(rs.getString("lecture_index"));
				liv.setLecture_addr(rs.getString("lecture_addr"));
				liv.setLecture_data(rs.getString("lecture_data"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs,pstmt,conn);
		}
		return liv;
	}
	
	//강의 목차 수정하기
	@Override
	public void updateIndex(LectureInfoVO liv) {
		System.out.println("dao info : " + liv.getInfo_num());
		String sql = "UPDATE lecture_info "
				   + " SET lecture_index = ?, "
				   + " lecture_addr = ?, "
				   + " lecture_data = ? "
				   + " WHERE info_num = ?";
		conn = DBCPUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, liv.getLecture_index());
			pstmt.setString(2, liv.getLecture_addr());
			pstmt.setString(3, liv.getLecture_data());
			pstmt.setInt(4, liv.getInfo_num());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 강의 목차 삭제
	@Override
	public void deleteIndex(int lecture_num, String lecture_index) {
		
		String sql = "DELETE FROM lecture_info "
				   + " WHERE lecture_num = ? "
				   + " AND lecture_index = ?";
		conn = DBCPUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lecture_num);
			pstmt.setString(2, lecture_index);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 강의 수정
	@Override
	public void updateLecture(LectureRegVO lecReg) {
		
		String sql = "UPDATE lecture "
				   + " SET category = ?, "
				   + " lecture_name = ?, "
				   + " pre_subject = ?, "
				   + " intro = ?, "
				   + " thumbnail = ? "
				   + " WHERE lecture_num = ?";
		conn = DBCPUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lecReg.getCategory());
			pstmt.setString(2, lecReg.getLecture_name());
			pstmt.setString(3, lecReg.getPre_subject());
			pstmt.setString(4, lecReg.getIntro());
			pstmt.setString(5, lecReg.getThumbnail());
			pstmt.setInt(6, lecReg.getLecture_num());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteLecture(int lecture_num, String lecture_name) {
		conn = DBCPUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			String sql = "DELETE FROM lecture WHERE lecture_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lecture_num);
			pstmt.executeUpdate();
			sql = "DELETE FROM lecture_info WHERE lecture_num = ? AND lecture_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lecture_num);
			pstmt.setString(2, lecture_name);
			pstmt.executeUpdate();
			sql = "DELETE FROM my_lecture WHERE lecture_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lecture_name);
			pstmt.executeUpdate();
			sql = "DELETE FROM comment WHERE lecture_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lecture_num);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
}