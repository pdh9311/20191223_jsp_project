package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.LectureRegDAO;
import dao.LectureRegDAOImpl;
import sun.rmi.server.Dispatcher;
import vo.LectureInfoVO;
import vo.LectureRegVO;
import vo.SubjectVO;

public class LectureRegServiceImpl implements LectureRegService {

	LectureRegDAO dao = new LectureRegDAOImpl();

	// DB subject 테이블에 등록되어있는 강의 과목을 받아서 카테고리 option값에 추가해주기
	@Override
	public ArrayList<SubjectVO> getSubject(HttpServletRequest request) {
		ArrayList<SubjectVO> subject = new ArrayList<>();
		subject = dao.getSubject();
		System.out.println("Service subject : " + subject);
		return subject;
	}

	// 강의 개설화면 1 DB에 등록하고 강의 개설화면 2 로 넘어가기
	@Override
	public int lectureReg(HttpServletRequest request) {
		String saveDir = "/upload";
		LectureRegVO lecReg = new LectureRegVO();
		try {
			String realPath = request.getServletContext().getRealPath(saveDir);
			MultipartRequest multi = new MultipartRequest(request, realPath, 1024 * 1024 * 30, "utf-8",
					new DefaultFileRenamePolicy());
			String teacher_id = multi.getParameter("teacher_id");
			String category = multi.getParameter("category");
			String lecture_name = multi.getParameter("lecture_name");
			String pre_subject = multi.getParameter("pre_subject");
			String intro = multi.getParameter("intro");
			String file = (String) multi.getFileNames().nextElement();
			String fileName = multi.getFilesystemName(file);
			System.out.println("fileName : " + fileName);
//			String fileNameOrigin = multi.getOriginalFileName(file);
			String thumbnail = realPath + "\\" + fileName;
			System.out.println("realPath : " + thumbnail);
			lecReg.setTeacher_id(teacher_id);
			lecReg.setCategory(category);
			lecReg.setLecture_name(lecture_name);
			lecReg.setPre_subject(pre_subject);
			lecReg.setIntro(intro);
			lecReg.setThumbnail(fileName);
			System.out.println("Service : " + lecReg);
			request.setAttribute("lecture_name", lecture_name);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao.lectureReg(lecReg, request);
	}

	// 강의 상세보기 정보
	@Override
	public LectureRegVO getlectureReg(HttpServletRequest request) {
		LectureRegVO lrv = null;
		int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
		lrv = dao.getlectureRegVO(lecture_num);
		System.out.println("con : " + lrv);
		return lrv;
	}

	// 강의 상세보기 목차 정보
	@Override
	public ArrayList<LectureInfoVO> getlectureInfo(HttpServletRequest request) {
		ArrayList<LectureInfoVO> liv = null;
		int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
		liv = dao.getLectureList(lecture_num);
		return liv;
	}

	// 강의 목차 가져오기
	@Override
	public LectureInfoVO getLectureIndex(HttpServletRequest request) {
		LectureInfoVO liv = null;
		int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
		String lecture_index = request.getParameter("lecture_index");
		System.out.println("leture_num : " + lecture_num);
		System.out.println("leture_index : " + lecture_index);
		liv = dao.getLectureIndex(lecture_num, lecture_index);
		System.out.println("con liv : " + liv);
		return liv;
	}

	// 강의 목차 추가
	@Override
	public void addLecture(HttpServletRequest request, HttpServletResponse response) {
		LectureInfoVO liv = null;

		try {
			String saveDir = "/upload";
			String realPath = request.getSession().getServletContext().getRealPath(saveDir);
			System.out.println(realPath);

			File f = new File(realPath);
			if (!f.exists()) {
				f.mkdirs();
			}

			MultipartRequest multi = new MultipartRequest(request, realPath, 1024 * 1024 * 10, "utf-8",
					new DefaultFileRenamePolicy());
			String file = (String) multi.getFileNames().nextElement();
			System.out.println(file);
			String fileName = multi.getFilesystemName(file);
			System.out.println(fileName);
			String original_fileName = multi.getOriginalFileName(file);
			System.out.println(original_fileName);
			int lecture_num = Integer.parseInt(multi.getParameter("lecture_num"));
			String lecture_name = multi.getParameter("lecture_name");
			String lecture_index = multi.getParameter("lecture_index");
			String lecture_addr = multi.getParameter("lecture_addr");
			String lecture_data = fileName;
			liv = new LectureInfoVO();
			liv.setLecture_num(lecture_num);
			liv.setLecture_name(lecture_name);
			liv.setLecture_index(lecture_index);
			liv.setLecture_addr(lecture_addr);
			liv.setLecture_data(lecture_data);
			dao.addIndex(liv);
			response.sendRedirect("updateLecture.lr?lecture_num=" + lecture_num);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 강의 목차 수정
	@Override
	public void updateIndex(HttpServletRequest request, HttpServletResponse response) {

		try {

			String saveDir = "/upload";
			String realPath = request.getSession().getServletContext().getRealPath(saveDir);
			System.out.println(realPath);

			MultipartRequest multi = new MultipartRequest(request, realPath, 1024 * 1024 * 10, "utf-8",
					new DefaultFileRenamePolicy());
			String file = (String) multi.getFileNames().nextElement();

			String upload_file = multi.getFilesystemName(file);
			System.out.println("SERVICE upload_file : " + upload_file);
			String origin_file = multi.getOriginalFileName(file);
			int info_num = Integer.parseInt(multi.getParameter("info_num"));
			System.out.println("info : " + info_num);
			int lecture_num = Integer.parseInt(multi.getParameter("lecture_num"));
			System.out.println("num : " + lecture_num);
			String lecture_name = multi.getParameter("lecture_name");
			System.out.println("name : " + lecture_name);
			String lecture_index = multi.getParameter("lecture_index");
			System.out.println("index : " + lecture_index);
			String lecture_addr = multi.getParameter("lecture_addr");
			System.out.println("addr : " + lecture_addr);
			String lecture_data = multi.getParameter("lecture_data");
			System.out.println("data : " + lecture_data);

			if (upload_file == null) {
				upload_file = lecture_data;
			} else if (upload_file != null) {
				File f = new File(realPath + "\\" + lecture_data);
				System.out.println("deleteImage : " + realPath + "\\" + lecture_data);
				f.delete();
				System.out.println("삭제 성공");
			}

			LectureInfoVO liv = new LectureInfoVO();
			liv.setInfo_num(info_num);
			liv.setLecture_num(lecture_num);
			liv.setLecture_name(lecture_name);
			liv.setLecture_index(lecture_index);
			liv.setLecture_addr(lecture_addr);
			liv.setLecture_data(upload_file);
			dao.updateIndex(liv);

			response.sendRedirect("updateLecture.lr?lecture_num=" + lecture_num);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 강의 목차 삭제
	@Override
	public void deleteIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
		String lecture_index = request.getParameter("lecture_index");
		dao.deleteIndex(lecture_num, lecture_index);
		response.sendRedirect("updateLecture.lr?lecture_num=" + lecture_num);

	}

	@Override
	public void updateLecture(HttpServletRequest request, HttpServletResponse response) {

		try {

			String saveDir = "/upload";

			String realPath = request.getServletContext().getRealPath(saveDir);
			MultipartRequest multi = new MultipartRequest(request, realPath, 1024 * 1024 * 30, "utf-8",
					new DefaultFileRenamePolicy());
			int lecture_num = Integer.parseInt(multi.getParameter("lecture_num"));
			System.out.println("ca1 : " + lecture_num);
			String category = multi.getParameter("category");
			System.out.println("ca2 : " + category);
			String lecture_name = multi.getParameter("lecture_name");
			System.out.println("ca3 : " + lecture_name);
			String pre_subject = multi.getParameter("pre_subject");
			System.out.println("ca4 : " + pre_subject);
			String intro = multi.getParameter("intro");
			System.out.println("ca5 : " + intro);
			String pre_thumbnail = multi.getParameter("thumbnail");
			System.out.println("thum : " + pre_thumbnail);
			String file = (String) multi.getFileNames().nextElement();
			String fileName = multi.getFilesystemName(file);
			System.out.println("fileName : " + fileName);
			String thumbnail = realPath + "\\" + fileName;
			System.out.println("realPath : " + thumbnail);
			if (fileName == null) {
				fileName = pre_thumbnail;
			} else if (fileName != null) {
				File f = new File(realPath + "\\" + pre_thumbnail);
				System.out.println("deleteImage : " + realPath + "\\" + pre_thumbnail);
				f.delete();
				System.out.println("삭제 성공");
			}
			LectureRegVO lecReg = new LectureRegVO();
			lecReg.setLecture_num(lecture_num);
			lecReg.setCategory(category);
			lecReg.setLecture_name(lecture_name);
			lecReg.setPre_subject(pre_subject);
			lecReg.setIntro(intro);
			lecReg.setThumbnail(fileName);
			System.out.println("Service : " + lecReg);
			dao.updateLecture(lecReg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void deleteLecture(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			int lecture_num = Integer.parseInt(request.getParameter("lecture_num"));
			System.out.println("lecture_num : " + lecture_num);
			String lecture_name = request.getParameter("lecture_name");
			System.out.println("lecture_name : " + lecture_name);
			dao.deleteLecture(lecture_num, lecture_name);
			response.sendRedirect("myStudy.me");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 검색기능
	@Override
	public ArrayList<LectureRegVO> getSearch(HttpServletRequest request) {
		int nextNum = Integer.parseInt(request.getParameter("nextNum"));
		System.out.println("LRS nextNum : " + nextNum);
		request.setAttribute("nextNum", nextNum);

		String searchName = request.getParameter("searchName");
		request.setAttribute("searchName", searchName);
		System.out.println("searchName : " + searchName);

		String searchValue = request.getParameter("searchValue");
		request.setAttribute("searchValue", searchValue);
		System.out.println("searchValue : " + searchValue);
		ArrayList<LectureRegVO> searchList = dao.getSearchList(searchName, searchValue, nextNum);
		System.out.println("LectureRegServiceImpl searchList : " + searchList);
		return searchList;
	}

}
