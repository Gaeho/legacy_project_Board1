<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "header.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<!-- 작성 2번 view단  -->
<body>
	<form action="write" method="post">
		<table class="content1">
			<tr>
				<td>이름</td>
				<td><input type="text" name="bName"> </td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="bTitle"> </td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea class="textarea1" name="bContent" rows="10" ></textarea> </td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="확인"> <a href="list">목록보기</a> </td>
				
			</tr>
		</table>
	</form>
</body>
</html>