package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.MembersVO;

public interface MembersService {

		// 회원 가입
		void memberJoin(HttpServletRequest request, HttpServletResponse response);
		
		// 회원 로그인
		void memberLogin(HttpServletRequest request, HttpServletResponse response);
		
		// 회원 정보 수정
		MembersVO memberUpdate(HttpServletRequest request,HttpServletResponse response);
		
		// 로그아웃 처리
		void memberLogout(HttpServletRequest request, HttpServletResponse response);
		
		// 강사 약력 저장
		void teacherJoin(HttpServletRequest request, HttpServletResponse response);
		
		// 회원 탈퇴
		void withdrawSubmit(HttpServletRequest request, HttpServletResponse response);
		
}
