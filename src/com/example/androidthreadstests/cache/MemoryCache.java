package com.example.androidthreadstests.cache;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {

	private static MemoryCache sInstance;
	private static final long DEFAULT_MAX_SIZE = Runtime.getRuntime().maxMemory() / 6;
	private long mMaxSize;
	private Map<String, Bitmap> mCache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
	private long mSize;
	private MemoryCache(long maxSize) {
		setLimit(mMaxSize);
	}
	
	public static MemoryCache getInstance() {
		
		synchronized (MemoryCache.class) {
			if(sInstance == null) {
				sInstance = new MemoryCache(DEFAULT_MAX_SIZE);
			}
		}
		
		return sInstance;
	}
	
	public synchronized void setLimit(long newMaxSize) {
		Log.d(getClass().getSimpleName(), "New max size: " + newMaxSize);
		if(newMaxSize > 0){
			mMaxSize = newMaxSize;
		}else{
			mMaxSize = DEFAULT_MAX_SIZE;
		}
	}
	
	public synchronized void put(String key, Bitmap value) {
		try{
			if(mCache.containsKey(key)){
				return;
			}
			mCache.put(key, value);
			mSize += getSize(value);
			ensureSize();
		}catch(Throwable e){
			Log.d(getClass().getSimpleName(), "IN CACHE PUT METHOD " + e.getMessage());
		}
		
	}
	
	public synchronized Bitmap get(String key){
		try{
			if(!mCache.containsKey(key)){
				return null;
			}
			return mCache.get(key);
		}catch(Throwable e){
			Log.d(getClass().getSimpleName(), "IN CACHE GET METHOD " + e.getMessage());
			return null;
		}
	}
	
	public synchronized boolean contains(String key) {
		return mCache.containsKey(key);
	}

	private void ensureSize() {
		
		if(mSize > mMaxSize) {
			long cleanedUpSizeTotal = 0l;
			Iterator<Map.Entry<String, Bitmap>> iterator = mCache.entrySet().iterator();
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
	
	public void clear() {
		mCache.clear();
		mSize = 0;
		System.gc();
	}

}
