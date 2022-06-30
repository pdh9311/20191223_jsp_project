package vo;

public class MembersVO {
	private String member_id;			// 회원 아이디
	private String name;
	private String password;			// 회원 비밀번호
	private String ts_check;			// 강사 / 학생 구분
	private String birth;				// 생년월일
	private String hope_sub;			// 희망 과목
	private String profile_img;			// 프로필 사진 주소
	
	public MembersVO() {}
	
	public MembersVO(String member_id, String name, String ts_check, String birth, String hope_sub,
			String profile_img) {
		this.member_id = member_id;
		this.name = name;
		this.ts_check = ts_check;
		this.birth = birth;
		this.hope_sub = hope_sub;
		this.profile_img = profile_img;
	}

	public MembersVO(String member_id, String name, String ts_check, String birth, String profile_img) {
		this.member_id = member_id;
		this.name = name;
		this.ts_check = ts_check;
		this.birth = birth;
		this.profile_img = profile_img;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTs_check() {
		return ts_check;
	}

	public void setTs_check(String ts_check) {
		this.ts_check = ts_check;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getHope_sub() {
		return hope_sub;
	}

	public void setHope_sub(String hope_sub) {
		this.hope_sub = hope_sub;
	}

	public String getProfile_img() {
		return profile_img;
	}

	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}

	@Override
	public String toString() {
		return "MembersVO [member_id=" + member_id + ", name=" + name + ", password=" + password + ", ts_check="
				+ ts_check + ", birth=" + birth + ", hope_sub=" + hope_sub + ", profile_img="
				+ profile_img + "]";
	}

	
	
	
	

}
