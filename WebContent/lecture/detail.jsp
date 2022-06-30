<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="s"%>
<%@ include file="../common/header.jsp"%>

<%
	// may need to show how much classes are uploaded
	Object vector = request.getAttribute("try");
	if (vector == null) {
		System.out.println("nothing");
	} else {
		String did = (String) vector;

		if (did == "1") {
			out.println("<script>alert('upload comment')</script>");
		} else {
			out.println("<script>alert('upload fail')</script>");
		}
	}
%>
<s:query var="teacher_name" dataSource="jdbc/MysqlDB">
	select * from teacher where teacher_id = ? 
	<s:param>${lrv.teacher_id }</s:param>
</s:query>


<!-- Page Content -->
<div class="line"
	style="background-color: rgba(255, 255, 255, 0.75); border-radius: 0.5rem">
	<div class="container">

		<!-- Jumbotron Header -->
		<header class="jumbotron my-4"
			style="background-color: rgba(255, 255, 255, 0);">
			<div class="col-lg-3 col-md-6 mb-4" style="float: left">
				<div class="card h-100" style="margin-top: 10px;">
					<img class="card-img-top" src="upload/${lrv.thumbnail}"
						width="500px" height="170px">
				</div>
			</div>
			<font size="12">${lrv.lecture_name}</font>
			<!--  -->
			<%-- <p>강사 : ${teacher_name.name}</p> --%>
			<p>
				<c:forEach var="data" items="${teacher_name.rows}">
				강사 : ${data.name}
			</c:forEach>
			</p>
			<p>수강생 ${lrv.reg_count}명</p>
			<p class="lead">${lrv.intro}</p>
			<%-- <form id="sub" action="studyRoom.lv" method="post">
		      <input type="hidden" name="lecture_num" value="${lrv.lecture_num}"/>
		      <input type="hidden" name="lecture_name" value="${lrv.lecture_name}"/>
		      <input type="hidden" name="member_id" value="${member.member_id}" /> --%>
			<div style="float: right; margin-right: 20px">
				<input type="button" class="btn btn-primary btn-lg"
					onclick="javascript:go();" value="바로 학습하기" />
			</div>
			<%-- </form> --%>
		</header>

		<!-- Page Features -->
		<div class="title">
			<h4>강의 소개</h4>
		</div>
		<div>
			<p class="lead">
				${lrv.intro}<br /></p>
		</div>

		<div class="title">
			<h4>강의 목차</h4>
		</div>

		<table class="type09">
			<thead>
				<tr>
					<th class="thead_th" scope="col">강의</th>
					<th scope="col">내용</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="i" value="0" />
				<%-- <tr onclick="detail('${n.notice_num}');"> --%>
				<c:forEach var="list" items="${liv}">
					<c:set var="i" value="${i+1}" />
					<tr onclick="detail('${i}');">
						<th scope="row">${i}강</th>
						<td>${list.lecture_index}</td>
					</tr>
				</c:forEach>


			</tbody>
		</table>
	</div>
</div>

<!-- 댓글 작성 -->
<%-- <%@ include file="../comment/comment.jsp" %> --%>


<s:query var="comment" dataSource="jdbc/MysqlDB">
  	select * from comment where lecture_num = ? order by uptime
  	<s:param>${param.lecture_num}</s:param>
</s:query>
<%-- <c:set var="maxCount" value="comment.rowCount"/> --%>

<!-- page = 0: 1,2,3,4,"5"/
  	   page = 1: 6,7,8,9,"10"/... 
  	   except page > rowCount/5-->

<div class="line"
	style="padding-top: 15px; background-color: rgba(255, 255, 255, 0.75);  border-radius: 0.5rem;">
	<div class="container">
		<div class="commentWrap"
			style="background-color: rgba(255, 255, 255, 0.75);">
			<h4>댓글 작성</h4>
			<br />
			<div class="commentWrite">
				<form action="commentWrite.lr?lecture_num=${param.lecture_num}"
					method="post" id="commentForm">
					<input id="memID" type="hidden" name="member_id"
						value="${member.member_id}" />
					<textarea name="comment_content" class="comment_content"></textarea>
					<input type="button" id="commentWriteBtn" value="등록" />
				</form>
			</div>
		</div>
	</div>
