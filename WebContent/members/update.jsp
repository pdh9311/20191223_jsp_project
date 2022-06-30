<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp"%>
<div class="updateWrap">
	<form id="acceptBtn" method="post" enctype="multipart/form-data">
		<div class="joinLine">
			<input type="hidden" name="profile_img" value="${member.profile_img}" />
			<table>
				<tr>
					<th colspan="2"><h2>회원정보수정</h2></th>
				</tr>
				<tr>
					<td colspan="2">프로필 사진</td>
				</tr>
				<tr>
					<td colspan="2"><img id="img"
						src="upload/${member.profile_img}" width="100px" height="100px"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="file" id="input_img"
						name="profile_img" value="사진 선택" accept=".jpg, .png, .jpeg, .gif" /></td>
				</tr>
				<tr>
					<td>아이디</td>
					<td><input type="email" name="member_id"
						value="${member.member_id}" readonly /></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name" value="${member.name}"
						readonly /></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" id="password" name="password"
						required /></td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="password" id="password_chk"
						name="password_chk" required /></td>
				</tr>
				<c:choose>
					<c:when test="${member.ts_check eq '강사'}">
						<td>직업</td>
						<td><input type="text" name="ts_check"
							value="${member.ts_check}" readonly /></td>
					</c:when>
					<c:otherwise>
						<tr>
							<td>직업</td>
							<td><select id="ts_check" name="ts_check">
									<option value="학생">학생</option>
									<option value="강사">강사</option>
							</select></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td>생년월일</td>
					<td><input type="text" name="birth" value="${member.birth}"
						readonly /></td>
				</tr>
				<tr>
					<td>수강희망과목</td>
					<td><label> <input type="checkbox" name="hope_sub"
							value="html" /> HTML
					</label> <label> <input type="checkbox" name="hope_sub"
							value="javascript" /> JAVASCRIPT
					</label> <label> <input type="checkbox" name="hope_sub"
							value="java" /> JAVA
					</label> <label> <input type="checkbox" name="hope_sub" value="c" />
							C
					</label></td>
				</tr>
			</table>
			</div>
			
			<div class="joinLine" style="margin-top:30px">
			<table>
				<!-- 강사 약력 수정하는 부분 추가 -->
				<c:if test="${member.ts_check eq '강사'}">
					<tr>
						<th colspan="2"><h2>강사 약력 수정</h2></th>
					</tr>
					<tr>
						<td>아이디</td>
						<td><input type="email" name="teacher_id"
							value="${member.member_id}" readonly /></td>
					</tr>
					<tr>
						<td>이름</td>
						<td><input type="text" name="name" value="${member.name}"
							readonly /></td>
					</tr>
					<tr>
						<td>경력</td>
						<td><textarea name="career" cols="60" rows="5" required>${career}</textarea></td>
					</tr>
					<tr>
						<td>과목</td>
						<td><textarea name="subject" cols="60" rows="5" required>${sub}</textarea></td>
					</tr>
				</c:if>
			</table>
		</div>
		<div style="margin-top:30px; margin-left:49%; margin-bottom:50px;">
			<input class="btn btn-primary" type="submit" value="수정완료" />
		</div>
	</form>
</div>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	var sel_file;

	$("#input_img").on("change", handleImgFileSelect);

	function handleImgFileSelect(e) {
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);

		filesArr.forEach(function(f) {

			if (!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				return;
			}

			sel_file = f;

			var reader = new FileReader();
			reader.onload = function(e) {
				$("#img").attr("src", e.target.result);
			}
			reader.readAsDataURL(f);
		});
	}

	$("#acceptBtn").submit(function() {
		var pass = $("#password").val();
		var pass_chk = $("#password_chk").val();
		var teacher = $("#ts_check").val();
		if (pass != pass_chk) {
			alert("비밀번호가 일치하지 않습니다.");
			$("#acceptBtn").attr("action", "update.me");
		} else {
			$("#acceptBtn").attr("action", "updateSubmit.me");
		}
	});
</script>
<%@ include file="../common/footer.jsp"%>
