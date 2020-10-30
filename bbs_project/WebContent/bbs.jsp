<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="bbs.BbsDAO"%>
<%@ page import="bbs.BbsVO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device" ,initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>로그인화면</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<link rel="stylesheet" href="css/common.css">
</head>
<body>

	<!-- 헤더 -->
	<jsp:include page="common/bbs_header.jsp" flush="false" />
	<%
		int pageNumber = 1; //기본페이지
		if(request.getParameter("pageNumber") != null){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
	%>
	<!-- 로그인 영역 -->
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align:center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeeee; text-align: center;">작성일</th>
					</tr>
				</thead>
				<tbody>
					<%	
						BbsDAO bbsDAO = new BbsDAO();
						ArrayList<BbsVO> list = bbsDAO.getList(pageNumber);
						for(int i=0; i < list.size(); i++){
					%>
					<tr>
						<td><%=list.get(i).getBbsID() %></td>
						<td><a href="view.jsp?bbsID=<%=list.get(i).getBbsID()%>">
							<%=list.get(i).getBbsTitle().replace(" ", "&nbsp;").replace("<", "&lt;").replace(">","&gt;").replace("\n","<br>")%>
							</a>
						</td>
						<td><%=list.get(i).getUserID() %></td>
						<td><%=list.get(i).getBbsDate() %></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
			<%
				if(pageNumber != 1){
			%>
				<a href="bbs.jsp?pageNumber=<%=pageNumber - 1 %>" class="btn btn-success btn-arraw-left">다음</a>
			<%
				} if(bbsDAO.nextPage(pageNumber + 1)){
			%>
				<a href="bbs.jsp?pageNumber=<%=pageNumber + 1 %>" class="btn btn-success btn-arraw-left">이전</a>
			<%
				}
			%>
			<a href="write.jsp" class="btn btn-primary pull-right" onclick="return writeButton();">글쓰기</a>

		</div>
	</div>


	
</body>
</html>