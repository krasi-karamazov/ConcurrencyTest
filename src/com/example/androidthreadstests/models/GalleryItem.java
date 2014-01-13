package com.example.androidthreadstests.models;

import android.content.ContentValues;

public class GalleryItem extends BaseLoaderModel<String> {
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
    public ContentValues getContentValues() {
        final ContentValues cv = new ContentValues();
        cv.put(ID_KEY, getId());
        cv.put(ID_KEY, getId());
        cv.put(TITLE_KEY, mTitle);
        cv.put(FARM_KEY, mFarm);
        cv.put(SECRET_KEY, mSecret);
        cv.put(SERVER_KEY, mServer);

        return cv;
    }
}
