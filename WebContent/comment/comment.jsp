<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="css/comment.css" rel="stylesheet" type="text/css" />
<c:if test="${!empty member}">
  <div class="line" style="padding-top:15px;">
  	<div class="container">
		<div class="commentWrap">
			<h4>댓글 작성</h4>
			<br/>
			<div class="commentWrite">
				<form action="commentWrite.bo" method="post">
					<textarea name="comment_content" class="comment_content"></textarea>
					<input type="button" id="commentWriteBtn" value="등록" />
				</form>
			</div>
		</div>
	</div>
  </div>
</c:if>