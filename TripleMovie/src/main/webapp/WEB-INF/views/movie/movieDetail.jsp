<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/res/css/detail.css">
<div class="detail_container">
	<div class="detail_sub_container">
		<div class="detail_main_content">
			<section class="detail_left_section">
				<img alt="이미지 등록실패" src="${movieDetail.items[0].image}">		
			</section>
			
			<section class="detail_right_section">	
				<h1>${movieDetail.items[0].title}<span>${movieDetail.audits}</span></h1>
				<div class="detail_info">
					<span>${movieDetail.openDt }</span> 
					<span>${movieDetail.showTm }분</span> 
					<span>
						<c:forEach items="${movieDetail.genres}" var="genres">
						 	${genres.genreNm}
					 	</c:forEach>
				 	</span>
				</div>
				<div>
					<span class="font_bold">감독</span>
					<span>
						<c:forEach items="${movieDetail.directors}" var="directors">
							${directors.peopleNm }
						</c:forEach>
					</span>
				</div>
				<div>
					<span class="font_bold">출연</span>
					<span> 
						<c:forEach items="${movieDetail.actors}" var="actors" begin="1" end="4"> 
							${actors.peopleNm }
						</c:forEach>
					</span>
				</div>
				<hr>
				<div class="detail_content_container">
					<span class="detail_content">
						${movieDetail.summary}
					</span>
				</div>		
			</section>
		</div>
		<div class="detail_comment_container">
			<h1>리뷰</h1>
			<table class="comment_table">
				<tr>
					<td class="comment_textarea">
						<textarea id="ctnt" rows="5" cols="60" placeholder="내용을 작성해주세요."></textarea>
					</td>
					<td class="comment_btn">
						<a onclick="insCmt()">등록</a>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- 댓글 뿌리는 곳!!!!!-->
		<c:forEach items="${movieCmt.cmt_info}" var="cmt_info" varStatus="status">
			<table  class="comment_content">
				<tr>
					<td class="comment_nick_td">닉네임</td>
					<td class="comment_nick">${cmt_info.nick_nm}</td>
					<td class="comment_ctnt"><span>${cmt_info.ctnt}</span></td>
					<td class="comment_favorite_td">좋아요</td>
					<td class="comment_favorite">${cmt_info.ctnt_cnt}
						<c:choose>
							<c:when test="${movieCmt.cmt_favorite[status.index].i_cmt != null}">
								<span onclick="cmt_Notfavorite(${movieCmt.cmt_favorite[status.index].seq})">
									<img src="/res/img/cmt_like.png">
								</span>
							</c:when>
							<c:otherwise>
								<span onclick="cmt_favorite(${cmt_info.i_cmt})">
									<img src="/res/img/cmt_unlike.png">
								</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</c:forEach>
	</div>
</div>
	
  <!-- 동영상 고침 
	<section>
		<div id="youTubePlayer1"></div>
	</section>
-->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script type="text/javascript">

	function insCmt(){
		var ctnt = document.querySelector('#ctnt').value;
		console.log("ctnt: " + ctnt)
		axios.post('/movie/ajaxCmt', {
			ctnt : ctnt,
			cd : `${movieDetail.movieCd}`
		}).then(function(res) {
			if(res.data == 1){
				location.reload(true);
			}else{
				alert("댓글을 등록할 수  없습니다!")
			}
		})
	}
	
	function cmt_Notfavorite(seq){
		console.log('seq: ' + seq)
		axios.get('/movie/ajaxCmt_Notfavorite',{
			params:{
				seq:seq
			}
		}).then(function(res){
			if(res.data == 1){
				location.reload(true);
			}else{
				alert('로그인 후 이용이 가능합니다.')
			}
		})
	}
	
	function cmt_favorite(i_cmt){
		console.log('i_cmt: ' + i_cmt)
		axios.get('/movie/ajaxCmt_favorite',{
			params:{
				i_cmt:i_cmt
			}
		}).then(function(res){
			if(res.data == 1){
				location.reload(true);
			}else{
				alert('로그인 후 이용이 가능합니다')
			}
		})
	}

	// youtube API 불러옴
	var tag = document.createElement('script');
	tag.src = "https://www.youtube.com/player_api";
	var firstScriptTag = document.getElementsByTagName('script')[0];
	firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

	// 플레이어변수 설정
	var youTubePlayer1;

	function onYouTubeIframeAPIReady() {
	    youTubePlayer1 = new YT.Player('youTubePlayer1', {
	        width: '1000',
	        height: '563',
	        videoId: `${movieDetail.videocode}`,
	        playerVars: {rel: 0},//추천영상 안보여주게 설정
	        events: {
	          'onReady': onPlayerReady, //로딩할때 이벤트 실행
	          'onStateChange': onPlayerStateChange //플레이어 상태 변화시 이벤트실행
	        }
	    });//youTubePlayer1셋팅
	}
	
	function onPlayerReady(event) {
	    event.target.playVideo();//자동재생
	    //로딩할때 실행될 동작을 작성한다.
	}

	function onPlayerStateChange(event) {
	    if (event.data == YT.PlayerState.PLAYING) {
	        //플레이어가 재생중일때 작성한 동작이 실행된다.
	    }
	  }	
</script>