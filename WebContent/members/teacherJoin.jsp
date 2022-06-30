<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp"%>
<div class="joinWrap">
	<div class="joinLine">
		<form action="teacherJoinSubmit.me" method="post">
			<table>
				<tr>
					<th colspan="2"><h2 style="margin-top:20px">강사 약력</h2></th>
				</tr>
				<tr>
					<td>아이디</td>
					<td><input type="email" name="teacher_id"
						value="${members.member_id}" readonly /></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name" value="${members.name}"
						readonly /></td>
				</tr>
				<tr>
					<td>약력</td>
					<td><textarea name="career" cols="60" rows="5" required></textarea></td>
				</tr>
				<tr>
					<td>과목</td>
					<td><textarea name="subject" cols="60" rows="5" required></textarea></td>
				</tr>
				<tr>
					<td colspan="2"><input class="btn btn-primary" type="submit"
						value="작성완료" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>

<%@ include file="../common/footer.jsp"%>
