<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp"%>


<div class="studyRoomWrap">
	<div class="studyRoomLine">
		<c:set var="num" value="${param.num}" />
		<div style="float: left; width: 50%;">
			<font size="5" style="color: white">제 ${param.num+1}강 :
				${lectureList[num].lecture_name}</font>
		</div>
		<div class="exit" style="float: right; width: 22%; margin-top: 7px;">
			<font size="5">
				<a href="main.me" style="color:white">홈으로!</a>
			</font>
		</div>
		<div class="videoNoteWrap">
			<div class="videoBox">
				<embed src="${lectureList[num].lecture_addr}" width="1200px"
					height="100%" />
			</div>
			<div class="lectureNote">
				<c:choose>
					<c:when test="${!empty lectureList}">
						<div class="attachNote">강의자료</div>
						<hr align="left" />
						<div class="noteAtag">
							<a href="lectureData.lv?file_name=${lectureList[num].lecture_data}"
								 style="color:black">${lectureList[num].lecture_data}</a>
						</div>
					</c:when>
					<c:otherwise>
						<p>강의 자료 없음</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<div class=indexList>
			<table>
				<thead>
					<tr>
						<th>강의</th>
						<th>목차</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty lectureList}">
							<c:set var="i" value="0" />
							<c:forEach var="list" items="${lectureList}" varStatus="status">
								<tr>
									<td>${status.count}강</td>
									<td>
									<a href="studyRoom.lv?member_id=${member.member_id}&lecture_num=${param.lecture_num}&num=${i}"
										style="color:black">▶${list.lecture_index}</a>
									</td>
								</tr>
								<c:set var="i" value="${i+1}" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan=2>목차가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>

				</tbody>
			</table>
		</div>

	</div>
</div>
<!-- </div>
</div> -->
<%@ include file="../common/videoFooter.jsp" %> 
