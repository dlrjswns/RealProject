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

<c:forEach var="v" items="${datas}">
	<c:set var="bvo" value="${v.board}" />
	${bvo.mid} <a href="board_controller.jsp?action=fav&cnt=${cnt}&bid=${bvo.bid}">♥</a> <br>
	<c:forEach var="vv" items="${v.rdatas}">
		${vv.rid} ${vv.bid} ${vv.mid} ${vv.rmsg} <br>
	</c:forEach>
	<hr>
</c:forEach>

<a href="board_controller.jsp?action=main&cnt=${cnt+2}">[더보기]</a>

</body>
</html>