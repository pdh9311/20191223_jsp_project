<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Do it Programming</title>
<link href="css/common.css" rel="stylesheet" type="text/css" />
<link href="css/detail.css" rel="stylesheet" type="text/css" />
<link href="css/comment.css" rel="stylesheet" type="text/css" />
<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<!-- Custom styles for this template -->

</head>

<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="main.me">Do it Programming</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<!-- 검색 -->
					<li class="nav-item" style="margin-top: 5px;">
						<form action="lectureSearch.lr" method="post">
							<input type="hidden" name="nextNum" value="0" /> <select id="sel"
								name="searchName" onchange="javascript:select();"
								style="margin: 3px 5px 3px 0;">
								<option value="" selected>--선택--</option>
								<option value="lecture_name">강의명</option>
								<option value="category">카테고리</option>
							</select> <span id="here"></span> <input class="btn-primary" type="submit"
								value="검색" />
						</form>
					</li>
					<!-- 검색 -->
					<li class="nav-item active"><a class="nav-link" href="main.me">홈</a>
					</li>
					<c:choose>
						<c:when test="${!empty sessionScope.member}">
							<c:if test="${member.ts_check eq '강사'}">
								<li class="nav-item active"><a class="nav-link"
									href="lectureReg.lr">강의 개설</a></li>
							</c:if>
							<li class="nav-item active"><a class="nav-link"
								href="myStudy.me">내 강의실</a></li>
							<li class="nav-item active"><a class="nav-link"
								href="myInfo.me">${member.name}님 반가워요</a></li>
							<li class="nav-item active"><a class="nav-link"
								href="logOut.me">로그아웃</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item active"><a class="nav-link"
								href="login.me">로그인</a></li>
							<li class="nav-item active"><a class="nav-link"
								href="join.me">회원가입</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

	<script>
  	function select() {
  		var selected = $("#sel").val();
  		console.log(selected);
  		var str = "";
  		if(selected == 'lecture_name' ){
  			str += "<input type='text' name='searchValue' palecholder='과목검색'>";
  		}else if (selected == 'category'){
  			str += "<select name='searchValue'>";
  			str += "<c:forEach var='subj' items='${subject}' varStatus='s' >";
  			str += "<c:if test='${s.count eq 1}'>";
  			str += "<option value='${subj.subject_name}' selected>${subj.subject_name}</option>";
  			str += "</c:if>";
  			str += "<option value='${subj.subject_name}'>${subj.subject_name}</option>";
  			str += "</c:forEach>";
  			str += "</select>";
  		}else{
  			str += "";
  		}
  		 document.getElementById("here").innerHTML=str; 
  		
  	}
  </script>