package com.example.androidthreadstests.models;

import java.util.HashMap;
import java.util.Map;

public class GalleryItem extends BaseGalleryModel {
	
	private String mId;
	private String mTitle;
	private String mFarm;
	private String mSecret;
	private String mServer;
	
	public static final String ID_KEY = "ID";
	public static final String TITLE_KEY = "TITLE";
	public static final String FARM_KEY = "FARM";
	public static final String SECRET_KEY = "SECRET";
	public static final String SERVER_KEY = "SERVER";
	
	
	
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getFarm() {
		return mFarm;
	}
	public void setFarm(String mFarm) {
		this.mFarm = mFarm;
	}
	public String getSecret() {
		return mSecret;
	}
	public void setSecret(String mSecret) {
		this.mSecret = mSecret;
	}
	public String getServer() {
		return mServer;
	}
	public void setServer(String mServer) {
		this.mServer = mServer;
	}
	@Override
	public Map<String, String> getParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(ID_KEY, mId);
		map.put(TITLE_KEY, mTitle);
		map.put(FARM_KEY, mFarm);
		map.put(SECRET_KEY, mSecret);
		map.put(SERVER_KEY, mServer);
		return map;
	}
}
