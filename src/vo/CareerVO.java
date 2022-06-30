package vo;

public class CareerVO {
	private String teacher_id;
	private String name;
	private String career;
	private String subject;
	
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
		return "CareerVO [teacher_id=" + teacher_id + ", name=" + name + ", career=" + career + ", subject=" + subject
				+ "]";
	}
	
}
