package com.example.androidthreadstests.cache;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageMemoryCache {

	private static ImageMemoryCache sInstance;
	private static final long DEFAULT_MAX_SIZE = Runtime.getRuntime().maxMemory() / 6;
	private long mMaxSize;
	private Map<String, Bitmap> mLruMap = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
	private long mSize;
	private ImageMemoryCache(long maxSize) {
		setLimit(mMaxSize);
	}
	
	public static ImageMemoryCache getInstance() {
		
		synchronized (ImageMemoryCache.class) {
			if(sInstance == null) {
				sInstance = new ImageMemoryCache(DEFAULT_MAX_SIZE);
			}
		}
		
		return sInstance;
	}
	
	public synchronized void setLimit(long newMaxSize) {
		Log.d(getClass().getSimpleName(), "New max size: " + newMaxSize);
		mMaxSize = newMaxSize;
	}
	
	public synchronized void put(String key, Bitmap value) {
		try{
			if(mLruMap.containsKey(key)){
				return;
			}
			mLruMap.put(key, value);
			mSize += getSize(value);
			ensureSize();
		}catch(Throwable e){
			Log.d(getClass().getSimpleName(), "IN CACHE PUT METHOD " + e.getMessage());
		}
		
	}
	
	public synchronized Bitmap get(String key){
		try{
			if(!mLruMap.containsKey(key)){
				return null;
			}
			return mLruMap.get(key);
		}catch(Throwable e){
			Log.d(getClass().getSimpleName(), "IN CACHE GET METHOD " + e.getMessage());
			return null;
		}
	}
	
	public synchronized boolean contains(String key) {
		return mLruMap.containsKey(key);
	}

	private void ensureSize() {
		
		if(mSize > mMaxSize) {
			long cleanedUpSizeTotal = 0l;
			Iterator<Map.Entry<String, Bitmap>> iterator = mLruMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Bitmap> entry = iterator.next();
				long size = getSize(entry.getValue());
				mSize -= size;
				cleanedUpSizeTotal += size;
				iterator.remove();
				if(mSize <= mMaxSize){
					break;
				}
			}
			Log.d(getClass().getSimpleName(), "Cleaned " + cleanedUpSizeTotal + " bytes.");
		}
	}

	private long getSize(Bitmap value) {
		long size = value.getRowBytes() * value.getHeight();
		return size;
	}

}
