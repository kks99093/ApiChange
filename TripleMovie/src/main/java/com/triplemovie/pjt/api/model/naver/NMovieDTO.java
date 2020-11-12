package com.triplemovie.pjt.api.model.naver;

import java.util.Iterator;

import org.jsoup.nodes.Element;

import com.triplemovie.pjt.api.model.kobis.info.Actor;
import com.triplemovie.pjt.api.model.kobis.info.Director;
import com.triplemovie.pjt.api.model.kobis.info.Genre;

public class NMovieDTO {
	private String videocode;
	private String audits;
	private String movieCd;
	private String showTm; 
	private String openDt;
	private String summary;
	
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	private String imgurl;
	
	private String lastBuildDate;
    private long total;
    private long start;
    private long display;
    private Item[] items;
    private Genre[] genres;
    private Director[] directors;
    private Actor[] actors;
    
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getVideocode() {
		return videocode;
	}
	public void setVideocode(String videocode) {
		this.videocode = videocode;
	}
    
    
	public Director[] getDirectors() {
		return directors;
	}
	public void setDirectors(Director[] directors) {
		this.directors = directors;
	}
	public Actor[] getActors() {
		return actors;
	}
	public void setActors(Actor[] actors) {
		this.actors = actors;
	}
	public Genre[] getGenres() {
		return genres;
	}
	public void setGenres(Genre[] genres) {
		this.genres = genres;
	}
	public String getAudits() {
		return audits;
	}
	public void setAudits(String audits) {
		this.audits = audits;
	}
	public String getMovieCd() {
		return movieCd;
	}
	public void setMovieCd(String movieCd) {
		this.movieCd = movieCd;
	}
	
	public String getShowTm() {
		return showTm;
	}
	public void setShowTm(String showTm) {
		this.showTm = showTm;
	}
	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getLastBuildDate() {
		return lastBuildDate;
	}
	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getDisplay() {
		return display;
	}
	public void setDisplay(long display) {
		this.display = display;
	}
	public Item[] getItems() {
		return items;
	}
	public void setItems(Item[] items) {
		this.items = items;
	}
    
    
}
