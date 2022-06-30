package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LectureRegDAO;
import dao.LectureRegDAOImpl;
import vo.LectureInfoVO;

public class VideoServiceImpl implements VideoService {
	
	LectureRegDAO dao = new LectureRegDAOImpl();
	
	// 강의자료 다운로드 처리
	@Override
	public void fileDown(HttpServletRequest request, HttpServletResponse response) {
		String realPath = request.getServletContext().getRealPath("/upload");
		System.out.println("realPath : " + realPath);
		
		String file_name = request.getParameter("file_name");
		System.out.println("file_name : " +file_name);
		
		String filePath = realPath+File.separator+file_name;
		System.out.println("filePath : " + filePath);
		
		String mimeType = request.getServletContext().getMimeType(filePath);
		System.out.println("mimeType : " + mimeType);
		
		if(mimeType == null) mimeType="application/octet-stream";
		response.setContentType(mimeType);
		
		try {			
			String agent = request.getHeader("User-Agent");
			System.out.println("요청을 보낸 사용자 브라우저 정보 확인 : " + agent);
			if(agent.indexOf("MSIE") > -1 || agent.indexOf("Trident") > -1) {
				file_name = URLEncoder.encode(file_name,"UTF-8").replaceAll("\\+", "%20");
			}else {
				file_name = new String(file_name.getBytes("UTF-8"),"ISO-8859-1");
			}
			System.out.println("file_name : " + file_name);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		response.setHeader("Content-Disposition","attachment;fileName="+file_name);
		
		try {
			FileInputStream fis = new FileInputStream(filePath);
			OutputStream os = response.getOutputStream();
			
			byte[] bytes = new byte[4096];
			int numRead = 0;
			while((numRead = fis.read(bytes,0, bytes.length)) != -1) {
				os.write(bytes,0,numRead);
			}
			os.flush();
			os.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 전체 강의 목차 검색
	@Override
	public ArrayList<LectureInfoVO> getLecture(HttpServletRequest request) {
		int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
		//int lecture_num = 1;
		ArrayList<LectureInfoVO> lectureList = new ArrayList<>();
		lectureList = dao.getLectureList(lecture_num);
		System.out.println("Service Array lectureList : "+lectureList);
		return lectureList;
	}

	// 수강 신청 유무 확인
	@Override
	public boolean checkMyLecture(HttpServletRequest request) {
		boolean isCheck = false;
		System.out.println("체크요청");
		System.out.println("check1 : "+ request.getParameter("member_id"));
		System.out.println("check2 : "+ request.getParameter("lecture_num"));
		System.out.println("check3 : "+ request.getParameter("lecture_name"));
		System.out.println("check4 : "+ request.getParameter("num"));
		String member_id = request.getParameter("member_id");
		String lecture_name = request.getParameter("lecture_name");
		int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
		isCheck = dao.joinMyLecture(member_id,lecture_num,lecture_name);
		
		if(isCheck) {
			
		}else if(!isCheck) {
			
		}
		return isCheck;
	}

	
	
}
