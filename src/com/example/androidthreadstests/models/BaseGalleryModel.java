package com.example.androidthreadstests.models;

import java.util.Map;

public abstract class BaseGalleryModel {
	private String mId;
	public abstract Map<String, String> getParams();
	
	public String getId() {
		return mId;
	}
	public void setId(String mId) {
		this.mId = mId;
	}
}
