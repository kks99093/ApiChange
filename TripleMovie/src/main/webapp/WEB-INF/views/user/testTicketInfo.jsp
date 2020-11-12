<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:forEach var="item" items="${ticketInfo}">
	<div>
		제목: ${item.title}, 
		 예매날짜: ${fn:substring(item.r_dt,0,4) }년  ${fn:substring(item.r_dt,4,6) }월  ${fn:substring(item.r_dt,6,8) }일, 
		몇관: ${item.room }관,예매시간: ${item.s_Time}분
		<c:forEach var="seats" items="${item.seats}">
			${seats}좌석
		</c:forEach>
		<button onclick="test(${item.i_ticket})">예매취소</button>
	</div>
</c:forEach>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script type="text/javascript">
	function test(i_ticket){
		if(!confirm("정말로 취소 하시겠습니까?")){return;}
		axios.post('/user/delTicket',{
			i_ticket:i_ticket
		}).then(function(res){
			if(res.data == 1){
				alert('예매가 취소되었습니다');
				location.reload(true);
			}
		})
	}
</script>