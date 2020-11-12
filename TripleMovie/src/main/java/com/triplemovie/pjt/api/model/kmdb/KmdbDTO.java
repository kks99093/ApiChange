package com.triplemovie.pjt.api.model.kmdb;

import com.google.gson.annotations.SerializedName;

public class KmdbDTO {
	@SerializedName("Query")
	private String query;
	@SerializedName("KMAQuery")
    private String kmaQuery;
	@SerializedName("TotalCount")
    private long totalCount;
	@SerializedName("Data")
    private Datum[] data;
	
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getKmaQuery() {
		return kmaQuery;
	}
	public void setKmaQuery(String kmaQuery) {
		this.kmaQuery = kmaQuery;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public Datum[] getData() {
		return data;
	}
	public void setData(Datum[] data) {
		this.data = data;
	}
    
}
