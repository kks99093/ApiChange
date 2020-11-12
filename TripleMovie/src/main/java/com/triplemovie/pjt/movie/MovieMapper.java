package com.triplemovie.pjt.movie;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.triplemovie.pjt.movie.model.CinemaVO;
import com.triplemovie.pjt.movie.model.CmtDMI;
import com.triplemovie.pjt.movie.model.CmtVO;
import com.triplemovie.pjt.movie.model.MovieDTO;
import com.triplemovie.pjt.user.model.UserTicketVO;

@Mapper
public interface MovieMapper {
	// ------------------ insert -----------------------------
	
	int insCmt(CmtVO vo);
	void insCinema(CinemaVO param);
	void insMain();
	int insFavorite(CmtDMI param);
	int insTicketing(UserTicketVO vo);
	void insSeats(UserTicketVO vo);
	
	// ------------------ update -----------------------------
	
	void updMain(String mainData);
	
	// ------------------ select -----------------------------
	
	CinemaVO selRoomTime(CinemaVO param);
	String selMain();
	List<CmtDMI> selCmt(MovieDTO param);
	List<CmtVO> selCmtFavorite(MovieDTO param);
	CinemaVO[] selR_dt();
	CinemaVO[] selTime(CinemaVO param);

	// ------------------ delete -----------------------------
	
	int delFavorite(CmtDMI param);

}
