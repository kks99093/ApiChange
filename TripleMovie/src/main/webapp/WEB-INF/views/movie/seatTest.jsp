<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
</head>
<body>
	<form id="frm" action="/movie/test" method="post">
	성인
	
	<select name="adult" onchange="choicePer()">
		<option value="0">0</option>		
		<c:forEach begin="1" end="4" var="i">
			<option value="${i}">${i} 명</option>
		</c:forEach>		
    </select>
	청소년
    <select name="student" onchange="choicePer()">
		<option value="0">0</option>
		<c:forEach begin="1" end="4" var="i">		
			<option value="${i}">${i} 명</option>
		</c:forEach>		
    </select>
    <input type="hidden" name="title" value="${userTicket.title}">
    <input type="hidden" name="r_dt" value="${userTicket.r_dt}">
    <input type="hidden" name="movieCd" value="${userTicket.movieCd}">
    <input type="hidden" name="s_dt" value="${userTicket.s_dt}">
    <input type="hidden" name="room" value="${userTicket.room}">
    <input type="hidden" name="seats">
	<input type="hidden" name="seatCnt">
	<div id= "seat"></div>
	<input type="submit" value="테스트서브밋">
	</form>
<script>
	var selectedSeats = []

	for(let i=0; i<10; i++){
        div = document.createElement("div")
		for(let j=0; j<10; j++){
			input = document.createElement("input")
			input.type = 'button'
			//input.name = 'seats'
			let seat = mapping(i, j);
			input.id = 'seat'+mapping(i, j)
			input.value = seat
			input.setAttribute('onclick','choiceSeat('+input.id+')')
			div.appendChild(input)
		}
        seat.appendChild(div)
	}
	//좌석선택시
	function choiceSeat(id){
    	if (id.classList.contains("selected")){ //selected 클래스명을 가진 좌석이라면(이미 선택)
    		console.log('선택된 좌석')
    		id.classList.remove("selected") //클래스명 selected 제거
    		id.style.backgroundColor = 'white'
    		frm.seatCnt.value += 1;
    		let idx = selectedSeats.indexOf(id.id)
    		selectedSeats.splice(idx,1)
    		frm.seats.value = selectedSeats
    	}else{ //선택되지 않은 좌석
    		if(frm.seatCnt.value == 0){
        		alert('인원을 확인해주세요')
        	}else{
        	id.classList.add("selected");
        	id.style.backgroundColor = 'red'
    		frm.seatCnt.value -= 1;
        	selectedSeats.push(id.id)
        	frm.seats.value = selectedSeats
        	}
    	}
		
	}
	
	//인원선택시 로직
    function choicePer(){
    	frm.seatCnt.value = Number(frm.adult.value) + Number(frm.student.value)
    }
	
	function mapping(i, j){
		let seat = ""
		switch (i){
	    case 0:
	    	seat = "A" + j;
	        break;
	    case 1:
	    	seat = "B" + j;
	        break;
	    case 2:
	    	seat = "C" + j;
	        break;
	    case 3:
	    	seat = "D" + j;
	        break;
	    case 4:
	    	seat = "E" + j;
	        break;
	    case 5:
	    	seat = "F" + j;
	        break;
	    case 6:
	    	seat = "G" + j;
	        break;
	    case 7:
	    	seat = "H" + j;
	        break;
	    case 8:
	    	seat = "I" + j;
	        break;
	    case 9:
	    	seat = "J" + j;
	        break;
	}
		return seat;
	}


    
</script>
</body>
</html>