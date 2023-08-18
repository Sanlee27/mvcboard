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
	<h1>게시글 상세</h1>
	<table border="1">
			<tr>
				<th>boardNo</th>
				<td>
					${o.boardNo}
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
					${o.memberId}
				</td>
			</tr>
			<tr>
				<th>createdate</th>
				<td>
					${o.createdate}
				</td>
			</tr>
			<tr>
				<th>updatedate</th>
				<td>
					${o.updatedate}
				</td>
			</tr>
	</table>
	<a href="/board/boardList">게시판으로</a>
	<a href="/board/modifyBoard?boardNo=${o.boardNo}">게시판수정</a>
	<a href="/board/removeBoard?boardNo=${o.boardNo}">게시글삭제</a>
</body>
</html>