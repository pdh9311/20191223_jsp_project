package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.CommentDAO;
import dao.LectureRegDAO;
import dao.LectureRegDAOImpl;
import service.LectureRegService;
import service.LectureRegServiceImpl;
import util.DBCPUtil;
import vo.LectureInfoVO;
import vo.LectureRegVO;
import vo.SubjectVO;

@WebServlet("*.lr")
public class LectureRegController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	LectureRegService lrs = new LectureRegServiceImpl();
	RequestDispatcher dispatcher = null;
	LectureRegDAO lrd = new LectureRegDAOImpl();
	CommentDAO cd = new CommentDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getRequestURI().substring(request.getContextPath().length() + 1);
		System.out.println(cmd+"요청");
		String nextPage = null;
		
		if(cmd.equals("lectureSearch.lr")) {
			System.out.println("강의 검색 요청");
			ArrayList<LectureRegVO> searchList = lrs.getSearch(request);
			System.out.println("LectureRegController searchList : " +searchList);
			request.setAttribute("searchList", searchList);
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			nextPage = "/lecture/search.jsp";
		}
		
		if(cmd.equals("detail.lr")) {
			System.out.println("강의 상세보기 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			LectureRegVO lrv = lrs.getlectureReg(request);
			request.setAttribute("lrv", lrv);
			
			ArrayList<LectureInfoVO> liv = lrs.getlectureInfo(request);
			request.setAttribute("liv", liv);
			System.out.println("con"+lrv.getLecture_num());
			int lecture_num = lrv.getLecture_num();
			nextPage = "/lecture/detail.jsp?lecture_num="+lecture_num;
		}
		
		if(cmd.equals("commentWrite.lr")) {
			
			String member_id = request.getParameter("member_id");
			int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
			
			if (cd.commentUpload(member_id, request, response)) {
				request.setAttribute("try", "1");
			} else {
				request.setAttribute("try", "0");
			}
			
			String page = "detail.lr?lecture_num=" + lecture_num + "&page=0";
			System.out.println("page" + page);
			dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}
		
		if(cmd.equals("lectureReg.lr")) {
			System.out.println("강의 등록 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			nextPage = "/lecture/lecture_profile.jsp";
		}
		
		if(cmd.equals("lectureReg1.lr")) {
			System.out.println("강의 등록1 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			int lecture_num = lrs.lectureReg(request);
			String lecture_name = (String)request.getAttribute("lecture_name");
			nextPage = "/lecture/lecture_index.jsp?lecture_num="+lecture_num + "&lecture_name="+lecture_name; 
		}
		
		
		if(cmd.equals("lecture_index.lr")) {
			String page = "lecture/lecture_index.jsp";
			dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}
		
		if(cmd.equals("lecture_index_db.lr")) {
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			String saveDir = "/upload";
			String realPath 
			= request.getSession().getServletContext().getRealPath(saveDir);
			System.out.println(realPath);
			
			File file = new File(realPath);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			MultipartRequest multi = new MultipartRequest(
					request, 
					realPath,
					1024*1024*10,
					"utf-8",
					new DefaultFileRenamePolicy());
		/// file ////////////////////////////////////////////////////////////////

			Enumeration<String> fileNames = multi.getFileNames();
			
			ArrayList<String> list = new ArrayList<>(); 
			
			String origin = "";
			String fileName = "";
			while(fileNames.hasMoreElements()) {
				String name = fileNames.nextElement();
				System.out.println("file name : " + name);
				fileName = multi.getFilesystemName(name);
				System.out.println("upload 된 파일 이름 : " + fileName);
				list.add(fileName);
				origin = multi.getOriginalFileName(name);
				System.out.println("원본 파일 이름 : " + origin);
			}

		/////////////////////////////////// DB work ////////////////////////////////////
			
			Connection conn = null;
			PreparedStatement pstmt = null;
						
			String lecture_name = multi.getParameter("lecture_name");
			String lecture_index = multi.getParameter("lecture_index");
			String lecture_addr = multi.getParameter("lecture_addr");
			String lecture_data = fileName;
			int lecture_num = Integer.parseInt(multi.getParameter("lecture_num"));
			
			conn = DBCPUtil.getConnection();
			
			String sql = "INSERT INTO lecture_info VALUES (null,?,?,?,?,?)";
			
			try {	
				
				conn.setAutoCommit(true);
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1,lecture_num);
				pstmt.setString(2,lecture_name);
				pstmt.setString(3,lecture_index);
				pstmt.setString(4,lecture_addr);
				pstmt.setString(5,lecture_data);

				if(pstmt.executeUpdate() > 0){
					request.setAttribute("try", "1");
					System.out.println("DB success");
				}else{
					request.setAttribute("try", "0");
					System.out.println("DB fail");
				}
				
												
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("try", "0");
				System.out.println("DB fail");
			} finally {
				DBCPUtil.close(pstmt,conn);
				// String page = "lecture/lecture_index.jsp?lecture_num=" + Integer.toString(lecture_num) + "&lecture_name=" + lecture_name;
				String page = "lecture_index.lr?lecture_num=" + Integer.toString(lecture_num) + "&lecture_name=" + lecture_name;
				dispatcher = request.getRequestDispatcher(page);
				dispatcher.forward(request, response);
			}
			
		}
		
		if(cmd.equals("updateLecture.lr")) {
			System.out.println("강의 수정 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			
			LectureRegVO lrv = lrs.getlectureReg(request);
			request.setAttribute("lrv", lrv);
			
			ArrayList<LectureInfoVO> liv = lrs.getlectureInfo(request);
			request.setAttribute("liv", liv);
			System.out.println("con"+lrv.getLecture_num());
			nextPage="/lecture/updateLecture.jsp?lecture_num="+lrv.getLecture_num();
		}
		
		if(cmd.equals("addIndexForm.lr")) {
			System.out.println("강의 목차 추가 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			nextPage = "/lecture/addIndex.jsp";
		}
		
		if(cmd.equals("addIndex.lr")) {
			System.out.println("강의 목차 추가 요청");
			lrs.addLecture(request,response);
		}
		
		if(cmd.equals("updateIndexForm.lr")) {
			System.out.println("강의 목차 수정 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			LectureInfoVO liv = lrs.getLectureIndex(request);
			request.setAttribute("index", liv);
			nextPage = "/lecture/updateIndexForm.jsp";
		}
		
		if(cmd.equals("updateIndex.lr")) {
			System.out.println("강의 목차 수정 요청");
			lrs.updateIndex(request,response);
		}
		
		if(cmd.equals("deleteIndex.lr")) {
			System.out.println("강의 목차 삭제 요청");
			lrs.deleteIndex(request,response);
		}
		
		if(cmd.equals("updateLectureSubmit.lr")) {
			System.out.println("강의 수정 요청");
			lrs.updateLecture(request,response);
			nextPage = "myStudy.me";
		}
		
		if(cmd.equals("deleteLecture.lr")) {
			System.out.println("강의 목차 삭제 요청");
			lrs.deleteLecture(request,response);
		}
		
		if(nextPage != null) {
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
