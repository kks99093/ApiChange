package com.triplemovie.pjt.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.triplemovie.pjt.api.model.kmdb.KmdbDTO;
import com.triplemovie.pjt.api.model.kmdb.KmdbParam;

public class Kmdb {
	private static String serviceKey = "RE4G1G08Q2LDC9H7BA1T";
	
	
	public static KmdbDTO movieSearch(KmdbParam param){
		StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
		urlBuilder.append("&ServiceKey="+serviceKey);
		if(param.getMovieSeq() != null) {
			urlBuilder.append("&movieSeq="+param.getMovieSeq());
		}else if(param.getMovieNm() != null) {
		urlBuilder.append("&title="+param.getMovieNm());
		}		
		urlBuilder.append("&detail=Y");
		
		try {
		URL url = new URL(urlBuilder.toString()); 
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
		conn.setRequestMethod("GET"); 
		conn.setRequestProperty("Content-type", "application/json"); 
		BufferedReader rd; 
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) { 
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
		} else { 
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream())); 
		} 
		StringBuilder sb = new StringBuilder(); 
		String line; 
		while ((line = rd.readLine()) != null) { 
			sb.append(line); 
		} rd.close(); 
		conn.disconnect();  
		
		Gson gson = new Gson();
		String jsonData = sb.toString();
		
		KmdbDTO kdDTO = gson.fromJson(jsonData, KmdbDTO.class);

		return kdDTO;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
