package dao;

import vo.CareerVO;
import vo.MembersVO;
import vo.TeacherVO;

public interface MembersDAO {

	// 회원가입 + 여기서 중복체크 같이
	public boolean memberJoin(MembersVO vo);
	// 로그인 
	public MembersVO memberLogin(String member_id, String password); 
	// 회원 정보 검색
	public MembersVO getMemberById(String member_id);
	// 회원 정보 수정
	public boolean memberUpdate(MembersVO member);
	// 회원 정보 수정
		public boolean memberUpdate(MembersVO member, String subject, String career);
	// 강사 가입
	public boolean teacherJoin(CareerVO cv);
	// 강사 정보 검색
	public TeacherVO getTeacherInfo(String member_id);
	// 회원탈퇴
	public boolean deleteMember(String member_id);
}