</div>

<div>
	<!-- 댓글 목록 -->
	<c:set var="loop_flag" value="false" />
	<c:forEach var="idx" begin="1" end="${comment.rowCount}">
		<%-- <p>outer ${idx}</p> --%>
		<c:if test="${not loop_flag }">
			<%-- <c:set var="loop_flag" value="true"/> --%>
			<c:if test="${param.page*5+1 <= idx && param.page*5+5 >= idx}">
				<%-- <p>middle ${idx}</p> --%>

				<c:forEach var="commentList" items="${comment.rows}"
					varStatus="status">
					<!-- commentList START -->
					<%-- <p>inner ${idx}</p> --%>
					<c:if test="${status.count == idx}">
						<div style="padding-bottom: 5px;">
							<div class="container"
								style="background-color: rgba(255, 255, 255, 0.7); border-radius: 0.5rem;">
								<table>
									<tr>
										<td><font size="4">${commentList['writer_id'] }</font></td>
										<td><textarea style="width: 500px; height: 75px;"
												readonly>${commentList['comment_content'] }</textarea></td>
										<td>
											<p>${commentList['uptime'] }</p>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</c:if>
				</c:forEach>
				<!-- commentList END -->

			</c:if>
		</c:if>
	</c:forEach>

	<!-- [처음] ... [n-2] [n-1] [n] [n+1] [n+2] ... [끝]  -->
	<div class="container">
		<c:set var="maxpage" value="${(comment.rowCount-1) / 5}" />
		<f:parseNumber var="maxpage_int" value="${maxpage}" integerOnly="true" />
		<ul class="pagination justify-content-center">
			<li class="page-item"><a class="page-link"
				href="detail.lr?lecture_num=${param.lecture_num}&page=0">☜</a></li>
			<c:if test="${param.page-2 >= 0}">
				<li class="page-item"><a class="page-link"
					href="detail.lr?lecture_num=${param.lecture_num}&page=${param.page-2}">[${param.page-1}]</a>
				</li>
			</c:if>
			<c:if test="${param.page-1 >= 0}">
				<li class="page-item"><a class="page-link"
					href="detail.lr?lecture_num=${param.lecture_num}&page=${param.page-1}">[${param.page}]</a>
				</li>
			</c:if>

			<li class="page-item"><a class="page-link">[${param.page+1}]</a>
			</li>
			<c:if test="${param.page+1 <= maxpage_int}">
				<li class="page-item"><a class="page-link"
					href="detail.lr?lecture_num=${param.lecture_num}&page=${param.page+1}">[${param.page+2}]</a></li>
			</c:if>

			<c:if test="${param.page+2 <= maxpage_int}">
				<li class="page-item"><a class="page-link"
					href="detail.lr?lecture_num=${param.lecture_num}&page=${param.page+2}">[${param.page+3}]</a></li>
			</c:if>
			<li class="page-item"><a class="page-link"
				href="detail.lr?lecture_num=${param.lecture_num}&page=${maxpage_int}">☞</a></li>
		</ul>
	</div>
</div>
<!-- /.container -->
<script>
	/* 한민 */
	document.getElementById('commentWriteBtn').onclick = function() {
		if (document.getElementById('memID').value == "") {
			alert('로그인 후 이용가능합니다');
			return;
		} else {
			document.getElementById('commentForm').submit();
			return;
		}
	}
	/* 승업 */
	function detail(num) {
		if ('${member}' == null || '${member}' == "") {
			alert("로그인 후 이용가능합니다");
		} else {
			location.href = "studyRoom.lv?member_id=${member.member_id}&lecture_num=${lrv.lecture_num}&lecture_name=${lrv.lecture_name}&num="
					+ (num - 1);
		}

	}

	function go() {

		if ('${member.member_id}' == "") {
			alert('로그인 후 이용가능합니다.');
		} else {
			location.href = "studyRoom.lv?member_id=${member.member_id}&lecture_num=${lrv.lecture_num}&lecture_name=${lrv.lecture_name}&num=0";
			//$("#sub").submit();
		}

	}
</script>
<%@ include file="../common/footer.jsp"%>

