<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<link rel="stylesheet" href="/res/css/ticket.css">

<div class="ticket_container">
	<form action="/movie/movieSheets" id="frm" >
	<section class="day_select">
		<header class="title">
			<span>날짜선택</span>
		</header>
		<c:forEach items="${movieDay}" var="movieDay" varStatus="i">
			<div class="day_div" id="day_btn${i.index}" onclick="choisDay(${movieDay.r_dt},${i.index})">
				<span id="day_week${i.index}"></span>
			</div>
		</c:forEach>
	</section>
	<section class="movie_select">
		<header class="title">
			<span>영화선택</span>
		</header>
		<input type="hidden" name="title">
		<input type="hidden" name="r_dt" value="0">
		<input type="hidden" name="movieCd" value="0">
		<input type="hidden" name="room" value="0">
		<input type="hidden" name="s_dt" value="0">
		<c:forEach items="${movieList}" var="movieList"  varStatus="i">
			<div id="movie_btn${i.index}" class="movie_div" onclick="choisMv(${movieList.movieCd},${i.index},'${movieList.title}')">
				<span>${movieList.title}</span>
			</div>
		</c:forEach>
	</section>
	<section class="cinema_select">
		<header class="title">
			<span>관선택</span>
		</header>
		<c:forEach begin="1" end="4" varStatus="i">
			<div id="room_btn${i.index}" class="cinema_div" onclick="choisRoom(${i.index})">
				<span>${i.index }관</span>
			</div>
		</c:forEach>
	</section>
	<section class="time_select">
		<header class="title">
			<span>시간선택</span>
		</header>
		<!-- foreach문 -->
		<div id="time" class="cinema_div">
		</div>
		<div id="submit">
		</div>
	</section>
	</form>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script type="text/javascript">

//날짜 구하는 함수
function week(){
	var week = ['일', '월', '화', '수', '목', '금', '토'];
    let today = new Date();
    let year = today.getYear();
    let month = today.getMonth() + 1;  // 월
	let date = today.getDate(); // 날짜
	let day = today.getDay();  // 요일
	let lastDate = new Date(year, month, 0);
    for(var i=0; i<=2; i++){
	    var weekday = document.getElementById('day_week'+i);
		if((date+i) > lastDate.getDate() && i == 1){
			month += 1;
			date = 0;
		}else if((date+i)> lastDate.getDate() && i ==2){
			month += 1;
			date = -1;
		}
    	
    	if((day+i) > 6 && i ==1){
    		day = -1;
    	}else if((day + i) > 6 && i == 2){
    		day = -2;
    	}
		weekday.innerHTML = month + '/' + (date + i) + '(' + week[(day+i)] + ')';
    }
	
}


//날짜 함수 호출
week();

//날짜 선택시 함수
function choisDay(r_dt,idx){
	time.innerHTML = ''
	submit.innerHTML = ''
	for(let i=1; i<5;i++ ){
		roomBtn = document.getElementById('room_btn'+i)
		roomBtn.style.backgroundColor= 'white'
	}
	for(let i=0; i<5;i++ ){
		movieBtn = document.getElementById('movie_btn'+i)
		movieBtn.style.backgroundColor = 'white'
	}
	
	frm.r_dt.value = r_dt
	for(let i=0; i<3; i++){
		dayBtn = document.getElementById('day_btn'+i)
		if(i == idx){
			dayBtn.style.backgroundColor = '#bebebe'
		}else{
			dayBtn.style.backgroundColor = 'white'
		}
	}
}

//영화 선택시 함수
function choisMv(movieCd,idx,title){
	if(frm.r_dt.value == 0){
		alert('날짜를 골라주세요')
		return
	}
	
	time.innerHTML = ''
	submit.innerHTML = ''
	for(let i=1; i<5;i++ ){
		roomBtn = document.getElementById('room_btn'+i)
		roomBtn.style.backgroundColor= 'white'
	}
	frm.title.value = title;
	frm.movieCd.value = movieCd
	for(let i=0; i<5;i++ ){
		movieBtn = document.getElementById('movie_btn'+i)
		if(i == idx){
			movieBtn.style.backgroundColor = '#bebebe'
		}else{
			movieBtn.style.backgroundColor = 'white'
		}
	}
	
}
// 관선택시 함수
function choisRoom(room){
	if(frm.movieCd.value == 0){
		alert('영화를 선택해주세요')
		return
	}
	time.innerHTML = ''
	frm.room.value = room;
	for(let i=1; i<5;i++ ){
		roomBtn = document.getElementById('room_btn'+i)
		if(i == room){
			roomBtn.style.backgroundColor = '#bebebe'
			selTime()
		}else{
			roomBtn.style.backgroundColor= 'white'
		}
	}
	
}

//시간 선택시 함수
function choisTime(s_dt, idx, length){
	submit.innerHTML = ''
	frm.s_dt.value = s_dt;
	console.log(s_dt)
	for(let i=0; i<length;i++ ){
		timeBtn = document.getElementById('time_btn'+i)
		if(i == idx){
			timeBtn.style.backgroundColor = '#bebebe'
			submit_btn = document.createElement('input')
			submit_btn.type = 'submit'
			submit_btn.value = '예매하기'
			submit.appendChild(submit_btn)
		}else{
			timeBtn.style.backgroundColor= 'white'
		}
	}
	
}

//시간 불러오는 부분
function selTime(){
	room = frm.room.value
	r_dt = frm.r_dt.value
	
	axios.get('/movie/selTime',{
		params:{
			room : room,
			r_dt : r_dt
		}
	}).then(function(res){
		console.log(res.data)
		for(let i=0; i<res.data.length; i++){
			time_btn = document.createElement('div')
			time_btn.id = 'time_btn'+i
			time_btn.setAttribute('onclick','choisTime('+ res.data[i].s_dt + ',' + i +','+res.data.length +')')
			time_span = document.createElement('span')
			time_span.innerHTML = res.data[i].time
			time_btn.appendChild(time_span)
			time.appendChild(time_btn)
		}
	})
}
</script>
</div>
