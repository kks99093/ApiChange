package com.triplemovie.pjt.user.model;

import java.util.List;

public class UserTicketVO {
	private String title;
	private String movieCd;
	private int i_user;
	private String r_dt;
	private int room;
	private int s_dt;//분으로 담겨있음
	private String[] seats;
	private String seat;
	private int seatCnt; //몇개 예매했는지
	private int i_ticket;//티켓테이블 pk
	private String s_Time;
	
	public String getS_Time() {
		return s_Time;
	}
	public void setS_Time(String s_Time) {
		this.s_Time = s_Time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getI_ticket() {
		return i_ticket;
	}
	public void setI_ticket(int i_ticket) {
		this.i_ticket = i_ticket;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getMovieCd() {
		return movieCd;
	}
	public void setMovieCd(String movieCd) {
		this.movieCd = movieCd;
	}
	public String[] getSeats() {
		return seats;
	}
	public void setSeats(String[] seats) {
		this.seats = seats;
	}
	public int getSeatCnt() {
		return seatCnt;
	}
	public void setSeatCnt(int seatCnt) {
		this.seatCnt = seatCnt;
	}
	public int getI_user() {
		return i_user;
	}
	public void setI_user(int i_user) {
		this.i_user = i_user;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
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
	
	
	
}
