package com.triplemovie.pjt.movie.model;

public class CinemaVO {
	private int i_movie;
	private String movie_cd;
	private int room;
	private int s_dt;
	private int e_dt;
	private int r_dt;
	private String time;
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getR_dt() {
		return r_dt;
	}
	public void setR_dt(int r_dt) {
		this.r_dt = r_dt;
	}
	public String getMovie_cd() {
		return movie_cd;
	}
	public void setMovie_cd(String movie_cd) {
		this.movie_cd = movie_cd;
	}
	public int getI_movie() {
		return i_movie;
	}
	public void setI_movie(int i_movie) {
		this.i_movie = i_movie;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public int getS_dt() {
		return s_dt;
	}
	public void setS_dt(int s_dt) {
		this.s_dt = s_dt;
	}
	public int getE_dt() {
		return e_dt;
	}
	public void setE_dt(int e_dt) {
		this.e_dt = e_dt;
	}
}
