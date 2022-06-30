package vo;

public class LectureInfoVO {
	private int info_num;
	private int lecture_num;
	private String lecture_name; 
	private String lecture_index;
	private String lecture_addr;
	private String lecture_data;
	
	
	public int getInfo_num() {
		return info_num;
	}
	public void setInfo_num(int info_num) {
		this.info_num = info_num;
	}
	public int getLecture_num() {
		return lecture_num;
	}
	public void setLecture_num(int lecture_num) {
		this.lecture_num = lecture_num;
	}
	public String getLecture_name() {
		return lecture_name;
	}
	public void setLecture_name(String lecture_name) {
		this.lecture_name = lecture_name;
	}
	public String getLecture_index() {
		return lecture_index;
	}
	public void setLecture_index(String lecture_index) {
		this.lecture_index = lecture_index;
	}
	public String getLecture_addr() {
		return lecture_addr;
	}
	public void setLecture_addr(String lecture_addr) {
		this.lecture_addr = lecture_addr;
	}
	public String getLecture_data() {
		return lecture_data;
	}
	public void setLecture_data(String lecture_data) {
		this.lecture_data = lecture_data;
	}
	
	@Override
	public String toString() {
		return "LectureInfoVO [lecture_num=" + lecture_num + ", lecture_name=" + lecture_name + ", lecture_index="
				+ lecture_index + ", lecture_addr=" + lecture_addr
				+ ", lecture_data=" + lecture_data + "]";
	}
	

	

}
