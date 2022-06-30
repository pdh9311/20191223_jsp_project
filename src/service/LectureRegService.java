package service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.LectureInfoVO;
import vo.LectureRegVO;
import vo.SubjectVO;

public interface LectureRegService {

	// 강의 개설화면 1 DB에 등록하고 강의 개설화면 2 로 넘어가기
	int lectureReg(HttpServletRequest request);
	
	// 강의 정보
	LectureRegVO getlectureReg(HttpServletRequest request);
	
	// 강의 목차 정보		
	ArrayList<LectureInfoVO> getlectureInfo(HttpServletRequest request);
	
	// DB subject 테이블에 등록되어있는 강의 과목을 받아서 카테고리 option값에 추가해주기
	ArrayList<SubjectVO> getSubject(HttpServletRequest request);
	
	// 강의 목차 추가
	void addLecture(HttpServletRequest request, HttpServletResponse response);
	
	// 강의 검색
	ArrayList<LectureRegVO> getSearch(HttpServletRequest request);
	
	// 강의 목차 가져오기
	LectureInfoVO getLectureIndex(HttpServletRequest request);
	
	// 강의 목차 수정
	void updateIndex(HttpServletRequest request, HttpServletResponse response);
	
	// 강의 목차 삭제
	void deleteIndex(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	// 강의 수정
	void updateLecture(HttpServletRequest request, HttpServletResponse response);

	void deleteLecture(HttpServletRequest request, HttpServletResponse response);
	
	
}
