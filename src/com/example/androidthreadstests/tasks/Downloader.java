package com.example.androidthreadstests.tasks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.example.androidthreadstests.cache.DiskCache;
import com.example.androidthreadstests.models.BaseGalleryModel;
import com.example.androidthreadstests.tasks.listeners.ImageDownloadListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

public abstract class Downloader<T, I, D> extends HandlerThread {

	public static final int MESSAGE_DOWNLOAD = 1;
	private Handler mHandler;
	private Map<T, I> mMap = Collections.synchronizedMap(new HashMap<T, I>());
	private Handler mResponseHandler;
	private ImageDownloadListener<T, D> mListener;
	public Downloader(Handler target, Context context) {
		super("Downloader");
		mResponseHandler = target;
		DiskCache.setUpStorage(context);
	}
	
	public final void begin() {
		start();
		getLooper();
	}
	
	public void setListener(ImageDownloadListener<T, D> listener) {
		mListener = listener;
	}
	
	public void queueDownload(T token, I model) {
		mMap.put(token, model);
		mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
	}
	
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onLooperPrepared() {
		super.onLooperPrepared();
		mHandler = new Handler(){
			@SuppressWarnings("unchecked")
			public void handleMessage(Message msg) {
				if(msg.what == MESSAGE_DOWNLOAD){
					T key = (T)msg.obj;
					handleToken(key);
				}
			}
		};
	}
	
	private void handleToken(final T key){
		final I model = mMap.get(key);
		if(model == null) {
			return;
		}
		final D result = handleRequest(model);
		mResponseHandler.post(new Runnable() {
			
			@Override
			public void run() {
				
				if(mMap.get(key) != null && !mMap.get(key).equals(model)){
					return;
				}
				mMap.remove(key);
				mListener.onImageDownloaded(key, result);
			}
		});
	}
	
	public void shotdown() {
		mMap.clear();
		mHandler.removeMessages(MESSAGE_DOWNLOAD);
		quit();
	}

	protected abstract D handleRequest(I model);

}
