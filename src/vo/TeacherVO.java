package vo;

public class TeacherVO {
	private String teacher_id; 		// 강사 아이디
	private String name;	// 강사 이름
	private String career;			// 강사 경력
	private String subject;			// 강의 과목
	
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Override
	public String toString() {
		return "TeacherVO [teacher_id=" + teacher_id + ", name=" + name + ", career=" + career
				+ ", subject=" + subject + "]";
	}
	
}
