package com.example.androidthreadstests.utils;

public class Constants {
	public static final String ENDPOINT = "http://api.flickr.com/services/rest/";
	public static final String GET_RECENT_METHOD = "flickr.photos.getRecent";
	public static final String API_KEY = "891b839bf4433797667a18370ec2d4d0";
	public static final String FORMAT = "rest";
	public static final String PER_PAGE_ARG = "200";
	public static int NUM_PAGES = 0;
	public static int CURRENT_PAGE = 1;
	
	
	/**
	 * Query params names
	 */
	public static final String METHOD_PARAM_NAME = "method";
	public static final String API_KEY_PARAM_NAME = "api_key";
	public static final String FORMAT_PARAM_NAME = "format";
	public static final String PER_PAGE_ARG_NAME = "per_page";
	public static final String PAGE_ARG_NAME = "page";
}
