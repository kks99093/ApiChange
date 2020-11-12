package com.triplemovie.pjt.movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triplemovie.pjt.Const;
import com.triplemovie.pjt.api.Kmdb;
import com.triplemovie.pjt.api.Kobis;
import com.triplemovie.pjt.api.Naver;
import com.triplemovie.pjt.api.Youtube;
import com.triplemovie.pjt.api.model.kmdb.KmdbDTO;
import com.triplemovie.pjt.api.model.kmdb.KmdbParam;
import com.triplemovie.pjt.api.model.kobis.KobisDTO;
import com.triplemovie.pjt.api.model.kobis.NameToCdDTO;
import com.triplemovie.pjt.api.model.kobis.info.MovieInfoDTO;
import com.triplemovie.pjt.api.model.naver.NMovieDTO;
import com.triplemovie.pjt.api.model.naver.Summary;
import com.triplemovie.pjt.movie.model.CinemaVO;
import com.triplemovie.pjt.movie.model.CmtDMI;
import com.triplemovie.pjt.movie.model.CmtVO;
import com.triplemovie.pjt.movie.model.MovieDTO;
import com.triplemovie.pjt.movie.model.SearchDTO;
import com.triplemovie.pjt.user.model.UserTicketVO;

@Service
public class MovieService {
	
	@Autowired
	private MovieMapper mapper;
	
	//Kmdb 영화 이름으로 검색 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ끝
	public KmdbDTO moiveNmSearch(KmdbParam param){
		KmdbDTO kdDTO = null;
		kdDTO = Kmdb.movieSearch(param);
		String poster = kdDTO.getData()[0].getResult()[0].getPosters();
		poster = poster.substring(0, poster.indexOf("|"));
		kdDTO.getData()[0].getResult()[0].setPosters(poster);
		return kdDTO;
	}
	
	//Kmdb 영화 seq로 검색
	public KmdbDTO movieSeqSearch(KmdbParam param) {
		KmdbDTO kdDTO = null;
		kdDTO = Kmdb.movieSearch(param);
		String poster = kdDTO.getData()[0].getResult()[0].getPosters();
		poster = poster.substring(0, poster.indexOf("|"));
		kdDTO.getData()[0].getResult()[0].setPosters(poster);
		return kdDTO;
		
	}
	
