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
	<c:forEach	items="${movieList }" var="movieList">
		 // 제목: ${movieList.items[0].title } ㅡ
		  포스터:<img  src="${movieList.items[0].image }"> ㅡ 
		  심의:${movieList.audits }
		 ㅡ 평점: ${movieList.items[0].userRating } ㅡ 
		 개봉날짜: ${movieList.openDt }
	</c:forEach>
</body>
</html>