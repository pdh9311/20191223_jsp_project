<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>s
<%@ include file="../common/header.jsp" %>
<div class="lectureWrap">
	<div class="lectureLine" style="margin-top:20px">
		<form action="lectureReg1.lr" method="POST" enctype="multipart/form-data" >
			<table>
				<tr><th colspan="2"><h2 style="margin-top:20px">강의등록</h2></th></tr>
				<tr>
					<td>작성자</td>
					<td><input type="text" name="teacher_id" value="${member.member_id}" readonly/></td>
				</tr>
				<tr>
					<td>카테고리</td>		
					<td>
						<select name="category">
						<c:forEach var="subj" items="${subject}" varStatus="s" >
							<c:if test="${s.count eq 1}">
							<option value="${subj.subject_name}" selected>${subj.subject_name}</option>
							</c:if>
							<option value="${subj.subject_name}" >${subj.subject_name}</option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>강의 제목</td>
					<td><input style="width:300px" type="text" name="lecture_name" placeholder="강의 제목을 입력해주세요" required/></td>
				</tr>
				<tr>
					<td>강의소개</td>
					<td>
						<textarea name="intro" cols=50 rows=10></textarea>
					</td>
				</tr>
				<tr>
					<td>선행 과목</td>
					<td><textarea name="pre_subject" cols=50 rows=2 placeholder="선택사항입니다" /></textarea></td>
				</tr>
				<tr>
					<td>썸네일</td>
					<td>
					<img id="img" src="upload/images.png" width="200px" height="150px">
					<br/><br/>
					<input type="file" id="input_img" name="thumbnail" value="사진 선택" accept=".jpg, .png, .jpeg, .gif" required />
					</td>
				</tr>
				<tr>
					<td colspan="2"><input class="btn btn-primary" type="submit" value="저장 후 다음 이동" /></td>
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
</script>
	
<%@ include file="../common/footer.jsp" %>
