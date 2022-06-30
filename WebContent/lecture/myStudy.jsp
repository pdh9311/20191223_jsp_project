<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="s"%>
<%@ include file="../common/header.jsp"%>

<!-- Page Content -->
<div class="container" style="margin-top: 100px; margin-bottom: 40%;">
	<!-- Page Features -->

	<s:query var="my_lecture" dataSource="jdbc/MysqlDB">
   		select * from my_lecture where member_id = ?
   		<s:param>${member.member_id}</s:param>
	</s:query>
	<c:choose>
		<c:when test="${empty my_lecture.rows}">
			<div>
				<font size="6" color="white">수강 중인 강의가 없습니다.</font>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<font size="6" color="white">수강 중인 강의</font>
			</div>

			<div class="row text-center">
				<!-- red line -->
				<c:forEach var="lecture_name" items="${my_lecture.rows}">
					<s:query var="my_lecture_real" dataSource="jdbc/MysqlDB">
			        	select * from lecture where lecture_name = ?
			        	<s:param>${lecture_name['lecture_name']}</s:param>
					</s:query>
					<c:forEach var="lecture_list" items="${my_lecture_real.rows}">
						<div class="col-lg-3 col-md-6 mb-4">

							<div class="card h-100">
								<img class="card-img-top"
									src="upload/${lecture_list.thumbnail}" alt="lecture_thumbnail">
								<div class="card-body">
									<h4 class="card-title">${lecture_list.lecture_name}</h4>
									<p class="card-text">${lecture_list.intro}</p>
								</div>

								<s:query var="teacher_name" dataSource="jdbc/MysqlDB">
				    				select * from teacher where teacher_id = ? 
				    				<s:param>${lecture_list.teacher_id }</s:param>
								</s:query>

								<div class="card-footer">
									<a
										href="detail.lr?lecture_num=${lecture_list.lecture_num}&page=0"
										class="btn btn-primary">Learn More!</a> &nbsp;&nbsp;&nbsp;
									<c:forEach var="t_name" items="${teacher_name.rows }">
										<small>${t_name.name}</small>
									</c:forEach>
								</div>

							</div>
						</div>
					</c:forEach>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>

	<!-- RED line -->

	<s:query var="lecture" dataSource="jdbc/MysqlDB">
   		select * from lecture
    </s:query>
	<s:query var="my_lecture_real" dataSource="jdbc/MysqlDB">
        select * from lecture where teacher_id = ?
        <s:param>${member.member_id}</s:param>
	</s:query>
	<!-- 직업이 강사이면 -->
	<c:if test="${member.ts_check eq '강사'}">
		<c:choose>
			<c:when test="${empty my_lecture_real.rows}">
				<div>
					<font size="6" color="white">개설 강의가 없습니다.</font>
				</div>
			</c:when>
			<c:otherwise>
				<div>
					<font size="6" color="white">개설 강의</font>
				</div>
				<div class="row text-center">

					<c:forEach var="lecture_list" items="${my_lecture_real.rows}">
						<div class="col-lg-3 col-md-6 mb-4">

							<div class="card h-100">
								<img class="card-img-top"
									src="upload/${lecture_list.thumbnail}" alt="lecture_thumbnail">
								<div class="card-body">
									<h4 class="card-title">${lecture_list.lecture_name}</h4>
									<p class="card-text">${lecture_list.intro}</p>
								</div>

								<s:query var="teacher_name" dataSource="jdbc/MysqlDB">
				    				select * from teacher where teacher_id = ? 
				    				<s:param>${lecture_list.teacher_id }</s:param>
								</s:query>

								<div class="card-footer">
									<table>
										<tr>
											<td><a
												href="detail.lr?lecture_num=${lecture_list.lecture_num}&page=0"
												class="btn btn-primary">Show more!</a></td>
										</tr>
										<tr>
											<td><a
												href="updateLecture.lr?lecture_num=${lecture_list.lecture_num}"
												class="btn btn-primary">수정 || 삭제</a></td>
										</tr>
									</table>
								</div>

							</div>
						</div>
					</c:forEach>

				</div>
			</c:otherwise>
		</c:choose>

		<!-- RED line -->
	</c:if>
	<!-- /.row -->

</div>
<!-- /.container -->
<%@ include file="../common/footer.jsp"%>

