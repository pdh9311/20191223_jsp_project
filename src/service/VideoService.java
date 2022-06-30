package service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.LectureInfoVO;

public interface VideoService {

	
	// 강의자료 다운로드 처리
	void fileDown(HttpServletRequest request, HttpServletResponse response);

	// 강의목록 요청
	ArrayList<LectureInfoVO> getLecture(HttpServletRequest request);

	// 수강 등록 유무 체크 및 수강 등록
	boolean checkMyLecture(HttpServletRequest request);	
	
	
}
