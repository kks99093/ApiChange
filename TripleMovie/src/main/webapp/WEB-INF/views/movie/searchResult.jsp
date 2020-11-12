<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${movieList}" var="item">
	<div onclick="test(${item.movieCd})">
		<img src="${item.imgurl}">
		<p>${item.title}</p>
		<p>${item.genre}</p>
		<p>${item.director}</p>
		<p>${item.country}</p>
		<p>${item.showTime}</p>
		<p>${item.actor}</p>
		<p>${item.grade}</p>
	</div>
</c:forEach>
<script type="text/javascript">
	function test(movieCd){
		location.href = '/movie/detail?movieCd=' + movieCd;
	}
</script>
