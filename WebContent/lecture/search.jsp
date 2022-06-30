<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../common/header.jsp"%>
<div class="container" style="margin-top: 100px;">

	<!-- Page Features -->
	<div>
		<font size="6" color="white">검색결과</font>
	</div>


	<!-- red line -->

	<c:choose>
		<c:when test="${!empty searchList}">
			<div class="row text-center">
				<c:forEach var="sl" items="${searchList}">
					<div class="col-lg-3 col-md-6 mb-4">
						<div class="card h-100">

							<img class="card-img-top" src="upload/${sl.thumbnail}"
								alt="lecture_thumbnail">
							<div class="card-body">
								<h4 class="card-title">${sl.lecture_name}</h4>
								<p class="card-text">${sl.intro}</p>
							</div>

							<div class="card-footer">
								<a href="detail.lr?lecture_num=${sl.lecture_num}&page=0"
									class="btn btn-primary">Learn More!</a> &nbsp;&nbsp;&nbsp; <small>${sl.teacher_id}</small>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<div style="margin: auto 0; margin-bottom: 10px;" id="moreBtn">
				<a href="lectureSearch.lr?nextNum=${nextNum +1}&searchName=${searchName}&searchValue=${searchValue}"
					class="btn btn-primary" style="width: 100%;"> + 더보기</a>
			</div>
		</c:when>
		<c:otherwise>
			<div>검색결과를 찾을 수 없습니다.</div>
		</c:otherwise>
	</c:choose>

	<!-- RED line -->

</div>
<%@ include file="../common/footer.jsp"%>