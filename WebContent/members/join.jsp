<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>
<div class="joinWrap">
	<div class="joinLine"  style="margin-top:30px">
	<form id="acceptBtn" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th colspan="2"><h2 style="margin-top:20px">회원가입</h2></th>
			</tr>
			<tr> 
				<td colspan="2">프로필 사진</td>
			</tr>
			<tr>
				<td colspan="2"><img id="img" src="upload/images.png" width="100px" height="100px"></td>
			</tr>
			<tr>
				<td colspan="2"><input type="file" id="input_img" name="profile_img" value="사진 선택" accept=".jpg, .png, .jpeg, .gif" required /></td>
			</tr>
			<tr>
				<td>아이디</td>
				<td><input type="email" placeholder="email형식" name="member_id" required/></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" required/></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" id="password" name="password" required/></td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td><input type="password" id="password_chk" name="password_chk" required/></td>
			</tr>
			<tr>
				<td>구분</td>
				<td>
					<select id="ts_check" name="ts_check">
						<option value="학생">학생</option>
						<option value="강사">강사</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td>
					<input type="date" name="birth" value="2019-12-23" min="1970-01-01" max="2020-01-01"/>
				</td>
			</tr>
			<tr>
				<td>수강희망과목</td>
				<td>
					<c:forEach var="subj" items="${subject}" >
					<label>
						<input type="checkbox" name="hope_sub" value="${subj.subject_name}">${subj.subject_name}
					</label>
					</c:forEach>					
				</td>
			</tr>
			<tr>
				<td colspan="2"><input class="btn btn-primary" type="submit" style="margin-top:5px" value="회원가입"/></td>
			</tr>
		</table>
	</form>
	</div>
</div>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	
		var sel_file;
		
		$("#input_img").on("change", handleImgFileSelect);
		
		function handleImgFileSelect(e) {
			var files = e.target.files;
			var filesArr = Array.prototype.slice.call(files);
			
			filesArr.forEach(function(f){
				
				if(!f.type.match("image.*")){
					alert("확장자는 이미지 확장자만 가능합니다.");
					return;
				}
				
				sel_file = f;
				
				var reader = new FileReader();
				reader.onload = function(e){
					$("#img").attr("src", e.target.result);
				}
				reader.readAsDataURL(f);
			});
		}
		
		$("#acceptBtn").submit(function(){
			
			var pass = $("#password").val();
			var pass_chk = $("#password_chk").val();
			var teacher = $("#ts_check").val();
			var split = "";
			if(pass != pass_chk) {
				alert("비밀번호가 일치하지 않습니다.");
				$("#acceptBtn").attr("action","join.me");
			}
			else {
				$("#acceptBtn").attr("action","joinSubmit.me");
			}
			
		});
		
	
	
</script>	
<%@ include file="../common/footer.jsp" %>
