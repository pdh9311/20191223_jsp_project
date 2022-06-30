package dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import vo.LectureInfoVO;
import vo.LectureRegVO;
import vo.SubjectVO;

public interface LectureRegDAO {

	// 강의 정보 등록
//	int lectureReg(LectureRegVO lecReg);
	int lectureReg(LectureRegVO lecReg, HttpServletRequest request);


	// 강의목차요청
	ArrayList<LectureInfoVO> getLectureList(int info_num);

	// 강의 유무 체크 후 수강신청
	boolean joinMyLecture(String member_id, int lecture_num, String lecture_name);

	// 강의 상세보기 정보
	LectureRegVO getlectureRegVO(int lecture_num);
		
	// DB에 저장된 과목정보 찾기
	ArrayList<SubjectVO> getSubject();

	// 강의 목차 추가
	void addIndex(LectureInfoVO liv);
		
	// 검색 기능
	ArrayList<LectureRegVO> getSearchList(String searchName, String searchValue, int nextNum);

	// 강의 목차 가져오기
	LectureInfoVO getLectureIndex(int lecture_num, String lecture_name);

	// 강의 목차 수정하기
	void updateIndex(LectureInfoVO liv);

	// 강의 목차 삭제
	void deleteIndex(int lecture_num, String lecture_index);

	// 강의 수정
	void updateLecture(LectureRegVO lecReg);


	void deleteLecture(int lecture_num, String lecture_name);
}

