package service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.MembersDAO;
import dao.MembersDAOImpl;
import vo.CareerVO;
import vo.MembersVO;
import vo.TeacherVO;

public class MembersServiceImpl implements MembersService{
	
	MembersDAO dao = new MembersDAOImpl();

	@Override
	public void memberJoin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("memberJoin요청");
		try {
			String saveDir = "/upload";
			String realPath 
			= request.getSession().getServletContext().getRealPath(saveDir);
			System.out.println(realPath);
			
			MultipartRequest multi = new MultipartRequest(
					request, 
					realPath,
					1024*1024*10,
					"utf-8",
					new DefaultFileRenamePolicy());
			
			MembersVO mv = new MembersVO();
			String file = (String)multi.getFileNames().nextElement();
			System.out.println(file);
			String profile_file = multi.getFilesystemName(file);
			System.out.println(profile_file);
			String profile_img = multi.getOriginalFileName(file);
			System.out.println(profile_img);
			String member_id = multi.getParameter("member_id");
			String name = multi.getParameter("name");
			String password = multi.getParameter("password");
			String ts_check = multi.getParameter("ts_check");
			String birth = multi.getParameter("birth");
			
			String[] hope = multi.getParameterValues("hope_sub");
			String split = "";
			for(String array : hope) {
				split += array + ",";
			}
			System.out.println("split : " + split);
			
			mv.setProfile_img(profile_file);
			mv.setMember_id(member_id);
			mv.setName(name);
			mv.setPassword(password);
			mv.setTs_check(ts_check);
			mv.setBirth(birth);
			mv.setHope_sub(split);
			System.out.println("members : " + mv);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			if(ts_check.equals("강사")) {
				if(dao.memberJoin(mv)) {
					System.out.println("강사 가입");
					request.setAttribute("members", mv);
					request.getRequestDispatcher("/members/teacherJoin.jsp").forward(request, response);
				}else if(!dao.memberJoin(mv)){
					System.out.println("강사 가입 실패");
					out.print("alert('아이디 중복');");
					out.print("history.go(-1);");
				}
				
			}else {
				if(dao.memberJoin(mv)) {
					System.out.println("학생가입");
					out.print("location.href='login.me'");
				}else if(!dao.memberJoin(mv)) {
					out.print("alert('아이디 중복');");
					out.print("history.go(-1);");
				}
				
			}
			out.print("</script>");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("memberJoin 종료");
		}
	}
	
	@Override
	public void teacherJoin(HttpServletRequest request, HttpServletResponse response) {
		try {
			CareerVO cv = new CareerVO();
			cv.setTeacher_id(request.getParameter("teacher_id"));
			cv.setName(request.getParameter("name"));
			cv.setCareer(request.getParameter("career"));
			cv.setSubject(request.getParameter("subject"));
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			if(dao.teacherJoin(cv)) {
				out.print("location.href='login.me'");
			}else {
				out.print("alert('가입실패!!!')");
				out.print("history.go(-1)");
			}
			out.print("</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void memberLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String member_id = request.getParameter("member_id");
		String password = request.getParameter("password");
		MembersVO member = dao.memberLogin(member_id, password);
		TeacherVO teacher = dao.getTeacherInfo(member_id);
		System.out.println("t : " + teacher);
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			if(member != null) {
				System.out.println(member);
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				session.setAttribute("teacher", teacher);
				out.print("<script>");
				out.print("location.href='main.me';");
				out.print("</script>");
			}else {
				out.print("<script>");
				out.print("alert('아이디 또는 비밀번호가 틀립니다.');");
				out.print("history.back();");
				out.print("</script>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public MembersVO memberUpdate(HttpServletRequest request,HttpServletResponse response) {
		
		MembersVO mv = null;
		
		try {
			
			String saveDir = "/upload";
			String realPath 
			= request.getSession().getServletContext().getRealPath(saveDir);
			System.out.println(realPath);
			
			MultipartRequest multi = new MultipartRequest(
					request, 
					realPath,
					1024*1024*10,
					"utf-8",
					new DefaultFileRenamePolicy());
			
			mv = new MembersVO();
			String file = (String)multi.getFileNames().nextElement();
			System.out.println(file);
			String profile_file = multi.getFilesystemName(file);
			System.out.println(profile_file);
			String profile_img = multi.getOriginalFileName(file);
			System.out.println(profile_img);
			String member_id = multi.getParameter("member_id");
			System.out.println("member_id" + member_id);
			String name = multi.getParameter("name");
			System.out.println("name" + name);
			String password = multi.getParameter("password");
			System.out.println("pass" + password);
			String ts_check = multi.getParameter("ts_check");
			System.out.println("ts_check" + ts_check);
			String birth = multi.getParameter("birth");
			System.out.println("birth" + birth);
			String profile = multi.getParameter("profile_img");
			System.out.println("profile" + profile);
			String[] hope = multi.getParameterValues("hope_sub");
			String split = "";
			for(String array : hope) {
				split += array + ",";
			}
			System.out.println("split : " + split);
			if(profile_file == null) {
				profile_file = profile;
			}else  if(profile_file != null) {
				File f = new File(realPath+"\\"+profile);
				System.out.println("deleteImage : " + realPath+"\\"+profile);
				f.delete();
				System.out.println("삭제 성공");
			}
			System.out.println("profile_file : " + profile_file);
			mv.setProfile_img(profile_file);
			mv.setMember_id(member_id);
			mv.setName(name);
			mv.setPassword(password);
			mv.setTs_check(ts_check);
			mv.setBirth(birth);
			mv.setHope_sub(split);
			System.out.println("members : " + mv);
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			if(ts_check.equals("강사")) {
				System.out.println("eqauals 강사수정 요청");
				String subject = multi.getParameter("subject");
				System.out.println("sub : " + subject);
				String career = multi.getParameter("career");
				System.out.println("car : " + career);
				if(dao.memberUpdate(mv,subject,career)) {
					out.print("<script>");
					out.print("alert('다시 로그인 해주세요.');");
					out.print("location.href='login.me';");
					out.print("</script>");
					HttpSession session = request.getSession();
					session.invalidate();
				}
			}else {
				if(dao.memberUpdate(mv)) {
					System.out.println("학생");
					out.print("<script>");
					out.print("alert('다시 로그인 해주세요.');");
					out.print("location.href='login.me';");
					out.print("</script>");
					HttpSession session = request.getSession();
					session.invalidate();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mv;
	}

	
	@Override
	public void memberLogout(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("session 초기화");
		
	}
	

	//회원탈퇴
	@Override
	public void withdrawSubmit(HttpServletRequest request, HttpServletResponse response) {
		boolean isCheck = false;
		String tempPass = request.getParameter("tempPass");
		System.out.println("temp pass : " + tempPass);
		
		MembersVO members = (MembersVO) request.getSession().getAttribute("member");
		
		response.setContentType("text/html;charset=utf-8");

		System.out.println("members : " + members);
		System.out.println("members_getMember_id: " + members.getMember_id());
		try {
			PrintWriter writer = response.getWriter();
			writer.print("<script>");
			if(members != null && tempPass.equals(tempPass)) {
				isCheck = dao.deleteMember(members.getMember_id());
				if(isCheck == true) {
					// 회원탈퇴 완료 시
					// Session 정보 삭제  - Cookie 정보 삭제
					memberLogout(request,response);
					
					writer.print("alert('회원탈퇴 완료');");
					writer.print("location.href='main.me';");
				}else {
					// 회원 정보 불일치 회원 탈퇴 실패
					writer.print("alert('회원탈퇴 실패');");
					writer.print("history.go(-1);");
				}
			} else {
				// 회원 정보 불일치 회원 탈퇴 실패
				writer.print("alert('회원탈퇴 실패');");
				writer.print("history.go(-1);");
			}
			writer.print("</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
