<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$('#addFile').click(function(){
			if($('.boardFiles').last().val() == ''){
				alert('빈 파일 업로드가 있습니다.');
			} else {
				$('#files').append('<input type="file" name="multipartFile" class="boardFiles"><br>')
			}
		});
		
		$('#removeFile').click(function(){
			if($('.boardFiles').last().val() != ''){
				alert('빈 파일 업로드가 없습니다');
			} else {
				$('.boardFiles').last().remove();
			}
		});
	});
</script>
</head>
<body>
	<c:set var="o" value="${board}"></c:set>
	<c:set var="b" value="${boardFile}"></c:set>
	<h1>게시물 수정</h1>
	<form action="/board/modifyBoard" method="post">
		<table border="1">
			<tr>
				<th>localName</th>
				<td>
					<input type="hidden" name="boardNo" value="${o.boardNo}">
					<input type="text" name="localName" value="${o.localName}" required="required">
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td>
					<input type="text" name="boardTitle" value="${o.boardTitle}" required="required">
				</td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td>
					<textarea rows="2" cols="80" name="boardContent" required="required">${o.boardContent}</textarea>
				</td>
			</tr>
			<tr>
				<th>boardFile</th>
				<td>
					<c:forEach var="b" items="${boardFile}">
						${b.originFilename} 
					</c:forEach><br>
					<div id="files">
						<input type="file" name="multipartFile" class="boardFiles"><br>
					</div>
					<button type="button" id="addFile">파일 추가</button>
					<button type="button" id="removeFile">파일 삭제</button><br>
				</td>
			</tr>
			<tr>
				<th>memberId</th>
				<td>
					<input type="text" name="memberId" value="${o.memberId}" readonly="readonly">
				</td>
			</tr>
		</table>
		<button type="submit">수정하기</button>
		<a href="/board/boardOne?boardNo=${o.boardNo}">뒤로가기</a>
	</form>
</body>
</html>