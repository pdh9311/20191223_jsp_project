<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../common/header.jsp"%>

<link href="css/myInfo.css" rel="stylesheet" type="text/css" />

<div class="myInfoWrap">
	<div class="container emp-profile">
		<form action="update.me" method="post">
			<div class="row">
				<div class="col-md-4">
					<div class="profile-img">
						<img src="upload/${member.profile_img}" alt="" />
					</div>
				</div>
				<div class="col-md-6">
					<div class="profile-head">
						<h4>${member.name}</h4>
						<h5>${member.ts_check}</h5>
						<p class="proile-rating">
							<!-- RANKINGS : <span>8/10</span> 필요없는 거 -->
						</p>
						<ul class="nav nav-tabs" id="myTab" role="tablist">
							<li class="nav-item">
								<a class="nav-link active" 
									id="home-tab" 
									data-toggle="tab" 
									href="#home" 
									role="tab"
									aria-controls="home"
									 aria-selected="true">About
								</a>
							</li>
							<c:if test="${member.ts_check eq '강사'}">
							<li class="nav-item">
								<a class="nav-link" 
									id="profile-tab"
									data-toggle="tab" 
									href="#profile" 
									role="tab"
									aria-controls="profile" 
									aria-selected="false">Career
								</a>
							</li>
							</c:if>
						</ul>
					</div>
				</div>
				<div class="col-md-2">
					<input type="submit" class="profile-edit-btn" name="btnAddMore"
						value="Edit Profile" />
						<br/><br/>
					<input type="button" class="profile-edit-btn" onclick="location.href='withdraw.me'" value="Widthdrawl" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="profile-work">
						<!-- 공란 -->
					</div>
				</div>
				<div class="col-md-8">
					<div class="tab-content profile-tab" id="myTabContent">
						<div class="tab-pane fade show active" 
							id="home" 
							role="tabpanel"
							aria-labelledby="home-tab">
							
							<div class="row">
								<div class="col-md-6">
									<label>아이디</label>
								</div>
								<div class="col-md-6">
									<p>${member.member_id}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<label>이름</label>
								</div>
								<div class="col-md-6">
									<p>${member.name}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<label>Email</label>
								</div>
								<div class="col-md-6">
									<p>${member.member_id}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<label>생년월일</label>
								</div>
								<div class="col-md-6">
									<p>${member.birth}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<label>수강희망과목</label>
								</div>
								<div class="col-md-6">
									<p>
										<c:forTokens items="${member.hope_sub}" delims="," var="sub">
													${sub}
										</c:forTokens>
									</p>
								</div>
							</div>
						</div>
						<c:if test="${member.ts_check eq '강사'}">
							<div class="tab-pane fade" id="profile" role="tabpanel"
								aria-labelledby="profile-tab">
								<div class="row">
									<div class="col-md-6">
										<label>강의 과목</label>
									</div>
									<div class="col-md-6">
										<input type="hidden" name="subject" value="${teacher.subject}"/>
										<p>${teacher.subject}</p>	<!-- 값 받아와서 수정 -->
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<label>강사 약력</label>
									</div>
									<div class="col-md-6">
									<input type="hidden" name="career" value="${teacher.career}"/>
										<p>${teacher.career}</p>	<!-- 값 받아와서 수정 -->
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>


<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<%@ include file="../common/footer.jsp"%>

