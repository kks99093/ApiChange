package com.triplemovie.pjt.api;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.web.client.RestTemplate;

import com.triplemovie.pjt.api.model.kobis.KobisDTO;
import com.triplemovie.pjt.api.model.kobis.NameToCdDTO;
import com.triplemovie.pjt.api.model.kobis.info.MovieInfoDTO;
import com.triplemovie.pjt.movie.model.MovieDTO;

public class Kobis {
	
	private static String apiKey = "430156241533f1d058c603178cc3ca0e";
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static KobisDTO boxOffice() {
		//어제날짜 가져오기
		Date dDate = new Date();
		dDate = new Date(dDate.getTime()+(1000*60*60*24*-1));
		SimpleDateFormat dSdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String yesterday = dSdf.format(dDate);
		
		KobisDTO mDTO = new KobisDTO();
		URI url = URI.create("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key="+apiKey+"&targetDt="+yesterday);
		mDTO = restTemplate.getForObject(url,KobisDTO.class);
		return mDTO;
	}
	
	//영화 상세 (cd로 상세정보 검색)
	public static MovieInfoDTO detailMovie(String movieCd){
		MovieInfoDTO infoDTO = new MovieInfoDTO();
		URI url = URI.create("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key="+apiKey+"&movieCd="+movieCd);
		infoDTO = restTemplate.getForObject(url,MovieInfoDTO.class);
		return infoDTO;
	}
	
	//이름으로 Cd값 얻어오기
	public static NameToCdDTO nameToCd(MovieDTO mDTO) {
		NameToCdDTO nTC = new NameToCdDTO();
		String movieNm = mDTO.getMovieNm(); 		
		URI url = URI.create("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key="+apiKey+"&movieNm="+movieNm);
		nTC  = restTemplate.getForObject(url, NameToCdDTO.class);
		return nTC;
	}
	
	
}
