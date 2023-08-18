<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
	<h1>게시글 입력</h1>
	<form action="/board/addBoard" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<th>localName</th>
				<td>
					<input type="text" name="localName" required="required">
				</td>
			</tr>
			<tr>
				<th>boardTitle</th>
				<td>
					<input type="text" name="boardTitle" required="required">
				</td>
			</tr>
			<tr>
				<th>boardContent</th>
				<td>
					<textarea rows="2" cols="80" name="boardContent" required="required"></textarea>
				</td>
			<tr>
				<th>File</th>
				<td>
					<button type="button" id="addFile">파일 추가</button>
					<button type="button" id="removeFile">파일 삭제</button>
					<div id="files">
						<input type="file" name="multipartFile" class="boardFiles"><br>
					</div>
				</td>
			</tr>
			<tr>
				<th>memberId</th>
				<td>
					<input type="text" name="memberId" required="required">
				</td>
			</tr>
		</table>
		<button type="submit">입력하기</button>
		<a href="/board/boardList">게시판으로</a>
	</form>
</body>
</html>