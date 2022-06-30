package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LectureRegService;
import service.LectureRegServiceImpl;
import service.MembersService;
import service.MembersServiceImpl;
import vo.MembersVO;
import vo.SubjectVO;

@WebServlet("*.me")
public class MembersController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	MembersService ms = new MembersServiceImpl();
	LectureRegService lrs = new LectureRegServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getRequestURI().substring(request.getContextPath().length()+1);
		System.out.println(cmd + "요청 들어옴");
		String nextPage = null;
		
		if(cmd.equals("main.me")) {
			System.out.println("MAIN PAGE 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			nextPage = "/common/main.jsp";
		}
		
		if(cmd.equals("join.me")) {
			System.out.println("JOIN PAGE 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			nextPage = "/members/join.jsp";
		}
		
		if(cmd.equals("joinSubmit.me")) {
			System.out.println("가입 요청");
			ms.memberJoin(request, response);
		}
		
		if(cmd.equals("teacherJoinSubmit.me")) {
			System.out.println("강사 약력 저장 요청");
			ms.teacherJoin(request, response);
		}
		
		if(cmd.equals("login.me")) {
			System.out.println("LOGIN PAGE 요청");
			nextPage = "/members/login.jsp";
		}
		
		if(cmd.equals("loginSubmit.me")) {
			System.out.println("로그인 요청");
			ms.memberLogin(request, response);
		}
		
		if(cmd.equals("studyRoom.me")) {
			System.out.println("강의실 화면 요청");
			nextPage = "/lecture/studyRoom.jsp";
		}
		
		if(cmd.equals("myStudy.me")) {
			System.out.println("내 강의실 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			nextPage = "/lecture/myStudy.jsp";
		}
		
		if(cmd.equals("myInfo.me")) {
			System.out.println("회원 정보 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			nextPage = "/members/myInfo.jsp";
		}
		
		if(cmd.equals("update.me")) {
			System.out.println("회원 정보 수정 화면 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			request.setAttribute("sub", request.getParameter("subject"));
			request.setAttribute("career", request.getParameter("career"));
			
			nextPage = "/members/update.jsp";
		}
		
		if(cmd.equals("updateSubmit.me")) {
			System.out.println("회원 정보 수정 요청");
			MembersVO member = ms.memberUpdate(request,response);
			System.out.println("Controller : " + member);
			
		}
		
		if(cmd.equals("logOut.me")) {
			ms.memberLogout(request,response);
			nextPage = "/common/main.jsp";
		}
		
		
		if(cmd.equals("withdraw.me")) {
			System.out.println("회원탈퇴 비밀번호 확인창 요청");
			nextPage="/members/withdraw.jsp";
		}
		
		if(cmd.equals("withdrawSubmit.me")) {
			System.out.println("회원탈퇴 요청");
			ms.withdrawSubmit(request,response);
		}
		
		
		if(nextPage != null) {
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	
	
}
