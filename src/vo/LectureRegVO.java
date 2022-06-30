package vo;

public class LectureRegVO {

	private int lecture_num;
	private String teacher_id;
	private String category;
	private String lecture_name;
	private String pre_subject;
	private String intro;
	private String thumbnail;
	private int reg_count;
	
	public int getLecture_num() {
		return lecture_num;
	}
	public void setLecture_num(int lecture_num) {
		this.lecture_num = lecture_num;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLecture_name() {
		return lecture_name;
	}
	public void setLecture_name(String lecture_name) {
		this.lecture_name = lecture_name;
	}
	public String getPre_subject() {
		return pre_subject;
	}
	public void setPre_subject(String pre_subject) {
		this.pre_subject = pre_subject;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
		
	public int getReg_count() {
		return reg_count;
	}
	public void setReg_count(int reg_count) {
		this.reg_count = reg_count;
	}
	
	@Override
	public String toString() {
		return "LectureRegVO [lecture_num=" + lecture_num + ", teacher_id=" + teacher_id + ", category=" + category
				+ ", lecture_name=" + lecture_name + ", pre_subject=" + pre_subject + ", intro=" + intro
				+ ", thumbnail=" + thumbnail + ", reg_count=" + reg_count + "]";
	}
	
}
