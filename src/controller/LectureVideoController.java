package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LectureRegService;
import service.LectureRegServiceImpl;
import service.VideoService;
import service.VideoServiceImpl;
import vo.LectureInfoVO;
import vo.SubjectVO;

@WebServlet("*.lv")
public class LectureVideoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	VideoService vs = new VideoServiceImpl();
	LectureRegService lrs = new LectureRegServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getRequestURI().substring(request.getContextPath().length() + 1);

		String nextPage = "";

		if (cmd.equals("lectureData.lv")) {
			System.out.println("파일 다운로드 ");
			vs.fileDown(request, response);
		}

		if (cmd.equals("studyRoom.lv")) {
			System.out.println("수강 유무 체크 요청");
			ArrayList<SubjectVO> subject = lrs.getSubject(request);
			request.setAttribute("subject", subject);
			boolean isCheck = vs.checkMyLecture(request);
			System.out.println("con : " + isCheck);
			
			if(isCheck) { 
				System.out.println("이미 신청");
				ArrayList<LectureInfoVO> lectureList = vs.getLecture(request);
				System.out.println("controller Array lectureList: " + lectureList);
				request.setAttribute("lectureList", lectureList); 
				response.setContentType("text/html;charset=utf-8");
				System.out.println("script 시작");
				String s = "";
				PrintWriter out = response.getWriter();
				s += "<script>";
				s += "alert('수강신청 완료되었습니다.');";
				s += "</script>";
				out.print(s);
				System.out.println(s + " -- script 끝");
				nextPage = "/lecture/studyRoom.jsp?num="+request.getParameter("num")+"&lecture_num="+request.getParameter("lecture_num"); 
			}else if(!isCheck){
				System.out.println("처음 수강 신청");
				ArrayList<LectureInfoVO> lectureList = vs.getLecture(request);
				System.out.println("controller Array lectureList: " + lectureList);
				request.setAttribute("lectureList", lectureList); 
				response.setContentType("text/html;charset=utf-8");
				System.out.println("script 시작");
				String s = "";
				PrintWriter out = response.getWriter();
				s += "<script>";
				s += "alert('수강신청 완료되었습니다.');";
				s += "</script>";
				out.print(s);
				System.out.println(s+" -- script 끝");
				nextPage = "/lecture/studyRoom.jsp?num=0&lecture_num="+request.getParameter("lecture_num"); 
			}

		}
		
		if (nextPage != null) {
			request.getRequestDispatcher(nextPage).forward(request, response);
		}

		/*
		 * if(cmd.equals("lecture_data.fd")) { String file_name =
		 * request.getParameter("file_name"); System.out.println("file_name : " +
		 * file_name); String realPath =
		 * request.getServletContext().getRealPath("/upload");
		 * System.out.println("realPath : " + realPath); String filePath =
		 * realPath+File.separator+file_name; System.out.println("filePath : " +
		 * filePath);
		 * 
		 * String mimeType = request.getServletContext().getMimeType(filePath);
		 * System.out.println("mimeType : "+mimeType);
		 * 
		 * // 8비트 바이너리 배열을 의미 if(mimeType == null) { mimeType =
		 * "application/octe-stream"; }
		 * 
		 * response.setContentType(mimeType);
		 * 
		 * String agent = request.getHeader("User-Agent");
		 * System.out.println("요청을 보낸 사용자 브라우저 정보확인 : "+agent);
		 * 
		 * boolean isBrowser = (agent.indexOf("MSIE") > -1 ||
		 * agent.indexOf("Trident")>-1);
		 * 
		 * if(isBrowser) { file_name =
		 * URLEncoder.encode(file_name,"UTF-8").replaceAll("\\+","%20");
		 * 
		 * }else { file_name = new String(file_name.getBytes("UTF-8"),"ISO-8859-1"); }
		 * 
		 * System.out.println(file_name);
		 * 
		 * response.setHeader("Content-Disposition","attachment;fileName="+file_name);
		 * 
		 * FileInputStream fis = new FileInputStream(filePath); OutputStream out =
		 * response.getOutputStream(); int numRead = 0;
		 * 
		 * byte[] bytes = new byte[4096]; while((numRead =
		 * fis.read(bytes,0,bytes.length)) != -1 ) { out.write(bytes,0,numRead); }
		 * out.flush(); out.close(); fis.close();
		 * 
		 * }
		 */

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