	//메인
	public List<KmdbDTO> movieMain(HttpSession hs) {
		List<KmdbDTO> kdDTOList = new ArrayList<KmdbDTO>();
		List<KmdbParam> movieRankList = (List<KmdbParam>) hs.getAttribute(Const.MOVIERANK);
		for(KmdbParam param : movieRankList) {
			KmdbDTO kdDTO = new KmdbDTO();
			try {
				kdDTO = moiveNmSearch(param);
				kdDTOList.add(kdDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return kdDTOList;
	}
	
	//영화 날짜
	public CinemaVO[] movieR_dt() {
		return mapper.selR_dt();
	}
	
	//영화 시간
	public CinemaVO[] selTime(CinemaVO param) {
		CinemaVO[] vo = mapper.selTime(param);
		for(CinemaVO var : vo) {
			int min = var.getS_dt();
			String time = String.format("%02d", (min/60)) + " : " + String.format("%02d", (min%60));
			var.setTime(time);
		}
		 
		return vo;
	}
	
	//영화 검색 결과
	public SearchDTO naverSearch(MovieDTO mDTO){
		NMovieDTO nDTO = Naver.naverMovie(mDTO.getMovieNm());
		if(nDTO.getItems().length > 0) {
			SearchDTO sDTO = Summary.movieinfo(nDTO.getItems()[0].getLink());
			return sDTO;
		}else {
			return null;
		}
	}
	
	
	//박스오피스 영화 정보 세션에 넣음 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ끝
	public void movieInfo(HttpSession hs) {
		List<KmdbParam> movieList = new ArrayList();
		KobisDTO kDTO = Kobis.boxOffice();
		for(int i=0; i<kDTO.getBoxOfficeResult().getDailyBoxOfficeList().length; i++) {
			KmdbParam param = new KmdbParam();
			String movieNm = kDTO.getBoxOfficeResult().getDailyBoxOfficeList()[i].getMovieNm();
			String openDt =  kDTO.getBoxOfficeResult().getDailyBoxOfficeList()[i].getOpenDt().substring(0, 4); 
			if(!openDt.equalsIgnoreCase("2020")) {
				continue;
			}
			param.setMovieNm(movieNm);
			KmdbDTO kdDTO = Kmdb.movieSearch(param);
			
			String repRlsDate = kdDTO.getData()[0].getResult()[0].getRepRlsDate();
			if(repRlsDate.length() <4 || !openDt.equals(repRlsDate.substring(0,4))) {
				continue;
			}
			String movieSeq = kdDTO.getData()[0].getResult()[0].getMovieSeq();
			param.setMovieSeq(movieSeq);
			if(movieList.size() <5) {
				movieList.add(param);
			}
		}
		hs.setAttribute(Const.MOVIERANK, movieList);

	}
	
	//서치화면
	public List<SearchDTO> nameToCd(MovieDTO mDTO) {
		String movieNm = mDTO.getMovieNm().replaceAll("\\p{Z}", ""); //영화이름 전체공백제거
		mDTO.setMovieNm(movieNm); //공백제거한걸 다시 set으로 보내줌
		NameToCdDTO nTC = Kobis.nameToCd(mDTO);
		List<SearchDTO> sDTOList = new ArrayList<SearchDTO>();
		for(int i=0; i<nTC.getMovieListResult().getMovieList().length; i++) {
			 mDTO.setMovieNm(nTC.getMovieListResult().getMovieList()[i].getMovieNm());
			 SearchDTO sDTO = new SearchDTO();
			 sDTO = naverSearch(mDTO);
			 sDTO.setMovieCd(nTC.getMovieListResult().getMovieList()[i].getMovieCd());
			 sDTOList.add(sDTO);
		}
		return sDTOList;
	}
	
	
	
	//디테일 영화정보
	public NMovieDTO movieDetail(MovieDTO movieDTO) {
		MovieInfoDTO infoDTO = Kobis.detailMovie(movieDTO.getMovieCd());
		String movieNm = infoDTO.getMovieInfoResult().getMovieInfo().getMovieNm();
		NMovieDTO nDTO = Naver.naverMovie(movieNm);
		String beforeOD = infoDTO.getMovieInfoResult().getMovieInfo().getOpenDt();
		StringBuilder sB = new StringBuilder();
		sB.append(beforeOD);
		sB.insert(4, "/");
		sB.insert(7, "/");
		String afterOD = new String(sB);
		
		String videocode = null;
		try {
			videocode = Youtube.search(movieNm);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0; i<nDTO.getItems().length; i++) {
			String peopleNm = infoDTO.getMovieInfoResult().getMovieInfo().getDirectors()[0].getPeopleNm()+"|";
			String director = nDTO.getItems()[i].getDirector();
			if(peopleNm.equals(director)) {
				nDTO.getItems()[0] = nDTO.getItems()[i];
				break;
			}
		}
		if(videocode != null){nDTO.setVideocode(videocode);}//iframe api를 위한 비디오코드
		nDTO.setMovieCd(movieDTO.getMovieCd()); //영화cd
		nDTO.setAudits(infoDTO.getMovieInfoResult().getMovieInfo().getAudits()[0].getWatchGradeNm()); //심의
		nDTO.setGenres(infoDTO.getMovieInfoResult().getMovieInfo().getGenres()); //장르
		nDTO.setShowTm(infoDTO.getMovieInfoResult().getMovieInfo().getShowTm());//시간
		nDTO.setOpenDt(afterOD);//개봉일자
		nDTO.setActors(infoDTO.getMovieInfoResult().getMovieInfo().getActors());//배우목록
		nDTO.setDirectors(infoDTO.getMovieInfoResult().getMovieInfo().getDirectors()); //감독목록
		return nDTO;
	}
	
	//날짜에 영화등록이 되어있는지 체크
	public CinemaVO selCinema(CinemaVO param) {
		return mapper.selRoomTime(param);
	}
	
	
	//영화 정보 인설트 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	public void insCinema(CinemaVO vo, HttpSession hs) {

		String[] arr = new String[20];
		String[] arrSeq = new String[20];
		List<KmdbParam> movieRank = (List<KmdbParam>) hs.getAttribute("movieRank");
		int num = 0;
		int num2 = 0;
		
		//영화목록 6,5,4,3,2개씩 List에 넣기
		for(int i=0; i<6; i++) {
			if(i == 5) {num2 = 2;}
			int ran = (int)((Math.random()*20)+1);
			for(int z=6-i; z>num2; z--) {
				String movieNm = movieRank.get(i).getMovieNm();
				String movieSeq = movieRank.get(i).getMovieSeq();
				arr[num] = movieNm;
				arrSeq[num] = movieSeq;
				num++;
			}
		}
		//-------------------------------------------------------------------------------------------
		//1~20까지 겹치지 않게 a배열에 담음
		int a[] = new int[20];
		for(int i=0; i<a.length; i++) {
			int ran = (int)((Math.random()*20));
			a[i] = ran;
			for(int z=0; z<i; z++) {
				if(a[i] == a[z]) {
					i--;
					break;
				}
			}
		}
		
		// ------------------------------------------------------------------------------------ db작업

		
		int ran = 0;
		for(int i=0; i<arr.length; i++) {
			if(ran >= 4) {ran=0;}
			ran++;
			CinemaVO param = new CinemaVO();
			KmdbParam kmdbParam = new KmdbParam();
			String movieNm = arr[a[i]];
			String movieSeq = arrSeq[a[i]];
			kmdbParam.setMovieNm(movieNm);
			
			KmdbDTO kdDTO = moiveNmSearch(kmdbParam);
			int runTime = 0;
			for(int z=0; z<kdDTO.getData()[0].getResult().length;z++ ) {
				String getTitle = kdDTO.getData()[0].getResult()[z].getTitleEtc();
				String title = getTitle.substring(0,getTitle.indexOf("^"));
				 if(title.equals(movieNm)) {					 					 
					 runTime = Integer.parseInt(kdDTO.getData()[0].getResult()[z].getRuntime());
				 }
			 }

			 param.setR_dt(vo.getR_dt());
			 param.setRoom(ran);	 
			 CinemaVO cinemaVo = mapper.selRoomTime(param);
			 if(cinemaVo == null) {
				 System.out.println("1번");
				 param.setS_dt(540);
				 int e_dt2 = 540 + runTime; 
				 param.setE_dt(e_dt2);
			 }else if(cinemaVo.getS_dt()+runTime >= 1380) {
				 System.out.println("2번");
				 i--;
				continue;
			 }else {
				 System.out.println("3번");
				 int s_dt = cinemaVo.getE_dt() + 10;
				 int e_dt2 = runTime + s_dt;
				 param.setS_dt(s_dt);
				 param.setE_dt(e_dt2);
			 }
//			 param.setMovie_cd(cd);
//			param.setMovieSeq(movieSeq)
			 param.setRoom(ran);
			 System.out.println("e_dt: " + param.getE_dt());
			 mapper.insCinema(param);
			 
		}
	}


	public int insCmt(CmtVO vo) { 
		return mapper.insCmt(vo); 
	}


	public CmtDMI movieSelCmt(MovieDTO param) {
		CmtDMI cmt_combine = new CmtDMI();
		List<CmtDMI> cmt_info = mapper.selCmt(param);
		if(param.getI_user() != 0) {
			List<CmtVO> cmt_favorite = mapper.selCmtFavorite(param);
			cmt_combine.setCmt_favorite(cmt_favorite);
		}
		cmt_combine.setCmt_info(cmt_info);
		return cmt_combine;
	}

	public int delFavorite(CmtDMI param) {
		return mapper.delFavorite(param);
	}

	public int insFavorite(CmtDMI param) {
		return mapper.insFavorite(param);
	}

	public void insTicketing(UserTicketVO vo) {
		if(vo.getSeatCnt() > 8) {return;}
		int i_ticket = mapper.insTicketing(vo);
		vo.setI_ticket(i_ticket);
		for(String seat : vo.getSeats()) {
			vo.setSeat(seat);
			mapper.insSeats(vo);
		}
		
	}



}
