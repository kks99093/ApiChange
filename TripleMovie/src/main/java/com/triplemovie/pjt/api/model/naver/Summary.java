package com.triplemovie.pjt.api.model.naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.triplemovie.pjt.movie.model.SearchDTO;

public class Summary {
	public static String moviesummary(String link) {
		String url = link;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			Elements element = doc.select("div.story_area");
			Element ie2 = element.select("p.con_tx").get(0);
			String summary = ie2.text();
			return summary;
		} catch (Exception e) {
			return "줄거리 없음";
		}
	}
	
	public static SearchDTO movieinfo(String link){		
		String url = link;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		Elements dl = doc.select("dl.info_spec");
		try {

			
			Element d_title = doc.select("h3.h_movie").get(0).select("a").get(0);
			String title = d_title.text();
			
			
			Element d_genre = dl.select("dd").get(0).select("span").get(0);
			String genre = d_genre.text();
		
			Element d_country = dl.select("dd").get(0).select("span").get(1);
			String country = d_country.text();
		
			Element d_showTime = dl.select("dd").get(0).select("span").get(2);
			String showTime = d_showTime.text();
			
		
			Element d_director = dl.select("dd").get(1).select("a").get(0);
			String director = d_director.text();
		
			Elements d_actor = dl.select("dd").get(2).select("p").get(0).select("a");
			String actor = d_actor.text();
		
			Element d_grade = dl.select("dd").get(3).select("p").get(0).select("a").get(0);
			String grade = d_grade.text();
			
		
			Element img = doc.select("div.poster").get(0).select("img").get(0);
			String imgurl = img.getElementsByAttribute("src").attr("src");
			
			SearchDTO sDTO = new SearchDTO();
			sDTO.setTitle(title);
			sDTO.setGenre(genre);
			sDTO.setDirector(director);
			sDTO.setCountry(country);
			sDTO.setActor(actor);
			sDTO.setGrade(grade);
			sDTO.setImgurl(imgurl);
			sDTO.setShowTime(showTime);
			return sDTO;
		}catch(Exception e) {	
			return null;
		}
		
	}
}
