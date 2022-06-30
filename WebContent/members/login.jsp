<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ include file="../common/header.jsp" %>

<div class="loginWrap">
	<div class="loginLine">
	<form action="loginSubmit.me" method="post">
		<table>
			<tr>
				<th colspan="2"><h2 style="margin-bottom:20px; margin-top:20px;">로그인</h2></th>
			</tr>
			<tr>
				<td>아이디</td>
				<td><input type="email" placeholder="email형식" name="member_id" required/></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password" required/></td>
			</tr>
			<tr>
				<td colspan="2"><input style="margin-top:20px" class="btn btn-primary" type="submit" value="로그인"/></td>
			</tr>
		</table>
	</form>	
	</div>
</div>
<%@ include file="../common/footer.jsp" %>
