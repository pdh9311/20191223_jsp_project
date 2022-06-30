<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>
	
	<div class="loginWrap">
	<div class="loginLine">
	<form action="withdrawSubmit.me" method="post">
		<table>
			<tr>
				<th colspan="2"><h2 style="margin-bottom:30px">회원탈퇴</h2></th>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="tempPass" placeHolder="비밀번호를 입력해주세요" required/></td>
			</tr>
			<tr>
				<td colspan="2"><input class="btn btn-primary" type="submit" value="회원탈퇴" style="margin-top:30px"/></td>
			</tr>
		</table>
	</form>	
	</div>
</div>
<%@ include file="../common/footer.jsp" %>
