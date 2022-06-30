package vo;

public class SubjectVO {

	private String Subject_num;
	private String Subject_name;
	
	public String getSubject_num() {
		return Subject_num;
	}
	public void setSubject_num(String subject_num) {
		Subject_num = subject_num;
	}
	public String getSubject_name() {
		return Subject_name;
	}
	public void setSubject_name(String subject_name) {
		Subject_name = subject_name;
	}
	
	@Override
	public String toString() {
		return "SubjectVO [Subject_num=" + Subject_num + ", Subject_name=" + Subject_name + "]";
	}
	
	
	
}
