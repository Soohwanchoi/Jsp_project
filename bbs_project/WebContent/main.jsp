<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device" ,initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/common.css">
<title>로그인화면</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
</head>
<body>

	<!-- 헤더 -->
	<jsp:include page="common/main_header.jsp" flush="false" />

	<!-- 로그인 영역 -->
	
	<div class="container">
		<div class="jumbotron">
			<div class="container">
				<h1>웹 사이트 소개</h1>
				<p style="font-size:25px;">간단 JSP웹사이트</p>
				<p>
					<a class="btn btn-primary btn-pull" href="#" role="button" style="font-size:20px;">살펴보기</a> 
				</p>
			</div>
		</div>
	</div>
	
	<img src="img/momo.jpg" alt="맘타로트">
</body>
</html>