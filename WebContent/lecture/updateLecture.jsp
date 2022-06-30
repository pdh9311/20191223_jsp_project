<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp"%>
<div class="container"
	style="margin-top: 60px; margin-bottom: 24%; color: black;">
	<!-- div라인 css적용 -->
	<div class="lectureLine">
		<form id="update_lecture" action="updateLectureSubmit.lr" method="post" enctype="multipart/form-data">
			<input type="hidden" name="lecture_num" value="${param.lecture_num}"/>
			<table>
				<tr>
					<th colspan="2"><h2>강의 업데이트</h2></th>
				</tr>
				<tr>
					<td>카테고리</td>
					<td><input type="text" name="category" value="${lrv.category}" /></td>
				</tr>
				<tr>
					<td>강의 제목</td>
					<td><input type="text" name="lecture_name"
						value="${lrv.lecture_name}" /></td>
				</tr>
				<tr>
					<td>선행학습</td>
					<td><input type="text" name="pre_subject"
						value="${lrv.pre_subject}" /></td>
				</tr>
				<tr>
					<td>강의 소개</td>
					<td><textarea name="intro">${lrv.intro}</textarea></td>
				</tr>
				<tr>
					<td>썸네일</td>
					<td><img id="img" src="upload/${lrv.thumbnail}" width="200px"
						height="150px"> <br />
					<br /> <input type="hidden" name="thumbnail"
						value="${lrv.thumbnail}" /> <input type="file" id="input_img"
						name="c_thumbnail" value="사진 선택" accept=".jpg, .png, .jpeg, .gif"
						required /></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- div라인 css적용 -->
	<div>
		<table class="type09" style="width: 80%; text-align: center; border-radius:0.5rem;">
			<thead>
				<tr style="background-color:rgba(255,255,255,0.8);">
					<th>강좌</th>
					<th>강의 제목</th>
					<th style="width: 30px;">수정</th>
					<th style="width: 30px;">삭제</th>
				</tr>
			</thead>
			<tbody style="background-color:rgba(255,255,255,0.7)">
				<c:set var="i" value="0" />
				<c:forEach var="list" items="${liv}">
					<c:set var="i" value="${i+1}" />
					<tr>
						<th scope="row">${i}강</th>
						<td>${list.lecture_index}</td>
						<td style="text-align: center; width: 30px;"><input
							type="button" class="btn btn-primary"
							onclick="location.href='updateIndexForm.lr?lecture_num=${lrv.lecture_num}&lecture_name=${list.lecture_name}&lecture_index=${list.lecture_index}';"
							value="수정" /></td>
						<td style="text-align: center; width: 30px;"><input
							type="button" class="btn btn-primary"
							onclick="location.href='deleteIndex.lr?lecture_num=${lrv.lecture_num}&lecture_index=${list.lecture_index}';"
							value="삭제" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
	<div style="padding-left:40%;">
			<input type="button" class="btn btn-primary"
				onclick="location.href='addIndexForm.lr?lecture_num=${lrv.lecture_num}&lecture_name=${lrv.lecture_name}';"
				value="목차 추가" /> 
				<input type="button" id="finishBtn" class="btn btn-primary" value="수정 완료" /> 
				<input type="button" id="deleteBtn" class="btn btn-primary"  value="삭제" />

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
	var update = document.getElementById('finishBtn');
	update.onclick = function() {
		if (confirm("강의 수정을 끝내시겠습니까?")) {
			document.getElementById("update_lecture").submit();
		} else {
			return;
		}
	};
	var deleteBtn = document.getElementById('deleteBtn');
	deleteBtn.onclick = function() {
		if (confirm("삭제 하시겠습니까?")) {
			location.href="deleteLecture.lr?lecture_num=${lrv.lecture_num}&lecture_name=${lrv.lecture_name}";
		} else {
			return;
		}
	};
</script>
<%@ include file="../common/footer.jsp"%>