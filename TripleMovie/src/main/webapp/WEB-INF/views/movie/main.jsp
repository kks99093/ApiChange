<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<link rel="stylesheet" href="../package/swiper-bundle.min.css">
<link rel="stylesheet" href="/res/css/slide.css">
<div class="swiper-container">
	<div class="swiper-wrapper">
		<div class="main_title">
			<p>박스오피스</p>
		</div>
		<div class="swiper-slide">
		<!--  -->
			<c:forEach	items="${movieList}" var="movieList">
				<table>
					<tr>
						<td colspan="2">
							 <img src="${movieList.data[0].result[0].posters }" class="poster" onclick="toDetail('${movieList.data[0].result[0].movieSeq}')">
						</td>
					</tr>
					<tr>
						<td colspan="2" class="title">
							<p>${movieList.kmaQuery}</p>		
						</td>
					</tr>
					<tr>
						<td class="time">
							<p>${movieList.data[0].result[0].runtime}분</p>
						</td>
					</tr>
					<tr>

					</tr>
					<tr>
						<td colspan="2">
							<a href="/movie/movieticket">
								<button type="button">예매하기</button>
							</a>
						</td>
					</tr>
				</table>
				 
			</c:forEach>
		</div>		
	</div>
	<!-- Add Pagination -->
	<div class="swiper-pagination"></div>
</div>

<!-- Swiper JS -->
<script src="../package/swiper-bundle.min.js"></script>

<!-- Initialize Swiper -->
<script>
	var swiper = new Swiper('.swiper-container', {
	pagination: {
			el: '.swiper-pagination',
		},
	});
	
	function toDetail(movieSeq){
		location.href = "/movie/detail?movieSeq="+movieSeq
	}
</script>
