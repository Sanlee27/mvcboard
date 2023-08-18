<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="o" value="${board}"></c:set>
	<c:set var="b" value="${boardFile}"></c:set>
	<h1>게시물 삭제</h1>
	<form action="/board/removeBoard" method="post">
		<table border="1">
			<tr>
				<th>boardNo</th>
				<td>
					<input type="text" name="boardNo" value="${o.boardNo}" readonly="readonly">
				</td>
			</tr>
			<tr>
				<th>localName</th>
				<td>
					${o.localName}
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td>
					${o.boardTitle}
				</td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td>
					${o.boardContent}
				</td>
			</tr>
			<tr>
				<th>boardFile</th>
				<td>
					<c:forEach var="b" items="${boardFile}">
						<img src="/upload/${b.saveFilename}"><br>
						${b.originFilename}
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>memberId</th>
				<td>
					<input type="text" name="memberId" required="required" placeholder="삭제하시려면 아이디를 입력하세요.">
				</td>
			</tr>
		</table>
		<button type="submit">삭제하기</button>
		<a href="/board/boardOne?boardNo=${o.boardNo}">뒤로가기</a>
	</form>
</body>
</html>