CREATE DATABASE jsp_project;

show tables;

CREATE TABLE members (						-- 회원테이블
	member_id VARCHAR(45) PRIMARY KEY,		-- 회원아이디
	name VARCHAR(10),						-- 회원이름
	password VARCHAR(45) NOT NULL ,			-- 회원비밀번호
	ts_check VARCHAR(45) NOT NULL ,			-- 강사,학생 체크 
	birth VARCHAR(45) NOT NULL ,			-- 생년월일
	hope_sub VARCHAR(45) ,					-- 희망 과목
	profile_img VARCHAR(500) NOT NULL,		-- 프로필 사진 주소
	regdate TIMESTAMP default now()
);
select * from members;
DELETE FROM members WHERE member_id = 'id003@naver.com';
DELETE FROM members;
INSERT INTO members values(
	'skykim@naver.com',
	'김하늘',
	'1111',
	'학생',
	'1995-09-18',
	'null',
	'dd',
	now()
);

desc members;

CREATE TABLE teacher (						-- 강사 약력정보
	teacher_id VARCHAR(45) PRIMARY KEY,		-- 강사 아이디
	name VARCHAR(45) NOT NULL,				-- 강사 이름
	career TEXT NOT NULL,					-- 강사 약력
	subject TEXT NOT NULL,					-- 과목
	FOREIGN KEY (teacher_id) REFERENCES members(member_id)
);

select * from teacher;
---------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------
CREATE TABLE lecture (											-- 강의 정보
	lecture_num INT PRIMARY KEY auto_increment,					-- 강의 번호
	teacher_id VARCHAR(45) NOT NULL,							-- 강사 아이디
	category VARCHAR(45) NOT NULL,								-- 과목 카테고리
	lecture_name VARCHAR(45) NOT NULL,							-- 강의 이름
	pre_subject VARCHAR(45),									-- 선행 과목
	intro TEXT NOT NULL,										-- 강의 소개
	thumbnail VARCHAR(500) NOT NULL,
	reg_count int default 0									-- 수강 중인 학생 수
);
desc lecture;
delete from lecture;
select * from lecture;
DELETE FROM lecture;
DELETE FROM lecture WHERE categroy = '1';
INSERT INTO lecture VALUES(null,'id003@naver.com','JAVA','자바프로그래밍','c언어','자바 강의를 소개 합니다.','Hydrangeas.jpg','0');
INSERT INTO lecture VALUES(null,'id003@naver.com',1,'웹','자바프로그래밍','c언어','자바 강의를 소개 합니다.''Hydrangeas.jpg');
INSERT INTO lecture VALUES(null,'id003@naver.com',2,'게임','데이터베이스','mysql','데이터베이스 강의를 소개 합니다.''Penguins.jpg');
INSERT INTO lecture VALUES(null,'id003@naver.com',3,'보안','빅데이터','데이터베이스','빅데이터 강의를 소개 합니다.''Chrysanthemum.jpg');
INSERT INTO lecture VALUES(null,'id003@naver.com',4,'그 외','JSP프로그래밍','java','jsp 강의를 소개 합니다.''Desert.jpg');
INSERT INTO lecture VALUES(null,'id003@naver.com',5,'웹','하둡','데이터베이스','하둡 강의를 소개 합니다.''Jellyfish.jpg');
INSERT INTO lecture VALUES(null,'id003@naver.com',6,'게임','몽고디비','mysql','몽고디비 강의를 소개 합니다.''Koala.jpg');


INSERT INTO lecture_info VALUES(null,1,'자바프로그래밍','변수','https://www.youtube.com/embed/RUKeUPWZoSc','Hydrangeas.jpg');
INSERT INTO lecture_info VALUES(null,1,'자바프로그래밍','상수','https://www.youtube.com/embed/kDqe8wjzgrs?list=PLG7te9eYUi7typZrH4fqXvs4E22ZFn1Nj','Penguins.jpg');
INSERT INTO lecture_info VALUES(null,1,'자바프로그래밍','리터럴','https://www.youtube.com/embed/-sn6SNOZecg','Chrysanthemum.jpg');
INSERT INTO lecture_info VALUES(null,1,'자바프로그래밍','제너럴','https://www.youtube.com/embed/i5oE9GpsnAc','Desert.jpg');
INSERT INTO lecture_info VALUES(null,1,'자바프로그래밍','인스턴스','https://www.youtube.com/embed/KdHCpB70riY','Jellyfish.jpg');
INSERT INTO lecture_info VALUES(null,1,'자바프로그래밍','클래스','https://www.youtube.com/embed/4In_wwRTlSs','Koala.jpg');

SELECT * FROM lecture_info;
delete from lecture_info;
CREATE TABLE lecture_info(
	info_num INT PRIMARY KEY auto_increment,					-- 강의 목차 번호
	lecture_num INT NOT NULL,									-- 강의 번호
	lecture_name VARCHAR(45) NOT NULL,							-- 강의 이름
	lecture_index VARCHAR(45) NOT NULL,							-- 강의 목차
	lecture_addr VARCHAR(100) NOT NULL,							-- 강의 동영상 주소
	lecture_data VARCHAR(100)									-- 강의 자료
);	

CREATE TABLE my_lecture (					-- 내 강의 정보
	member_id VARCHAR(45),		-- 회원 아이디
	lecture_name VARCHAR(45)				-- 강의 이름
);

select * from my_lecture WHERE member_id='hao0p0y@nate.com';
desc my_lecture;
DELETE FROM my_lecture WHERE member_id='hao0p0y@nate.com';

CREATE TABLE comment (										-- 댓글 
	writer_id VARCHAR(45) NOT NULL,						-- 작성자 아이디
	lecture_num INT NOT NULL,								-- 강의 번호
	comment_content TEXT,									-- 작성 내용	
	uptime timestamp default now()
);



CREATE TABLE subject (
	subject_num INT PRIMARY KEY auto_increment,
	subject_name VARCHAR(45) UNIQUE
);

select * from subject;
desc lecture;

delete from subject where subject_name = 'C++';

INSERT INTO subject value(null, 'HTML');
INSERT INTO subject value(null, 'JAVASCRIPT');
INSERT INTO subject value(null, 'JAVA');
INSERT INTO subject value(null, 'C');
INSERT INTO subject value(null, 'C++');
 
show tables;

--alter table lecture modify intro longtext;

DROP TABLE members;
DROP TABLE teacher;
DROP TABLE lecture;
DROP TABLE lecture_info;
DROP TABLE my_lecture;
DROP TABLE comment;
DROP TABLE subject;
