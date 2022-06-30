<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ page import="java.util.*"%>
<jsp:include page="/common/header.jsp" />
<br />

<br />
<%
	// may need to show how much classes are uploaded
	Object vector = request.getAttribute("try");
	if (vector == null) {
		System.out.println("nothing");
	} else {
		String did = (String) vector;

		if (did == "1") {
			out.println("<script>alert('upload success')</script>");
		} else {
			out.println("<script>alert('upload fail')</script>");
		}
	}
%>

<div class="lectureWrap">
	<div class="lectureLine">
		<form action="addIndex.lr" method="post"
			enctype="multipart/form-data" id="lecture_info">
			<input type="hidden" id="lecture_name" name="lecture_name"
				value="${param.lecture_name}"> <input type="hidden"
				id="lecture_num" name="lecture_num" value="${param.lecture_num}">

			<table>
				<tr>
					<th colspan="2"><h2>강의등록</h2></th>
				</tr>
				<tr>
					<td colspan="2">
						<div id="video"></div>
					</td>
				</tr>
				<tr>
					<td>수업 주제</td>
					<td><input type="text" id="lecture_index" name="lecture_index">
						<br /></td>
				</tr>
				<tr>
					<!-- https://www.youtube.com/embed/GUlSVmAdDzg?list=PLrzZpsWkFX79KvPPgMjSqXk9JHhDIgfHj -->
					<td>강의 동영상 링크</td>
					<td><input type="text" id="lecture_addr" name="lecture_addr">
						<input id="overview" type="button" value="미리보기"></td>
				</tr>
				<tr>
					<td>강의 자료</td>
					<td><input type="file" id="lecture_data" name="lecture_data">
						<br /></td>
				</tr>

				<tr>
					<td colspan="2">
						<input id="add" class="btn btn-primary" type="button" value="추가"> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>




<br />

<jsp:include page="/common/footer.jsp" />

<script>
	var overview = document.getElementById('overview');
	var add = document.getElementById('add');
	var finish = document.getElementById('finish');

	overview.onclick = function() {
		document.getElementById('video').innerHTML = "<embed style='width:600px; height:400px;' src='"
				+ document.getElementById('lecture_addr').value + "'/>";
		return;
	};

	/* location.href = "lecture_index.jsp?lecture_num=${param.lecture_num}"; */
	add.onclick = function() {
		if (confirm("추가하시겠습니까?")) {
			document.getElementById("lecture_info").submit();
		} else {
			return;
		}

	};
</script>