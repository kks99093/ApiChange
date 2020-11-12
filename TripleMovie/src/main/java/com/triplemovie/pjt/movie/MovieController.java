package com.triplemovie.pjt.movie;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.triplemovie.pjt.SecurityUtils;
import com.triplemovie.pjt.movie.model.CinemaVO;
import com.triplemovie.pjt.movie.model.CmtDMI;
import com.triplemovie.pjt.movie.model.CmtVO;
import com.triplemovie.pjt.movie.model.MovieDTO;
import com.triplemovie.pjt.user.model.UserTicketVO;
import com.triplemovie.pjt.movie.model.SearchDTO;

@Controller
@RequestMapping(value = "/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
		
	@RequestMapping(value="/main")
	public String movieMain(Model model,HttpSession hs) {
		movieService.movieInfo(hs);
		model.addAttribute("movieList",movieService.movieMain(hs));
		model.addAttribute("view", "movie/main");
		return "templete/temp";
	}
	
	@RequestMapping(value="/movieSearch")
	public String movieSerach(Model model) {
		model.addAttribute("view", "movie/movieSearch");
		return "templete/temp";
	}
	
	//영화 티켓
	@RequestMapping(value="/movieTicket")
	public String movieTicekting(Model model) {
//		model.addAttribute("movieList",movieService.movieMain());
		model.addAttribute("movieDay", movieService.movieR_dt());
		model.addAttribute("view","movie/movieTicket");
		return "templete/temp";
	}
	
	//영화 검색 결과
	@RequestMapping(value="/searchResult")
	public String movieSearch(Model model, MovieDTO mDTO) {
		model.addAttribute("movieList",movieService.nameToCd(mDTO));
		model.addAttribute("view","movie/searchResult");
		return "templete/temp";
	}
	//
	
	@RequestMapping(value="/detail")
	public String movieDetail(Model model,MovieDTO movieDTO, HttpSession hs) {
		//String movieNm = movieDTO.getMovieNm().substring(3, movieDTO.getMovieNm().length()-4); //태그 삭제
		//movieDTO.setMovieNm(movieNm); 나중에 search할떄 쓸거
		int i_user = SecurityUtils.getLoginUserPk(hs);
		movieDTO.setI_user(i_user);
		model.addAttribute("movieCmt", movieService.movieSelCmt(movieDTO));
		model.addAttribute("movieDetail",movieService.movieDetail(movieDTO));
		model.addAttribute("title", "영화 상세정보");
		model.addAttribute("view","movie/movieDetail");
		return "templete/temp";
	}
	
	@RequestMapping(value="/ajaxCmt", method = RequestMethod.POST)
	@ResponseBody 
	public int ajaxCmt(@RequestBody CmtVO vo, HttpSession hs){ 
		int i_user = SecurityUtils.getLoginUserPk(hs); 
		System.out.println("i_user: "+ i_user);
		vo.setI_user(i_user); 
		return movieService.insCmt(vo); 
	}
	
	//좋아요 취소
	@RequestMapping(value="/ajaxCmt_Notfavorite", method = RequestMethod.GET)
	@ResponseBody 
	public int ajaxCmt_Notfavorite(CmtDMI param,HttpSession hs) { 
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		return movieService.delFavorite(param); 
	 }
	
	//좋아요 누름
	@RequestMapping(value="/ajaxCmt_favorite", method = RequestMethod.GET)
	@ResponseBody 
	public int ajaxCmt_favorite(CmtDMI param,HttpSession hs) { 
		int i_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(i_user);
		return movieService.insFavorite(param); 
	 }
	 
	
	//티켓 시간 불러오기(ajax)
	@RequestMapping(value="/selTime")
	@ResponseBody 
	public CinemaVO[] ajaxCmt(CinemaVO param){ 
		return movieService.selTime(param); 
	}
	
	@RequestMapping(value="/movieSheets")
	public String test(Model model,UserTicketVO vo) {
		model.addAttribute("userTicket", vo);
		model.addAttribute("title", "좌석고르기");
		model.addAttribute("view","movie/seatTest");
		return "templete/temp";
	}
	
	@RequestMapping(value="/test", method = RequestMethod.POST)
	public String test2(Model model, UserTicketVO vo, HttpSession hs) {
		
		int i_user = SecurityUtils.getLoginUserPk(hs);
		vo.setI_user(i_user);
		
		movieService.insTicketing(vo);
		model.addAttribute("title", "영화 상세정보");
		model.addAttribute("view","movie/seatTest");
		return "templete/temp";
	}

}
